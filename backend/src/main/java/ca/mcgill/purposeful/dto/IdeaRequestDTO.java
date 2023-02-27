package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  private List<String> imgUrlIds;
  private String iconUrlId;

  public IdeaRequestDTO() {}

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
    this.imgUrlIds = new ArrayList<>();

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
      this.imgUrlIds.add(url.getId());
    }

    this.iconUrlId = idea.getIconUrl().getId();
  }

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
    this.imgUrlIds = imgUrls;
    this.iconUrlId = iconUrl;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getPurpose() {
    return purpose;
  }

  public String getDescription() {
    return this.description;
  }

  public boolean getIsPaid() {
    return isPaid;
  }

  public boolean getIsPrivate() {
    return isPrivate;
  }

  public boolean getInProgress() {
    return inProgress;
  }

  public Date getDate() {
    return date;
  }

  public List<String> getDomainIds() {
    return domainIds;
  }

  public List<String> getTechIds() {
    return techIds;
  }

  public List<String> getTopicIds() {
    return topicIds;
  }

  public List<String> getImgUrlIds() {
    return imgUrlIds;
  }

  public String getIconUrlId() {
    return iconUrlId;
  }
}
