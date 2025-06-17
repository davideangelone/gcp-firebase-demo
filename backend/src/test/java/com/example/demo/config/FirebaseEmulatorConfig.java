package com.example.demo.config;

import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.NoCredentials;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@Slf4j
public class FirebaseEmulatorConfig {
  @PostConstruct
  public void initFirebase() {
    log.info("Init Firebase emulator configuration");
  }

  @Bean
  public CredentialsProvider googleCredentials() {
    return NoCredentials::getInstance;
  }

}
