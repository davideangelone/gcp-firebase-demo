package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("!test")
@Configuration
@Slf4j
public class FirebaseConfig {

  @Value("${projectId}")
  private String projectId;

  @PostConstruct
  public void initFirebase() throws IOException {
    log.info("Init Firebase with default profile, project id: {}", projectId);
    // Per il deploy su Google Cloud Run l'environment variable GOOGLE_APPLICATION_CREDENTIALS non è necessaria, GCP fornisce le credenziali
    // Per il deploy su altri ambienti serve la environment variable GOOGLE_APPLICATION_CREDENTIALS che contiene il path al file di credenziali

    FirebaseOptions options = FirebaseOptions.builder()
                                             .setCredentials(GoogleCredentials.getApplicationDefault())
                                             .setProjectId(projectId)
                                             .build();

    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }
  }

}
