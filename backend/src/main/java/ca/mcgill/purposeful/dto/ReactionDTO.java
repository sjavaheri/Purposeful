package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Data transfer object for the Reaction class
 */
public class ReactionDTO {
  private String id;
  private ReactionType reactionType;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private String idea_id;
  private String user_id;

  /**
   * Default constructor.
   */
  public ReactionDTO() {}

    /**
     * Constructor.
     * @param reaction the reaction to convert to DTO
     */
  public ReactionDTO(Reaction reaction) {
    this.reactionType = reaction.getReactionType();
    this.date = reaction.getDate();
    this.idea_id = reaction.getIdea().getId();
    this.user_id = reaction.getRegularUser().getId();
  }

    /**
     * Constructor.
     * @param date the date of the reaction
     * @param reactionType the type of the reaction
     * @param idea_id the id of the idea
     * @param user_id the id of the user
     */
  public ReactionDTO(Date date, ReactionType reactionType, String idea_id, String user_id) {
    this.reactionType = reactionType;
    this.date = date;
    this.idea_id = idea_id;
    this.user_id = user_id;
  }

  /**
   * Returns the id of the reaction.
   * @return the id of the reaction
   */
  public String getId() {
    return id;
  }

    /**
     * Sets the id of the reaction.
     * @param id the id of the reaction
     */
  public void setId(String id) {
    this.id = id;
  }

    /**
     * Returns the type of the reaction.
     * @return the type of the reaction
     */
  public ReactionType getReactionType() {
    return reactionType;
  }

    /**
     * Sets the type of the reaction.
     * @param reactionType the type of the reaction
     */
  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

    /**
     * Returns the date of the reaction.
     * @return the date of the reaction
     */
  public Date getDate() {
    return date;
  }

    /**
     * Sets the date of the reaction.
     * @param date the date of the reaction
     */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * Returns the id of the idea.
   * @return the id of the idea
   */
  public String getIdea_id() {
    return idea_id;
  }

    /**
     * Sets the id of the idea.
     * @param idea_id the id of the idea
     */
  public void setIdea_id(String idea_id) {
    this.idea_id = idea_id;
  }

    /**
     * Returns the id of the user.
     * @return the id of the user
     */
  public String getUser_id() {
    return user_id;
  }

    /**
     * Sets the id of the user.
     * @param user_id the id of the user
     */
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
}
