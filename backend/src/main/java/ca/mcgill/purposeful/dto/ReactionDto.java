package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction.ReactionType;
import java.util.Date;

public class ReactionDto {

  private String id;
  private ReactionType reactionType;
  private Date date;

  public ReactionDto() {
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
}
