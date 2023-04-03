package ca.mcgill.purposeful.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * To send a search filter to the filter ideas backend controller method
 *
 * @author Wassim Jabbour
 */
public class SearchFilterDTO {
  private List<String> domains;
  private List<String> topics;
  private List<String> technologies;

  /** Default constructor */
  public SearchFilterDTO() {}

  /**
   * Constructor
   *
   * @param domains Domains
   * @param topics Topics
   * @param technologies Technologies
   */
  public SearchFilterDTO(List<String> domains, List<String> topics, List<String> technologies) {
    this.domains = domains;
    this.topics = topics;
    this.technologies = technologies;
  }

  /**
   * Get the domains
   *
   * @return Domains
   */
  public List<String> getDomains() {
    return domains;
  }

  /**
   * Set the domains
   *
   * @param domains Domains
   */
  public void setDomains(ArrayList<String> domains) {
    this.domains = domains;
  }

  /**
   * Get the topics
   *
   * @return Topics
   */
  public List<String> getTopics() {
    return topics;
  }

  /**
   * Set the topics
   *
   * @param topics Topics
   */
  public void setTopics(ArrayList<String> topics) {
    this.topics = topics;
  }

  /**
   * Get the technologies
   *
   * @return Technologies
   */
  public List<String> getTechnologies() {
    return technologies;
  }

  /**
   * Set the technologies
   *
   * @param technologies Technologies
   */
  public void setTechnologies(ArrayList<String> technologies) {
    this.technologies = technologies;
  }
}
