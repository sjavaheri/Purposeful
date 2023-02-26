package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction.ReactionType;

public class ReactionRequestDTO {

  private String idea_id;
  private String user_id;
  private ReactionType reactionType;

  public ReactionRequestDTO() {
  }

  public ReactionRequestDTO(
      String idea_id, String user_id, ReactionType reactionType) {
    this.idea_id = idea_id;
    this.user_id = user_id;
    this.reactionType = reactionType;
  }

  public ReactionType getReactionType() {
    return reactionType;
  }

  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  public String getIdea_id() {
    return idea_id;
  }

  public void setIdea_id(String idea_id) {
    this.idea_id = idea_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
}
