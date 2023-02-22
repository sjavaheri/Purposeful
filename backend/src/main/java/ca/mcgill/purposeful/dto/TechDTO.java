package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Technology;

public class TechDTO {

  private String id;
  private String name;

  public TechDTO() {}
  public TechDTO(Technology tech) {
    this.id = tech.getId();
    this.name = tech.getName();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
