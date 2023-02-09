package ca.mcgill.purposeful.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.Set;
//import java.sql.Date;
//import java.sql.Time;

/**
 * The Idea class, the model for all ideas in the database
 */
@Entity
public class Idea {

  // ------------------------
  // Idea Associations
  // ------------------------

  @ManyToMany
  @JoinTable(
      name = "idea_domain",
      joinColumns = @JoinColumn(name = "idea_id"),
      inverseJoinColumns = @JoinColumn(name = "url_id"))
  private Set<Domain> domains;

  @ManyToMany
  @JoinTable(
      name = "idea_topic",
      joinColumns = @JoinColumn(name = "idea_id"),
      inverseJoinColumns = @JoinColumn(name = "topic_id"))
  private Set<Topic> topics;

  @ManyToMany
  @JoinTable(
      name = "idea_technology",
      joinColumns = @JoinColumn(name = "idea_id"),
      inverseJoinColumns = @JoinColumn(name = "technology_id"))
  private Set<Technology> techs;

  @ManyToOne(optional = false)
  private URL iconUrl;

  @ManyToOne(optional = false)
  private RegularUser user;

  // ------------------------
  // Idea Attributes
  // ------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public Set<Domain> getDomains() {
    return domains;
  }

  public void setDomains(Set<Domain> domains) {
    this.domains = domains;
  }

  public Set<Topic> getTopics() {
    return topics;
  }

  public void setTopics(Set<Topic> topics) {
    this.topics = topics;
  }

  public Set<Technology> getTechs() {
    return techs;
  }

  public void setTechs(Set<Technology> techs) {
    this.techs = techs;
  }

  public URL getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(URL iconUrl) {
    this.iconUrl = iconUrl;
  }

  public RegularUser getUser() {
    return user;
  }

  public void setUser(RegularUser user) {
    this.user = user;
  }
}
