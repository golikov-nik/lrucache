package ru.ifmo.rain.golikov.mockapi.api;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiClientIntegrationTest {

  @Test
  public void getPosts() {
    var client = ApiClient.fromEnv();
    var currentTime = Instant.now();
    var fromTime = currentTime.minus(Duration.ofDays(1));
    var response = client.getPosts("#food", fromTime, currentTime);
    assertThat(response).allMatch(t -> (t.equals(currentTime) || t.isBefore(currentTime)) && t.isAfter(fromTime));
  }
}
