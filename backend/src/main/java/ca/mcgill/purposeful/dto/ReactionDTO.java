package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReactionDTO {

  private String id;
  private ReactionType reactionType;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private String idea_id;
  private String user_id;

  public ReactionDTO() {}

  public ReactionDTO(Reaction reaction) {
    this.reactionType = reaction.getReactionType();
    this.date = reaction.getDate();
    this.idea_id = reaction.getIdea().getId();
    this.user_id = reaction.getRegularUser().getId();
  }

  public ReactionDTO(
      String id, ReactionType reactionType, Date date, String idea_id, String user_id) {
    this.id = id;
    this.reactionType = reactionType;
    this.date = date;
    this.idea_id = idea_id;
    this.user_id = user_id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ReactionType getReactionType() {
    return reactionType;
  }

  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
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
