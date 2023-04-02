package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction.ReactionType;

public class ReactionRequestDTO {

  private String idea_id;
  private String email;
  private ReactionType reactionType;

  public ReactionRequestDTO() {}

  public ReactionRequestDTO(String idea_id, String email, ReactionType reactionType) {
    this.idea_id = idea_id;
    this.email = email;
    this.reactionType = reactionType;
  }

  public ReactionType getReactionType() {
    return this.reactionType;
  }

  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  public String getIdea_id() {
    return this.idea_id;
  }

  public void setIdea_id(String idea_id) {
    this.idea_id = idea_id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
