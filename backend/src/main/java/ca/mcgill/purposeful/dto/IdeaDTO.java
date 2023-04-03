package ca.mcgill.purposeful.dto;

import ca.mcgill.purposeful.model.Idea;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Data transfer object for the Idea class */
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

  /** Default constructor for IdeaDTO */
  public IdeaDTO() {}

  /**
   * Constructor for IdeaDTO
   *
   * @param idea The idea to convert to a DTO
   */
  public IdeaDTO(Idea idea) {
    this.id = idea.getId();
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

  /**
   * Constructor for IdeaDTO
   *
   * @param id The id of the idea
   * @param title The title of the idea
   * @param purpose The purpose of the idea
   * @param descriptions The description of the idea
   * @param date The date of the idea
   * @param isPaid Whether the idea is paid
   * @param inProgress Whether the idea is in progress
   * @param isPrivate Whether the idea is private
   * @param domains The domains of the idea
   * @param techs The techs of the idea
   * @param topics The topics of the idea
   * @param imgUrls The image urls of the idea
   * @param iconUrl The icon url of the idea
   */
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

  /**
   * Gets the id of the idea
   *
   * @return The id of the idea
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the title of the idea
   *
   * @return The title of the idea
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the purpose of the idea
   *
   * @return The purpose of the idea
   */
  public String getPurpose() {
    return purpose;
  }

  /**
   * Gets the description of the idea
   *
   * @return The description of the idea
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Gets whether the idea is paid
   *
   * @return Whether the idea is paid
   */
  public boolean getIsPaid() {
    return isPaid;
  }

  /**
   * Gets whether the idea is private
   *
   * @return Whether the idea is private
   */
  public boolean getIsPrivate() {
    return isPrivate;
  }

  /**
   * Gets whether the idea is in progress
   *
   * @return Whether the idea is in progress
   */
  public boolean getInProgress() {
    return inProgress;
  }

  /**
   * Gets the date of the idea
   *
   * @return The date of the idea
   */
  public Date getDate() {
    return date;
  }

  /**
   * Gets the domains of the idea
   *
   * @return The domains of the idea
   */
  public Set<DomainDTO> getDomains() {
    return domains;
  }

  /**
   * Gets the techs of the idea
   *
   * @return The techs of the idea
   */
  public Set<TechDTO> getTechs() {
    return techs;
  }

  /**
   * Gets the topics of the idea
   *
   * @return The topics of the idea
   */
  public Set<TopicDTO> getTopics() {
    return topics;
  }

  /**
   * Gets the image urls of the idea
   *
   * @return The image urls of the idea
   */
  public List<URLDTO> getImgUrls() {
    return imgUrls;
  }

  /**
   * Gets the icon url of the idea
   *
   * @return The icon url of the idea
   */
  public URLDTO getIconUrl() {
    return iconUrl;
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
