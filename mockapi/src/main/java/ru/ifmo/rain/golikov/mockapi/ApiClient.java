package ru.ifmo.rain.golikov.mockapi;

import java.time.Instant;
import java.util.*;

public class ApiClient {
  private static final String URL_FORMAT =
          "https://api.vk.com/method/newsfeed.search?q=%s&access_token=%s&start_time=%d&end_time=%d&v=%s";
  private static final String API_VERSION = "5.131";
  private final String apiToken;
  private final ApiResponseParser parser = new ApiResponseParser();
  private final UrlReader reader = new UrlReader();

  public ApiClient(final String apiToken) {
    this.apiToken = apiToken;
  }

  //  startTime exclusive, endTime inclusive
  public List<Instant> getPosts(final String hashtag, final Instant startTime, final Instant endTime) {
    var response = reader.readAsText(createUrl(hashtag, startTime, endTime));
    return parser.parseResponse(response);
  }

  private String createUrl(final String hashtag, final Instant startTime, final Instant endTime) {
    return String.format(URL_FORMAT,
            hashtag,
            apiToken,
            //  +1 because startTime is exclusive
            startTime.getEpochSecond() + 1,
            endTime.getEpochSecond(),
            API_VERSION);
  }
}
