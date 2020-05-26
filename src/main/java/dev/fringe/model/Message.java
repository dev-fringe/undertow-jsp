package dev.fringe.model;

import lombok.Data;

@Data
public class Message {

  private String message;
  private int id;

  public Message() {
  }

  public Message(int id, String message) {
    this.id = id;
    this.message = message;
  }
}