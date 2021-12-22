import org.junit.Before;
import org.junit.Test;
import ru.ifmo.rain.golikov.clock.SetableClock;
import ru.ifmo.rain.golikov.statistics.ClockEventStatistics;
import ru.ifmo.rain.golikov.statistics.EventStatistics;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ClockEventStatisticsTest {
  private static final double EPS = 1e-3;
  private SetableClock clock;
  private EventStatistics statistics;

  @Before
  public void setUp() {
    clock = new SetableClock(Instant.now());
    statistics = new ClockEventStatistics(clock);
  }

  @Test
  public void testSingle() {
    statistics.incEvent("abc");
    checkEvent("abc", 1);
  }

  @Test
  public void testHalfHour() {
    statistics.incEvent("abc");
    advance(Duration.ofMinutes(30));
    checkEvent("abc", 1);
  }

  @Test
  public void testTwoHours() {
    statistics.incEvent("abc");
    advance(Duration.ofHours(2));
    checkEvent("abc", 0);
  }

  @Test
  public void testMultiple() {
    statistics.incEvent("a");
    statistics.incEvent("b");
    statistics.incEvent("a");
    checkEvent("a", 2);
    checkEvent("b", 1);
    advance(Duration.ofMinutes(20));
    statistics.incEvent("c");
    statistics.incEvent("a");
    statistics.incEvent("a");
    checkEvent("a", 4);
    checkEvent("b", 1);
    checkEvent("c", 1);
    checkEvent("d", 0);
    advance(Duration.ofMinutes(41));
    checkEvent("a", 2);
    checkEvent("b", 0);
    checkEvent("c", 1);
    checkEvent("d", 0);
  }

  @Test
  public void testAllOnce() {
    statistics.incEvent("b");
    advance(Duration.ofMinutes(30));
    statistics.incEvent("a");
    statistics.incEvent("b");
    advance(Duration.ofMinutes(20));
    statistics.incEvent("a");
    advance(Duration.ofMinutes(11));

    //  a: 1, b: 2
    final var map = statistics.getAllEventStatistic();
    assertEquals(map.size(), 2);
    assertEquals(map.get("a"), 2 / 60.0, EPS);
    assertEquals(map.get("b"), 1 / 60.0, EPS);
  }

  private void advance(final Duration duration) {
    clock.setNow(clock.now().plus(duration));
  }

  private void checkEvent(final String name, final int requests) {
    assertEquals(statistics.getEventStatisticByName(name), requests / 60.0, EPS);
    if (requests == 0) {
      assertFalse(statistics.getAllEventStatistic().containsKey(name));
    } else {
      assertEquals(statistics.getAllEventStatistic().get(name), requests / 60.0, EPS);
    }
  }
}
