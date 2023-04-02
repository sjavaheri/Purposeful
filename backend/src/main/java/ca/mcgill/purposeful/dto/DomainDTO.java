package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Domain;

/**
 * Data transfer object for the Domain class.
 */
public class DomainDTO {

  private String id;
  private String name;

  /**
   * Default constructor for DomainDTO.
   */
  public DomainDTO() {}

    /**
     * Constructor for DomainDTO.
     *
     * @param domain
     *          the domain to be converted to a DomainDTO
     */
  public DomainDTO(Domain domain) {
    this.id = domain.getId();
    this.name = domain.getName();
  }

  /**
   * Get the id of the domain.
   * @return the id of the domain
   */
  public String getId() {
    return id;
  }

    /**
     * Get the name of the domain.
     * @return the name of the domain
     */
  public String getName() {
    return name;
  }
}
