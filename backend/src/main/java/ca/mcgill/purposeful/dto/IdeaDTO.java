package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Idea;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IdeaDTO {

  private String id;
  private boolean isPaid;
  private boolean isPrivate;
  private boolean inProgress;
  private String title;
  private String purpose;
  private String description;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private Set<DomainDTO> domains;
  private Set<TechDTO> techs;
  private Set<TopicDTO> topics;
  private List<URLDTO> imgUrls;
  private URLDTO iconUrl;

  private List<String> domainIds;
  private List<String> techIds;
  private List<String> topicIds;
  private List<String> imgUrlIds;
  private String iconUrlId;

  public IdeaDTO() {}

  public IdeaDTO(Idea idea) {
    this.isPaid = idea.isPaid();
    this.isPrivate = idea.isPrivate();
    this.inProgress = idea.isInProgress();
    this.title = idea.getTitle();
    this.purpose = idea.getPurpose();
    this.description = idea.getDescription();
    this.date = idea.getDate();
    this.domains = idea.getDomains().stream().map(DomainDTO::new).collect(Collectors.toSet());
    this.techs = idea.getTechs().stream().map(TechDTO::new).collect(Collectors.toSet());
    this.topics = idea.getTopics().stream().map(TopicDTO::new).collect(Collectors.toSet());
    this.imgUrls =
        idea.getSupportingImageUrls().stream().map(URLDTO::new).collect(Collectors.toList());
    this.iconUrl = new URLDTO(idea.getIconUrl());
  }

  public IdeaDTO(
      String id,
      String title,
      String purpose,
      String descriptions,
      Date date,
      boolean isPaid,
      boolean inProgress,
      boolean isPrivate,
      Set<DomainDTO> domains,
      Set<TechDTO> techs,
      Set<TopicDTO> topics,
      List<URLDTO> imgUrls,
      URLDTO iconUrl) {
    this.id = id;
    this.isPaid = isPaid;
    this.isPrivate = isPrivate;
    this.inProgress = inProgress;
    this.title = title;
    this.purpose = purpose;
    this.description = descriptions;
    this.date = date;
    this.domains = domains;
    this.techs = techs;
    this.topics = topics;
    this.imgUrls = imgUrls;
    this.iconUrl = iconUrl;
  }

  public IdeaDTO(
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

  public Set<DomainDTO> getDomains() {
    return domains;
  }

  public Set<TechDTO> getTechs() {
    return techs;
  }

  public Set<TopicDTO> getTopics() {
    return topics;
  }

  public List<URLDTO> getImgUrls() {
    return imgUrls;
  }

  public URLDTO getIconUrl() {
    return iconUrl;
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

  /**
   * Converts a list of Idea objects to a list of IdeaDTO objects
   *
   * @param ideas The ideas to convert
   * @return The converted list
   * @author Wassim Jabbour
   */
  public static ArrayList<IdeaDTO> convertToDto(List<Idea> ideas) {

    ArrayList<IdeaDTO> dtoList = new ArrayList<>();

    for (Idea idea : ideas) {
      dtoList.add(new IdeaDTO(idea));
    }

    return dtoList;
  }
}
