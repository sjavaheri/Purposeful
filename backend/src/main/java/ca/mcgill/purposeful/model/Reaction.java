package ca.mcgill.purposeful.model;

import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** The Reaction class, the model for all reactions in the database */
@Entity
public class Reaction {

  // ------------------------
  // Enumerations
  // ------------------------

  /** The ReactionType enum, the model for all reaction types in the database */
  public enum ReactionType {
    /** High Five reaction */
    HighFive
  }

  // ------------------------
  // Reaction Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false)
  private ReactionType reactionType;

  @Column(nullable = false)
  private Date date;

  // private Time time;

  // ------------------------
  // Reaction Associations
  // ------------------------

  @ManyToOne(optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "idea_id", nullable = false)
  private Idea idea;

  @ManyToOne(optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_id", nullable = false)
  private RegularUser regularUser;

  // ------------------------
  // Reaction Constructor
  // ------------------------

  /** The Reaction constructor, the constructor for all reactions in the database */
  public Reaction() {}

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  /**
   * Gets the id of the reaction
   *
   * @return id of the reaction
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the reaction
   *
   * @param id of the reaction
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * Gets the reaction type of the reaction
   *
   * @return reactionType of the reaction
   */
  public ReactionType getReactionType() {
    return reactionType;
  }

  /**
   * Sets the reaction type of the reaction
   *
   * @param reactionType of the reaction
   */
  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  /**
   * Gets the date of the reaction
   *
   * @return date of the reaction
   */
  public Date getDate() {
    return date;
  }

  /**
   * Sets the date of the reaction
   *
   * @param date of the reaction
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * Gets the idea of the reaction
   *
   * @return idea of the reaction
   */
  public Idea getIdea() {
    return idea;
  }

  /**
   * Sets the idea of the reaction
   *
   * @param idea of the reaction
   */
  public void setIdea(Idea idea) {
    this.idea = idea;
  }

  /**
   * Gets the regular user of the reaction
   *
   * @return regularUser of the reaction
   */
  public RegularUser getRegularUser() {
    return regularUser;
  }

  /**
   * Sets the regular user of the reaction
   *
   * @param regularUser of the reaction
   */
  public void setRegularUser(RegularUser regularUser) {
    this.regularUser = regularUser;
  }
}
