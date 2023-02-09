package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
//import java.sql.Date;
//import java.sql.Time;

/**
 * The Technology class, the model for all Technologies in the database
 */
@Entity
public class Reaction {

  // ------------------------
  // Enumerations
  // ------------------------

  public enum ReactionType {HighFive}

  // ------------------------
  // Reaction Attributes
  // ------------------------

  private String id;

  private ReactionType reactionType;

  private Date date;

  //private Time time;

  private String name;

  // ------------------------
  // Reaction Constructor
  // ------------------------

  public Reaction() {

  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
