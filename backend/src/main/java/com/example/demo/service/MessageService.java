package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

  @Autowired
  private MessageRepository repo;

  public Message saveMessage(String user, Message message) {
    message.setUser(user);
    message.setTimestamp(Date.from(Instant.now()));
    return repo.save(message).block();
  }

  public List<Message> getMessages() {
    return repo.findAll().collectList().block();
  }

  public void deleteMessage(String id) {
    repo.deleteById(id).block();
  }


}
