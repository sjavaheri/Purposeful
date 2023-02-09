package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
//import java.sql.Date;
//import java.sql.Time;

/**
 * The Idea class, the model for all ideas in the database
 */
@Entity
public class Idea {

  // ------------------------
  // Idea Attributes
  // ------------------------

  private String id;

  private Date date;

  //private Time time;

  private String title;

  private String purpose;

  private String description;

  private boolean isPaid;

  private boolean isInProgress;

  private boolean isPrivate;

  // ------------------------
  // Idea Constructor
  // ------------------------

  public Idea() {

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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

//  public Time getTime() {
//    return time;
//  }
//
//  public void setTime(Time time) {
//    this.time = time;
//  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public boolean isInProgress() {
    return isInProgress;
  }

  public void setInProgress(boolean inProgress) {
    isInProgress = inProgress;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }
}
