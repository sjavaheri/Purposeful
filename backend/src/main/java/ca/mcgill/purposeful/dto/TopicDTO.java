package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Topic;

/**
 * Data transfer object for the Topic class.
 */
public class TopicDTO {

  private String id;
  private String name;

  /**
   * Default constructor.
   */
  public TopicDTO() {}

    /**
     * Constructor.
     *
     * @param topic
     *          the topic to be converted to a DTO
     */
  public TopicDTO(Topic topic) {
    this.id = topic.getId();
    this.name = topic.getName();
  }

  /**
   * Get the id.
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Get the name.
   * @return the name
   */
  public String getName() {
    return name;
  }
}
