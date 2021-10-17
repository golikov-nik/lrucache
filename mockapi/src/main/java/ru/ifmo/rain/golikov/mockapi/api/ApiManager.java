package ru.ifmo.rain.golikov.mockapi.api;

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
    return countPosts(hashtag, hoursCount, Instant.now());
  }

  public int[] countPosts(final String hashtag, final int hoursCount, final Instant endTime) {
    Validate.isTrue(1 <= hoursCount && hoursCount <= 24,
            "Number of hours passed should be between 1 and 24");
    var postTimes = client.getPosts(
            hashtag,
            endTime.minus(Duration.ofHours(hoursCount)),
            endTime);
    int[] count = new int[hoursCount];
    for (var time : postTimes) {
      count[hoursCount - (int) time.until(endTime, HOURS) - 1]++;
    }
    return count;
  }
}
