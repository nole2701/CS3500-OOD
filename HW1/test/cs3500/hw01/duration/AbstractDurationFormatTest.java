package cs3500.hw01.duration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests for the format method of {@link Duration}s. 
    Add your tests to this class to assure that your format 
    method works properly
*/
public abstract class AbstractDurationFormatTest {
  @Test
  public void formatExample1() {
    assertEquals("4 hours, 0 minutes, and 9 seconds",
                  hms(4, 0, 9)
                    .format("%h hours, %m minutes, and %s seconds"));
  }

  @Test
  public void formatExample2() {
    assertEquals("4:05:17",
                  hms(4, 5, 17).format("%h:%M:%S"));
  }

  // ADD MORE TESTS HERE. 
  // THE ABOVE TEST NAMES ARE MERE PLACEHOLDERS. NAME YOUR TESTS MEANINGFULLY. 
  // IF YOU NAME YOUR TESTS SIMILAR TO ABOVE, YOU WILL LOSE POINTS!
  // Your tests must only use hms(...) and sec(...) to construct new Durations
  // and must *not* directly say "new CompactDuration(...)" or
  // "new HmsDuration(...)"

  Duration testhms0 = hms(0,0,0);
  Duration testhms1 = hms(5,2,3);
  Duration testhms2 = hms(15,42,30);
  Duration testsec = sec(18123);

  //testing format for %t
  @Test
  public void hmsformatt() {
    assertEquals("18123", testhms1.format("%t"));
  }

  //------------------------------------------------------------------------------------------------

  //testing format for %t in a zero case
  @Test
  public void hmsformatzerot() {
    assertEquals("0", testhms0.format("%t"));
  }

  //testing format for %h
  @Test
  public void hmsformath() {
    assertEquals("5", testhms1.format("%h"));
  }

  //testing format for %h in a zero case
  @Test
  public void hmsformatzeroh() {
    assertEquals("0", testhms0.format("%h"));
  }

  //testing format for %H for 0 padding
  @Test
  public void hmsformatH1() {
    assertEquals("05", testhms1.format("%H"));
  }

  //testing format for %H
  @Test
  public void hmsformatH2() {
    assertEquals("15", testhms2.format("%H"));
  }

  //testing format for %H in a zero case
  @Test
  public void hmsformatzeroH() {
    assertEquals("00", testhms0.format("%H"));
  }

  //------------------------------------------------------------------------------------------------

  //testing format for %m
  @Test
  public void hmsformatm() {
    assertEquals("2", testhms1.format("%m"));
  }

  //testing format for %m in a zero case
  @Test
  public void hmsformatzerom() {
    assertEquals("0", testhms0.format("%m"));
  }

  //testing format for %M for 0 padding
  @Test
  public void hmsformatM1() {
    assertEquals("02", testhms1.format("%M"));
  }

  //testing format for %M
  @Test
  public void hmsformatM2() {
    assertEquals("42", testhms2.format("%M"));
  }

  //testing format for %M in a zero case
  @Test
  public void hmsformatzeroM() {
    assertEquals("00", testhms0.format("%M"));
  }

  //------------------------------------------------------------------------------------------------

  //testing format for %s
  @Test
  public void hmsformats() {
    assertEquals("3", testhms1.format("%s"));
  }

  //testing format for %s in a zero case
  @Test
  public void hmsformatzeros() {
    assertEquals("0", testhms0.format("%s"));
  }

  //testing format for %S for 0 padding
  @Test
  public void hmsformatS1() {
    assertEquals("03", testhms1.format("%S"));
  }

  //testing format for %S
  @Test
  public void hmsformatS2() {
    assertEquals("30", testhms2.format("%S"));
  }

  //testing format for %S in a zero case
  @Test
  public void hmsformatzeroS() {
    assertEquals("00", testhms0.format("%S"));
  }

  //------------------------------------------------------------------------------------------------

  //testing format for the % specifier
  @Test
  public void hmsformatpercent() {
    assertEquals("%", testhms1.format("%%"));
  }

  //testing format to get "hh:mm:ss"
  @Test
  public void hmsformatFull1() {
    assertEquals("05:02:03", testhms1.format("%H:%M:%S"));
  }

  //testing format to get "hh:mm:ss"
  @Test
  public void hmsformatFull2() {
    assertEquals("15:42:30", testhms2.format("%H:%M:%S"));
  }

  //testing format to get "hh:mm:ss" in a zero case
  @Test
  public void hmsformatFullZero() {
    assertEquals("00:00:00", testhms0.format("%H:%M:%S"));
  }

  //------------------------------------------------------------------------------------------------

  //testing format for %t
  @Test
  public void secformat1() {
    assertEquals("18123", testsec.format("%t"));
  }

  //testing format for %h
  @Test
  public void secformat2() {
    assertEquals("5", testsec.format("%h"));
  }

  //testing format for %H for 0 padding
  @Test
  public void secformat3() {
    assertEquals("05", testsec.format("%H"));
  }

  //testing format for %m
  @Test
  public void secformat4() {
    assertEquals("2", testsec.format("%m"));
  }

  //testing format for %M for 0 padding
  @Test
  public void secformat5() {
    assertEquals("02", testsec.format("%M"));
  }

  //testing format for %s
  @Test
  public void secformat6() {
    assertEquals("3", testsec.format("%s"));
  }

  //testing format for %S for 0 padding
  @Test
  public void secformat7() {
    assertEquals("03", testsec.format("%S"));
  }

  //testing format for the % specifier
  @Test
  public void secformatpercent() {
    assertEquals("%", testsec.format("%%"));
  }

  //------------------------------------------------------------------------------------------------

  //testing for more unique combinations
  @Test
  public void hmsformatunique() {
    assertEquals("%5% asdf 505 2m03.", testhms1.format("%%%h%% asdf %h%H %mm%S."));
  }

  @Test
  public void secformatunique() {
    assertEquals("%5% asdf 505 2m03.", testsec.format("%%%h%% asdf %h%H %mm%S."));
  }

  //testing for invalid format modifiers
  @Test(expected = IllegalArgumentException.class)
  public void formatinvalid1() {
    String badformat = testhms1.format("%H%8");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatinvalid2() {
    String badformat = testhms1.format("%H%M%J");
  }

  //testing if it ends with %
  @Test(expected = IllegalArgumentException.class)
  public void formatendpercent1() {
    String badformat = testhms1.format("%h%");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatendpercent2() {
    String badformat = testhms1.format("%");
  }

  @Test(expected = IllegalArgumentException.class)
  public void formatendpercent3() {
    String badformat = testhms1.format("%%%");
  }


  

  /*
    Leave this section alone: It contains two abstract methods to
    create Durations, and concrete implementations of this testing class
    will supply particular implementations of Duration to be used within 
    your tests.
   */
  /**
   * Constructs an instance of the class under test representing the duration
   * given in hours, minutes, and seconds.
   *
   * @param hours the hours in the duration
   * @param minutes the minutes in the duration
   * @param seconds the seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration hms(int hours, int minutes, int seconds);

  /**
   * Constructs an instance of the class under test representing the duration
   * given in seconds.
   *
   * @param inSeconds the total seconds in the duration
   * @return an instance of the class under test
   */
  protected abstract Duration sec(long inSeconds);

  /**
   * Concrete class for testing HmsDuration implementation of Duration.
   */
  public static final class HmsDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new HmsDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new HmsDuration(inSeconds);
    }
  }

  /**
   * Concrete class for testing CompactDuration implementation of Duration.
   */
  public static final class CompactDurationTest extends AbstractDurationFormatTest {
    @Override
    protected Duration hms(int hours, int minutes, int seconds) {
      return new CompactDuration(hours, minutes, seconds);
    }

    @Override
    protected Duration sec(long inSeconds) {
      return new CompactDuration(inSeconds);
    }
  }
}
