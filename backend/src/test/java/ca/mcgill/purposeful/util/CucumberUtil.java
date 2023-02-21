package ca.mcgill.purposeful.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dao.URLRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Role;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.model.URL;
import io.cucumber.datatable.DataTable;

@Configuration
public class CucumberUtil {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private RegularUserRepository regularUserRepository;

  @Autowired
  private DomainRepository domainRepository;

  @Autowired
  private TopicRepository topicRepository;

  @Autowired
  private TechnologyRepository technologyRepository;

  @Autowired
  private URLRepository urlRepository;

  @Autowired
  private IdeaRepository ideaRepository;

  public static ArrayList<AppUser> unpackTableIntoUsers(DataTable dataTable) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    // create an empty list of app users
    ArrayList<AppUser> appUsers = new ArrayList<>();

    for (var row : rows) {
      // get the values from the data table
      String firstname = row.get("firstname");
      String lastname = row.get("lastname");
      String email = row.get("email");
      String password = row.get("password");
      String authorities = row.get("authorities");
      Authority authority = Authority.valueOf(authorities);

      // create an instance of App User with these properties
      AppUser appUser = new AppUser();
      appUser.setFirstname(firstname);
      appUser.setLastname(lastname);
      appUser.setEmail(email);
      appUser.setPassword(password);

      // set the athorities of the app user
      Set<Authority> setOfAuthorities = new HashSet<Authority>();
      setOfAuthorities.add(authority);
      appUser.setAuthorities(setOfAuthorities);

      // add the app user to the list of app users
      appUsers.add(appUser);
    }
    return appUsers;
  }

  /**
   * This method creates and saves an appUsers from a data table
   *
   * @param dataTable
   * @author Sasha
   */
  public void createAndSaveAppUsersFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      // create an instance of App User from the table values
      AppUser appUser = new AppUser();
      appUser.setFirstname(row.get("firstname"));
      appUser.setLastname(row.get("lastname"));
      appUser.setEmail(row.get("email"));
      appUser.setPassword(passwordEncoder.encode(row.get("password")));
      // set the athorities of the app user
      Set<Authority> setOfAuthorities = new HashSet<Authority>();
      Authority authority = Authority.valueOf(row.get("authorities"));
      setOfAuthorities.add(authority);

      // Add the user to the map of ids if it was passed
      if (idMap != null) {
        idMap.put(row.get("id"), appUser.getId());
      }

      // add the app user to the list of app users
      appUserRepository.save(appUser);
    }
  }

  /**
   * This method creates and saves regular users from a data table
   *
   * @author Wassim Jabbour
   */
  public void createAndSaveRegularUsersFromTable(DataTable dataTable, Map<String, String> idMap) {

    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      // create an instance of App User from the table values
      AppUser appUser = new AppUser();
      appUser.setFirstname(row.get("firstname"));
      appUser.setLastname(row.get("lastname"));
      appUser.setEmail(row.get("email"));
      appUser.setPassword(passwordEncoder.encode(row.get("password")));

      // Set the user to be regular
      Set<Authority> setOfAuthorities = new HashSet<Authority>();
      setOfAuthorities.add(Authority.User);

      // Create a regular user class
      RegularUser regularUser = new RegularUser();

      // Add the regular user to a list of roles
      List<Role> roles = new ArrayList<Role>();
      roles.add(regularUser);

      // Make the appuser instance point to the regular user
      appUser.setRole(roles);

      // Save both in the database
      appUserRepository.save(appUser);
      regularUserRepository.save(regularUser);

      // Add the regular user to the map if it was passed
      // Note: no need to add the app user to the map since we only will be
      // manipulating the regular
      // user instance later on
      if (idMap != null) {
        idMap.put(row.get("id"), regularUser.getId());
      }
    }
  }

  /**
   * Method to generate the HttpHeaders for the basic auth, i.e. when a user first
   * authenticate
   *
   * @param email
   * @param password
   * @return HttpHeaders required in the request
   */
  public HttpHeaders basicAuthHeader(String email, String password) {
    HttpHeaders headers = new HttpHeaders();
    String auth = email + ":" + password;
    var authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    headers.add("Authorization", authHeader);
    return headers;
  }

  /**
   * Method to generate the HttpHeaders for the bearer auth, i.e. when a user is
   * already
   * authenticated
   *
   * @param jwtToken
   * @return HttpHeaders required in the request
   */
  public HttpHeaders bearerAuthHeader(String jwtToken) {
    HttpHeaders headers = new HttpHeaders();
    var authHeader = "Bearer " + jwtToken;
    headers.add("Authorization", authHeader);
    return headers;
  }

  /**
   * This method creates and saves Domains from a data table
   * 
   * @param dataTable a data table containing the domains to be created
   * 
   * @author Thibaut Baguette
   */
  public void createAndSaveDomainFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      Domain domain = new Domain();
      domain.setName(row.get("name"));
      domainRepository.save(domain);
      idMap.put(row.get("id"), domain.getId());
    }
  }

  /**
   * This method creates and saves Topics from a data table
   * 
   * @param dataTable a data table containing the topics to be created
   * 
   * @author Thibaut Baguette
   */
  public void createAndSaveTopicFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      Topic topic = new Topic();
      topic.setName(row.get("name"));
      topicRepository.save(topic);
      idMap.put(row.get("id"), topic.getId());
    }
  }

  /**
   * This method creates and saves Technologies from a data table
   * 
   * @param dataTable a data table containing the technologies to be created
   * 
   * @author Thibaut Baguette
   */
  public void createAndSaveTechFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      Technology tech = new Technology();
      tech.setName(row.get("name"));
      technologyRepository.save(tech);
      idMap.put(row.get("id"), tech.getId());
    }
  }

  /**
   * This method creates and saves URLs from a data table
   * 
   * @param dataTable a data table containing the URLs to be created
   * 
   * @author Thibaut Baguette
   */
  public void createAndSaveURLFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      URL url = new URL();
      url.setURL(row.get("url"));
      urlRepository.save(url);
      idMap.put(row.get("id"), url.getId());
    }
  }

  /**
   * This method creates and saves Ideas from a data table
   * 
   * @param dataTable a data table containing the ideas to be created
   * 
   * @author Thibaut Baguette
   */
  public void createAndSaveIdeaFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      Idea idea = new Idea();
      idea.setTitle(row.get("title"));
      idea.setPurpose(row.get("purpose"));

      // domains
      Set<Domain> domains = new HashSet<Domain>();
      String[] domainIds = row.get("domains").split(",");
      for (String domainId : domainIds) {
        domains.add(domainRepository.findDomainById(idMap.get(domainId)));
      }
      idea.setDomains(domains);

      // topics
      Set<Topic> topics = new HashSet<Topic>();
      String[] topicIds = row.get("topics").split(",");
      for (String topicId : topicIds) {
        topics.add(topicRepository.findTopicById(idMap.get(topicId)));
      }
      idea.setTopics(topics);

      // techs
      Set<Technology> techs = new HashSet<Technology>();
      String[] techIds = row.get("techs").split(",");
      for (String techId : techIds) {
        techs.add(technologyRepository.findTechnologyById(idMap.get(techId)));
      }
      idea.setTechs(techs);

      // urls
      List<URL> supportingUrls = new ArrayList<URL>();
      String[] urlIds = row.get("supportingImageUrls").split(",");
      for (String urlId : urlIds) {
        supportingUrls.add(urlRepository.findURLById(idMap.get(urlId)));
      }
      idea.setSupportingImageUrls(supportingUrls);

      // icon
      URL iconUrl = urlRepository.findURLById(idMap.get(row.get("iconUrl")));
      idea.setIconUrl(iconUrl);

      idea.setPaid(Boolean.parseBoolean(row.get("isPaid")));
      idea.setInProgress(Boolean.parseBoolean(row.get("isInProgress")));
      idea.setPrivate(Boolean.parseBoolean(row.get("isPrivate")));
      ideaRepository.save(idea);
      idMap.put(row.get("id"), idea.getId());
    }
  }
}
