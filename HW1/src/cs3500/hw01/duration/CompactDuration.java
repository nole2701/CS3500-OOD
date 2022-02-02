package cs3500.hw01.duration;

/**
 * Durations represented compactly, with a range of 0 to
 * 2<sup>63</sup>-1 seconds.
 */
public final class CompactDuration extends AbstractDuration {
  /**
   * Constructs a duration in terms of its length in hours, minutes, and
   * seconds.
   *
   * @param hours the number of hours
   * @param minutes the number of minutes
   * @param seconds the number of inSeconds
   * @throws IllegalArgumentException if any argument is negative
   */
  public CompactDuration(int hours, int minutes, int seconds) {
    ensureHms(hours, minutes, seconds);
    this.inSeconds = inSeconds(hours, minutes, seconds);
  }

  /**
   * Constructs a duration in terms of its length in seconds.
   *
   * @param inSeconds the number of seconds (non-negative)
   * @throws IllegalArgumentException {@code inSeconds} is negative
   */
  public CompactDuration(long inSeconds) {
    if (inSeconds < 0) {
      throw new IllegalArgumentException("must be non-negative");
    }

    this.inSeconds = inSeconds;
  }

  private final long inSeconds;

  @Override
  protected Duration fromSeconds(long seconds) {
    return new CompactDuration(seconds);
  }

  /**
   * Implementation of the format method described in the Duration interface for CompactDuration.
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
          result += hoursOf(this.inSeconds);
        } else if (next.equals("H")) {
          if (hoursOf(this.inSeconds) < 10) {
            result += "0" + hoursOf(this.inSeconds);
          } else {
            result += hoursOf(this.inSeconds);
          }
        } else if (next.equals("m")) {
          result += minutesOf(this.inSeconds);
        } else if (next.equals("M")) {
          if (minutesOf(this.inSeconds) < 10) {
            result += "0" + minutesOf(this.inSeconds);
          } else {
            result += minutesOf(this.inSeconds);
          }
        } else if (next.equals("s")) {
          result += secondsOf(this.inSeconds);
        } else if (next.equals("S")) {
          if (secondsOf(this.inSeconds) < 10) {
            result += "0" + secondsOf(this.inSeconds);
          } else {
            result += secondsOf(this.inSeconds);
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
    return inSeconds;
  }

  @Override
  public String asHms() {
    return String.format("%d:%02d:%02d",
                          hoursOf(inSeconds),
                          minutesOf(inSeconds),
                          secondsOf(inSeconds));
  }
}
