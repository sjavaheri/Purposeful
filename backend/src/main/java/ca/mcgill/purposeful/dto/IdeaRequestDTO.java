package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Data transfer object for the Idea class */
public class IdeaRequestDTO {

  private String id;
  private boolean isPaid;
  private boolean isPrivate;
  private boolean inProgress;
  private String title;
  private String purpose;
  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private List<String> domainIds;
  private List<String> techIds;
  private List<String> topicIds;
  private List<String> imgUrls;
  private String iconUrl;

  /** Default constructor for IdeaRequestDTO. */
  public IdeaRequestDTO() {}

  /**
   * Constructor for IdeaRequestDTO.
   *
   * @param idea the idea to be converted to IdeaRequestDTO
   */
  public IdeaRequestDTO(Idea idea) {
    this.id = idea.getId();
    this.isPaid = idea.isPaid();
    this.isPrivate = idea.isPrivate();
    this.inProgress = idea.isInProgress();
    this.title = idea.getTitle();
    this.purpose = idea.getPurpose();
    this.description = idea.getDescription();
    this.date = idea.getDate();
    this.domainIds = new ArrayList<>();
    this.techIds = new ArrayList<>();
    this.topicIds = new ArrayList<>();
    this.imgUrls = new ArrayList<>();

    for (Domain domain : idea.getDomains()) {
      this.domainIds.add(domain.getId());
    }
    for (Topic topic : idea.getTopics()) {
      this.topicIds.add(topic.getId());
    }
    for (Technology tech : idea.getTechs()) {
      this.techIds.add(tech.getId());
    }
    for (URL url : idea.getSupportingImageUrls()) {
      this.imgUrls.add(url.getURL());
    }

    this.iconUrl = idea.getIconUrl().getURL();
  }

  /**
   * Constructor for IdeaRequestDTO.
   *
   * @param id the id of the idea
   * @param title the title of the idea
   * @param purpose the purpose of the idea
   * @param descriptions the description of the idea
   * @param date the date of the idea
   * @param isPaid whether the idea is paid
   * @param inProgress whether the idea is in progress
   * @param isPrivate whether the idea is private
   * @param domains the domains of the idea
   * @param techs the technologies of the idea
   * @param topics the topics of the idea
   * @param imgUrls the image urls of the idea
   * @param iconUrl the icon url of the idea
   */
  public IdeaRequestDTO(
      String id,
      String title,
      String purpose,
      String descriptions,
      Date date,
      boolean isPaid,
      boolean inProgress,
      boolean isPrivate,
      List<String> domains,
      List<String> techs,
      List<String> topics,
      List<String> imgUrls,
      String iconUrl) {
    this.id = id;
    this.isPaid = isPaid;
    this.isPrivate = isPrivate;
    this.inProgress = inProgress;
    this.title = title;
    this.purpose = purpose;
    this.description = descriptions;
    this.date = date;
    this.domainIds = domains;
    this.techIds = techs;
    this.topicIds = topics;
    this.imgUrls = imgUrls;
    this.iconUrl = iconUrl;
  }

  /**
   * Getter for id.
   *
   * @return the id of the idea
   */
  public String getId() {
    return id;
  }

  /**
   * Getter for title.
   *
   * @return the title of the idea
   */
  public String getTitle() {
    return title;
  }

  /**
   * Getter for purpose.
   *
   * @return the purpose of the idea
   */
  public String getPurpose() {
    return purpose;
  }

  /**
   * Getter for description.
   *
   * @return the description of the idea
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Getter for isPaid.
   *
   * @return whether the idea is paid
   */
  public boolean getIsPaid() {
    return isPaid;
  }

  /**
   * Getter for isPrivate.
   *
   * @return whether the idea is private
   */
  public boolean getIsPrivate() {
    return isPrivate;
  }

  /**
   * Getter for inProgress.
   *
   * @return whether the idea is in progress
   */
  public boolean getInProgress() {
    return inProgress;
  }

  /**
   * Getter for date.
   *
   * @return the date of the idea
   */
  public Date getDate() {
    return date;
  }

  /**
   * Getter for domainIds.
   *
   * @return the domain ids of the idea
   */
  public List<String> getDomainIds() {
    return domainIds;
  }

  /**
   * Getter for techIds.
   *
   * @return the technology ids of the idea
   */
  public List<String> getTechIds() {
    return techIds;
  }

  /**
   * Getter for topicIds.
   *
   * @return the topic ids of the idea
   */
  public List<String> getTopicIds() {
    return topicIds;
  }

  /**
   * Getter for imgUrls.
   *
   * @return the image urls of the idea
   */
  public List<String> getImgUrls() {
    return imgUrls;
  }

  /**
   * Getter for iconUrl.
   *
   * @return the icon url of the idea
   */
  public String getIconUrl() {
    return iconUrl;
  }
}
