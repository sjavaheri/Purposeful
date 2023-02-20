package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.URL;

public class URLDTO {

  private String id;
  private String url;
  private boolean isPresetIcon;

  public URLDTO(URL url) {
    this.id = url.getId();
    this.url = url.getURL();
    this.isPresetIcon = url.isPresetIcon();
  }

  public String getId() {
    return id;
  }


  public String getURL() {
    return url;
  }


  public boolean isPresetIcon() {
    return isPresetIcon;
  }

}
