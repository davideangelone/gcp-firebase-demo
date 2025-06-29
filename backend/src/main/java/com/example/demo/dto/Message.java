package com.example.demo.dto;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collectionName = "messages")
public class Message {
  @DocumentId
  private String id;
  private String user;
  private String text;
  private Date timestamp;

  public Message(String text) {
    this.text = text;
  }
}
