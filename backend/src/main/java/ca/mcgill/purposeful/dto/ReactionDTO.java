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
  private String email;

  public ReactionDTO() {}

  public ReactionDTO(Reaction reaction) {
    this.reactionType = reaction.getReactionType();
    this.date = reaction.getDate();
    this.idea_id = reaction.getIdea().getId();
    this.email = reaction.getRegularUser().getAppUser().getEmail();
  }

  public ReactionDTO(Date date, ReactionType reactionType, String idea_id, String email) {
    this.reactionType = reactionType;
    this.date = date;
    this.idea_id = idea_id;
    this.email = email;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ReactionType getReactionType() {
    return this.reactionType;
  }

  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  public Date getDate() {
    return this.date;
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

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
