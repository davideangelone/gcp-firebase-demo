package com.example.demo.repository;

import com.example.demo.dto.Message;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

public interface MessageRepository extends FirestoreReactiveRepository<Message> {
}
