package com.example.demo.repository;

import com.example.demo.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
@Slf4j
public class MessageRepositoryIntegrationTest {

  static final int FIRESTORE_EMULATOR_PORT = 9080;

  @Container
  static GenericContainer<?> firestoreEmulator = new GenericContainer<>(
          new ImageFromDockerfile()
                  .withDockerfile(Paths.get("src/test/resources/docker/Dockerfile"))
  )
                                                         .withExposedPorts(FIRESTORE_EMULATOR_PORT)
                                                         .waitingFor(Wait.forListeningPort());

  @Autowired
  MessageRepository messageRepository;

  // Override dinamico delle proprietà Spring
  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    String host = firestoreEmulator.getHost();
    Integer port = firestoreEmulator.getMappedPort(FIRESTORE_EMULATOR_PORT); //Porta mappata, generata dal container

    registry.add("spring.cloud.gcp.project-id", () -> "demo-project");
    registry.add("spring.cloud.gcp.firestore.project-id", () -> "demo-project");
    registry.add("spring.cloud.gcp.firestore.emulator.enabled", () -> "true");
    registry.add("spring.cloud.gcp.firestore.host-port", () -> host + ":" + port);
  }


  @BeforeEach
  void clean() {
    messageRepository.deleteAll().block();
  }

  @Test
  void testCrud() {
    AtomicReference<String> savedId = new AtomicReference<>();

    Message msg = new Message();
    msg.setText("Firestore Emulator Test");

    StepVerifier.create(messageRepository.save(msg))
                .assertNext(saved -> {
                  savedId.set(saved.getId());
                  assert saved.getText().equals(msg.getText());
                  log.info("Message saved with id: {} - Text: {}", saved.getId(), saved.getText());
                })
                .verifyComplete();

    StepVerifier.create(messageRepository.findById(savedId.get()))
                .assertNext(found -> {
                  assert found.getText().equals(msg.getText());
                  log.info("Message found with id: {} - Text: {}", found.getId(), found.getText());
                })
                .verifyComplete();

    StepVerifier.create(messageRepository.deleteById(savedId.get()))
                .verifyComplete();

    StepVerifier.create(messageRepository.findById(savedId.get()))
                .verifyComplete();
  }
}
