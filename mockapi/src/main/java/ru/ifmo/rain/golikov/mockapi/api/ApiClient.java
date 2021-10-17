package ru.ifmo.rain.golikov.mockapi.api;

import ru.ifmo.rain.golikov.mockapi.http.UrlReader;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

public class ApiClient {
  public static final String VK_HOST = "https://api.vk.com";
  public static final String URL_FORMAT =
          "%s/method/newsfeed.search?q=%s&access_token=%s&start_time=%d&end_time=%d&count=200&v=%s";
  public static final String API_VERSION = "5.131";
  public static final String API_TOKEN_VARIABLE = "API_TOKEN";
  private final String apiToken;
  private final ApiResponseParser parser = new ApiResponseParser();
  private final UrlReader reader = new UrlReader();
  private final String hostName;

  public ApiClient(final String apiToken) {
    this(VK_HOST, apiToken);
  }

  public ApiClient(final String hostName, final String apiToken) {
    this.hostName = hostName;
    this.apiToken = apiToken;
  }

  public static ApiClient fromEnv(final String variable) {
    return new ApiClient(System.getenv(variable));
  }

  public static ApiClient fromEnv() {
    return fromEnv(API_TOKEN_VARIABLE);
  }

  //  startTime exclusive, endTime inclusive
  public List<Instant> getPosts(final String hashtag, final Instant startTime, final Instant endTime) {
    var response = reader.readAsText(createUrl(hashtag, startTime, endTime));
    return parser.parseResponse(response);
  }

  private String createUrl(final String hashtag, final Instant startTime, final Instant endTime) {
    return String.format(URL_FORMAT,
            hostName,
            URLEncoder.encode(hashtag, StandardCharsets.UTF_8),
            apiToken,
            //  +1 because startTime is exclusive
            startTime.getEpochSecond() + 1,
            endTime.getEpochSecond(),
            API_VERSION);
  }
}
