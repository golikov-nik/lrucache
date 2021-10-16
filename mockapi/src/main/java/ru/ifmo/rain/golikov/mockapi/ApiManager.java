package ru.ifmo.rain.golikov.mockapi;

import org.apache.commons.lang.Validate;

import java.time.Duration;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;

public class ApiManager {
  private final ApiClient client;

  public ApiManager(final ApiClient client) {
    this.client = client;
  }

  public int[] countPosts(final String hashtag, final int hoursCount) {
    Validate.isTrue(1 <= hoursCount && hoursCount <= 24,
            "Number of hours passed should be between 1 and 24");
    var currentTime = Instant.now();
    var postTimes = client.getPosts(
            hashtag,
            currentTime.minus(Duration.ofHours(hoursCount)),
            currentTime);
    int[] count = new int[hoursCount];
    for (var time : postTimes) {
      count[hoursCount - (int) time.until(currentTime, HOURS) - 1]++;
    }
    return count;
  }
}
