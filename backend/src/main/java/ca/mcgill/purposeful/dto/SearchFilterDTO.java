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

  public SearchFilterDTO() {}

  public SearchFilterDTO(List<String> domains, List<String> topics, List<String> technologies) {
    this.domains = domains;
    this.topics = topics;
    this.technologies = technologies;
  }

  public List<String> getDomains() {
    return domains;
  }

  public void setDomains(ArrayList<String> domains) {
    this.domains = domains;
  }

  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(ArrayList<String> topics) {
    this.topics = topics;
  }

  public List<String> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(ArrayList<String> technologies) {
    this.technologies = technologies;
  }
}
