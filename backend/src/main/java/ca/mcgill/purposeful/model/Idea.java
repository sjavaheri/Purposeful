package ca.mcgill.purposeful.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
// import java.sql.Date;
// import java.sql.Time;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** The Idea class, the model for all ideas in the database */
@Entity
public class Idea {

  // ------------------------
  // Idea Attributes
  // ------------------------

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(nullable = false)
  private Date date;

  // private Time time;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String purpose;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private boolean isPaid;

  @Column(nullable = false)
  private boolean isInProgress;

  @Column(nullable = false)
  private boolean isPrivate;

  // ------------------------
  // Idea Associations
  // ------------------------

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "idea_domain", joinColumns = @JoinColumn(name = "idea_id"), inverseJoinColumns = @JoinColumn(name = "url_id"))
  private Set<Domain> domains;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "idea_topic", joinColumns = @JoinColumn(name = "idea_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
  private Set<Topic> topics;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "idea_technology", joinColumns = @JoinColumn(name = "idea_id"), inverseJoinColumns = @JoinColumn(name = "technology_id"))
  private Set<Technology> techs;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "icon_url_id", nullable = false)
  private URL iconUrl;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "supported_idea_id", nullable = true)
  private List<URL> supportingImageUrls;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_id", nullable = false)
  private RegularUser user;

  // ------------------------
  // Idea Constructor
  // ------------------------

  /**
   * Idea constructor
   */
  public Idea() {
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------
  /**
   * Get the id of the idea
   *
   * @return the id of the idea
   */
  public String getId() {
    return id;
  }

  /**
   * Set the id of the idea
   * @param id the id of the idea
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   *  Get the date of the idea
   * @return the date of the idea
   */
  public Date getDate() {
    return date;
  }

  /**
   * Set the date of the idea
   * @param date the date of the idea
   */
  public void setDate(Date date) {
    this.date = date;
  }

  // public Time getTime() {
  // return time;
  // }
  //
  // public void setTime(Time time) {
  // this.time = time;
  // }

  /**
   * Get the title of the idea
   * @return the title of the idea
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the title of the idea
   * @param title the title of the idea
   */
  public void setTitle(String title) {
    this.title = title;
  }

    /**
     * Get the purpose of the idea
     * @return the purpose of the idea
     */
  public String getPurpose() {
    return purpose;
  }

    /**
     * Set the purpose of the idea
     * @param purpose the purpose of the idea
     */
  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

    /**
     * Get the description of the idea
     * @return the description of the idea
     */
  public String getDescription() {
    return description;
  }

    /**
     * Set the description of the idea
     * @param description the description of the idea
     */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Check if the idea is paid
   * @return true if the idea is paid, false otherwise
   */
  public boolean isPaid() {
    return isPaid;
  }

    /**
     * Set the idea to paid or not paid
     * @param paid true if the idea is paid, false otherwise
     */
  public void setPaid(boolean paid) {
    isPaid = paid;
  }

    /**
     * Check if the idea is in progress
     * @return true if the idea is in progress, false otherwise
     */
  public boolean isInProgress() {
    return isInProgress;
  }

    /**
     * Set the idea to in progress or not in progress
     * @param inProgress true if the idea is in progress, false otherwise
     */
  public void setInProgress(boolean inProgress) {
    isInProgress = inProgress;
  }

    /**
     * Check if the idea is private
     * @return true if the idea is private, false otherwise
     */
  public boolean isPrivate() {
    return isPrivate;
  }

    /**
     * Set the idea to private or not private
     * @param aPrivate true if the idea is private, false otherwise
     */
  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

  /**
   * Get Domains of the idea
   * @return the domains of the idea
   */
  public Set<Domain> getDomains() {
    return domains;
  }

    /**
     * Set the domains of the idea
     * @param domains the domains of the idea
     */
  public void setDomains(Set<Domain> domains) {
    this.domains = domains;
  }

    /**
     * Get the topics of the idea
     * @return the topics of the idea
     */
  public Set<Topic> getTopics() {
    return topics;
  }

    /**
     * Set the topics of the idea
     * @param topics the topics of the idea
     */
  public void setTopics(Set<Topic> topics) {
    this.topics = topics;
  }

    /**
     * Get the technologies of the idea
     * @return the technologies of the idea
     */
  public Set<Technology> getTechs() {
    return techs;
  }

    /**
     * Set the technologies of the idea
     * @param techs the technologies of the idea
     */
  public void setTechs(Set<Technology> techs) {
    this.techs = techs;
  }

  /**
   * Get the icon url of the idea
   * @return the icon url of the idea
   */
  public URL getIconUrl() {
    return iconUrl;
  }

    /**
     * Set the icon url of the idea
     * @param iconUrl the icon url of the idea
     */
  public void setIconUrl(URL iconUrl) {
    this.iconUrl = iconUrl;
  }

    /**
     * Get the supporting image urls of the idea
     * @return the supporting image urls of the idea
     */
  public List<URL> getSupportingImageUrls() {
    return supportingImageUrls;
  }

    /**
     * Set the supporting image urls of the idea
     * @param supportingImageUrls the supporting image urls of the idea
     */
  public void setSupportingImageUrls(List<URL> supportingImageUrls) {
    this.supportingImageUrls = supportingImageUrls;
  }

    /**
     * Get the user of the idea
     * @return the user of the idea
     */
  public RegularUser getUser() {
    return user;
  }

    /**
     * Set the user of the idea
     * @param user the user of the idea
     */
  public void setUser(RegularUser user) {
    this.user = user;
  }
}
