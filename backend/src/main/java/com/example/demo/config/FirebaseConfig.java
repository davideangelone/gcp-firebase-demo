package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

  @PostConstruct
  public void initFirebase() throws IOException {
    log.info("Init Firebase with default profile");
    // Per il deploy su Google Cloud Run l'environment variable GOOGLE_APPLICATION_CREDENTIALS non è necessaria, GCP fornisce le credenziali
    // Per il deploy su altri ambienti serve la environment variable GOOGLE_APPLICATION_CREDENTIALS che contiene il path al file di credenziali

    FirebaseOptions options = FirebaseOptions.builder()
                                             .setCredentials(GoogleCredentials.getApplicationDefault())
                                             .build();

    if (FirebaseApp.getApps().isEmpty()) {
      FirebaseApp.initializeApp(options);
    }
  }

}
