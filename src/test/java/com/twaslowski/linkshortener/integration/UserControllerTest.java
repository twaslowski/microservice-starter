package com.twaslowski.linkshortener.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.twaslowski.linkshortener.domain.dto.UserDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UserControllerTest extends IntegrationTestBase {

  @Test
  @SneakyThrows
  void shouldRegisterUser() {
    var loginRequest = UserDTO.builder()
        .email("contact@twaslowski.com")
        .password("a very secure password")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldReturnConflictOnSecondRegistration() {
    var loginRequest = UserDTO.builder()
        .email("contact@twaslowski.com")
        .password("a very secure password")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  @SneakyThrows
  void shouldLoginUserSuccessfullyWithCorrectCredentials() {
    var loginRequest = UserDTO.builder()
        .email("contact@twaslowski.com")
        .password("a very secure password")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldReturnUnauthorizedOnInvalidCredentials() {
    var loginRequest = UserDTO.builder()
        .email("contact@twaslowski.com")
        .password("a very secure password")
        .build();

    var incorrectPassword = UserDTO.builder()
        .email("contact@twaslowski.com")
        .password("a very wrong password")
        .build();

    var incorrectUsername = UserDTO.builder()
        .email("info@twaslowski.com")
        .password("a very secure password")
        .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(incorrectPassword)))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(incorrectUsername)))
        .andExpect(status().isUnauthorized());
  }

}
