package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction.ReactionType;

/**
 * Data transfer object for the Reaction class
 */
public class ReactionRequestDTO {

  private String idea_id;
  private String user_id;
  private ReactionType reactionType;

  /**
   * Default constructor.
   */
  public ReactionRequestDTO() {}

  /**
   * Constructor.
   *
   * @param idea_id Idea id
   * @param user_id User id
   * @param reactionType Reaction type
   */
  public ReactionRequestDTO(String idea_id, String user_id, ReactionType reactionType) {
    this.idea_id = idea_id;
    this.user_id = user_id;
    this.reactionType = reactionType;
  }

  /**
   * Get reaction type.
   * @return Reaction type
   */
  public ReactionType getReactionType() {
    return reactionType;
  }

    /**
     * Set reaction type.
     * @param reactionType Reaction type
     */
  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  /**
   * Get idea id.
   * @return Idea id
   */
  public String getIdea_id() {
    return idea_id;
  }

    /**
     * Set idea id.
     * @param idea_id Idea id
     */
  public void setIdea_id(String idea_id) {
    this.idea_id = idea_id;
  }

    /**
     * Get user id.
     * @return User id
     */
  public String getUser_id() {
    return user_id;
  }

    /**
     * Set user id.
     * @param user_id User id
     */
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
}
