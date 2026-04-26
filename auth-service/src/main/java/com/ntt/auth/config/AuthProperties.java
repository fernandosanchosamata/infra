package com.ntt.auth.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

  private List<MockUser> users = new ArrayList<>();

  public List<MockUser> getUsers() {
    return users;
  }

  public void setUsers(List<MockUser> users) {
    this.users = users;
  }

  public static class MockUser {

    private String username;
    private String password;
    private String subject;
    private String customerId;
    private List<String> roles = new ArrayList<>();

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public String getCustomerId() {
      return customerId;
    }

    public void setCustomerId(String customerId) {
      this.customerId = customerId;
    }

    public List<String> getRoles() {
      return roles;
    }

    public void setRoles(List<String> roles) {
      this.roles = roles;
    }
  }
}
