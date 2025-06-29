package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class FirebasePrincipal implements Principal {
  private String name;
  private String email;
  private String id;

  public FirebasePrincipal(String name) {
    this.name = name;
  }
}
