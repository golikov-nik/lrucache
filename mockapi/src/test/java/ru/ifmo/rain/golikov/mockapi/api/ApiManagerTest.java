package ru.ifmo.rain.golikov.mockapi.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ifmo.rain.golikov.mockapi.TestUtils.makeInstants;

public class ApiManagerTest {
  private ApiManager manager;

  @Mock
  private ApiClient client;

  private AutoCloseable closeable;

  @Before
  public void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    manager = new ApiManager(client);
  }

  @After
  public void finishTest() throws Exception {
    closeable.close();
  }

  @Test
  public void testCountPosts() {
    final String hashtag = "#food";
    final Instant endTime = Instant.ofEpochSecond(18600);
    final Instant startTime = Instant.ofEpochSecond(4200);
    when(client.getPosts(hashtag, startTime, endTime))
            .thenReturn(createAnswer());

    final int[] expectedAnswer = new int[]{4, 1, 2, 0};
    assertThat(manager.countPosts(hashtag, 4, endTime))
            .isEqualTo(expectedAnswer);
  }

  private List<Instant> createAnswer() {
    return makeInstants(List.of(4201L, 4201L, 5000L, 4203L, 15000L, 13000L, 11400L));
  }

  @Test
  public void testInvalidHours() {
    testInvalid(27);
    testInvalid(0);
    testInvalid(-1);
  }

  private void testInvalid(final int hoursCount) {
    try {
      manager.countPosts("lalala", hoursCount, Instant.ofEpochSecond(566));
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (final IllegalArgumentException e) {
      assertThat(e).hasMessage("Number of hours passed should be between 1 and 24");
    }
  }
}
