package ru.ifmo.rain.golikov.clock;

import java.time.Instant;

public class SetableClock implements Clock {
  private Instant now;

  public SetableClock(final Instant now) {
    this.now = now;
  }

  public void setNow(final Instant now) {
    this.now = now;
  }

  @Override
  public Instant now() {
    return now;
  }
}
