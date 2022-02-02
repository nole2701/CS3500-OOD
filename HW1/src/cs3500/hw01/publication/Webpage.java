package cs3500.hw01.publication;

/**
 * Represents bibliographic information for webpages.
 */
public class Webpage implements Publication {
  private final String title;
  private final String url;
  private final String retrieved;

  /**
   * Constructs a {@code Webpage} object.
   *
   * @param title title of the webpage
   * @param url url of the webpage
   * @param retrieved when the webpage was accessed
   */

  public Webpage(String title, String url, String retrieved) {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }

  /**
   * Method for citing the webpage APA style.
   * @return returns a string of the citation
   */
  @Override
  public String citeApa() {
    return String.format("%s. Retrieved %s, from %s." , title, retrieved, url);
    //return title + ". " + "Retrieved " + retrieved + ", from " + url + ".";
  }

  /**
   * Method for citing the webpage MLA style.
   * @return returns a string of the citation
   */

  @Override
  public String citeMla() {
    return String.format("\"%s.\" Web. %s <%s>.", title, retrieved, url);
  }
}
