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

  public ReactionDTO(Reaction reaction) {
    this.reactionType = reaction.getReactionType();
    this.date = reaction.getDate();
  }

  public ReactionDTO(String id, ReactionType reactionType, Date date) {
    this.id = id;
    this.reactionType = reactionType;
    this.date = date;
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
