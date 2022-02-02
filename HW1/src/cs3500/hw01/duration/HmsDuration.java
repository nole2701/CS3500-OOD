package cs3500.hw01.duration;

/**
 * Durations represented as hours, minutes, and seconds.
 */
public final class HmsDuration extends AbstractDuration {
  /**
   * Constructs a duration in terms of its length in hours, minutes, and
   * seconds.
   *
   * @param hours the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of seconds
   * @throws IllegalArgumentException if any argument is negative
   */
  public HmsDuration(int hours, int minutes, int seconds) {
    this(inSeconds(hours, minutes, seconds));
    ensureHms(hours, minutes, seconds);
  }

  /**
   * Constructs a duration in terms of its length in seconds.
   *
   * @param inSeconds the number of seconds (non-negative)
   * @throws IllegalArgumentException {@code inSeconds} is negative
   */
  public HmsDuration(long inSeconds) {
    if (inSeconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }

    seconds = secondsOf(inSeconds);
    minutes = minutesOf(inSeconds);
    hours   = hoursOf(inSeconds);
  }

  private final int hours;
  private final int minutes;
  private final int seconds;

  @Override
  protected AbstractDuration fromSeconds(long seconds) {
    return new HmsDuration(seconds);
  }

  /**
   * Implementation of the format method described in the Duration interface for HmsDuration.
   *
   * @param template the template of which to format this Duration as
   * @return returns the string of the formatted Duration
   */
  @Override
  public String format(String template) {
    String result = "";

    for (int i = 0; i < template.length(); i++) {
      String c = String.valueOf(template.charAt(i));

      if (c.equals("%")) {
        if (i == template.length() - 1) {
          throw new IllegalArgumentException("Invalid format modifier. Ends with %");
        }
        String next = String.valueOf(template.charAt(i + 1));

        if (next.equals("t")) {
          result += this.inSeconds();
        } else if (next.equals("h")) {
          result += this.hours;
        } else if (next.equals("H")) {
          if (this.hours < 10) {
            result += "0" + this.hours;
          } else {
            result += this.hours;
          }
        } else if (next.equals("m")) {
          result += this.minutes;
        } else if (next.equals("M")) {
          if (this.minutes < 10) {
            result += "0" + this.minutes;
          } else {
            result += this.minutes;
          }
        } else if (next.equals("s")) {
          result += this.seconds;
        } else if (next.equals("S")) {
          if (this.seconds < 10) {
            result += "0" + this.seconds;
          } else {
            result += this.seconds;
          }
        } else if (next.equals("%")) {
          result += "%";
        } else {
          throw new IllegalArgumentException("Invalid format modifier");
        }
        i++;

      } else {
        result = result + c;
      }

    }

    return result;
  }

  @Override
  public long inSeconds() {
    return inSeconds(hours, minutes, seconds);
  }

  @Override
  public String asHms() {
    return asHms(hours, minutes, seconds);
  }
}
