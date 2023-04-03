package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.URL;

/**
 * Data transfer object for the URL class.
 */
public class URLDTO {

  private String id;
  private String url;
  private boolean presetIcon;

  /**
   * Default constructor.
   */
  public URLDTO() {}

    /**
     * Constructor.
     *
     * @param url
     *          the URL to be converted to a DTO
     */
  public URLDTO(URL url) {
    this.id = url.getId();
    this.url = url.getURL();
    this.presetIcon = url.isPresetIcon();
  }

  /**
   * Get the id of the URL.
   * @return  the id of the URL
   */
  public String getId() {
    return id;
  }

    /**
     * Get the URL.
     * @return  the URL
     */
  public String getURL() {
    return url;
  }

    /**
     * Get the preset icon.
     * @return  the preset icon
     */
  public boolean isPresetIcon() {
    return presetIcon;
  }
}
