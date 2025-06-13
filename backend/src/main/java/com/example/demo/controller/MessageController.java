package com.example.demo.controller;

import com.example.demo.dto.Message;
import com.example.demo.security.FirebasePrincipal;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  @Autowired
  private MessageService messageService;

  @PostMapping
  public Message saveMessage(@AuthenticationPrincipal FirebasePrincipal principal, @RequestBody Message message) {
    return messageService.saveMessage(principal.getName(), message);
  }

  @GetMapping
  public List<Message> getMessages() {
    return messageService.getMessages();
  }

  @DeleteMapping("/{id}")
  public void deleteMessage(@PathVariable("id") String id) {
    messageService.deleteMessage(id);
  }
}
