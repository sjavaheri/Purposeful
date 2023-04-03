package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction.ReactionType;

/** Data transfer object for the Reaction class */
public class ReactionRequestDTO {

  private String idea_id;
  private String email;
  private ReactionType reactionType;

  /** Default constructor. */
  public ReactionRequestDTO() {}

  /**
   * Constructor.
   *
   * @param idea_id The id of the idea
   * @param email The email of the user
   * @param reactionType The type of the reaction
   */
  public ReactionRequestDTO(String idea_id, String email, ReactionType reactionType) {
    this.idea_id = idea_id;
    this.email = email;
    this.reactionType = reactionType;
  }

  /**
   * Get reaction type.
   *
   * @return Reaction type
   */
  public ReactionType getReactionType() {
    return this.reactionType;
  }

  /**
   * Set reaction type.
   *
   * @param reactionType Reaction type
   */
  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  /**
   * Get idea id.
   *
   * @return Idea id
   */
  public String getIdea_id() {
    return this.idea_id;
  }

  /**
   * Set idea id.
   *
   * @param idea_id Idea id
   */
  public void setIdea_id(String idea_id) {
    this.idea_id = idea_id;
  }

  /**
   * Get email.
   *
   * @return Email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Set email.
   *
   * @param email Email
   */
  public void setEmail(String email) {
    this.email = email;
  }
}
