package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Technology;

/** Data transfer object for the Technology class. */
public class TechDTO {

  private String id;
  private String name;

  /** Default constructor for TechDTO. */
  public TechDTO() {}

  /**
   * Constructor for TechDTO.
   *
   * @param tech Technology object to convert to DTO
   */
  public TechDTO(Technology tech) {
    this.id = tech.getId();
    this.name = tech.getName();
  }

  /**
   * Get the id of the technology.
   *
   * @return id of the technology
   */
  public String getId() {
    return id;
  }

  /**
   * Get the name of the technology.
   *
   * @return name of the technology
   */
  public String getName() {
    return name;
  }
}
