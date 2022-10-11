package com.example.pet.model;

public enum Permission {
  READ("permission:read"),
  WRITE("permission:write");

  private final String permission;

  Permission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
