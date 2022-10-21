package com.example.pet.model;

public enum Authorities {
  READ("permission:read"),
  WRITE("permission:write");
  private final String permission;
  Authorities(String permission) {
    this.permission = permission;
  }
  public String getAuthorities() {
    return permission;
  }
}
