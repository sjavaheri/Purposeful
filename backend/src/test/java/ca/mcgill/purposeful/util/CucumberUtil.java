package ca.mcgill.purposeful.util;

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
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.model.URL;
import ca.mcgill.purposeful.service.AppUserService;
import io.cucumber.datatable.DataTable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CucumberUtil {

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private RegularUserRepository regularUserRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired DomainRepository domainRepository;

  @Autowired TopicRepository topicRepository;

  @Autowired TechnologyRepository technologyRepository;

  @Autowired IdeaRepository ideaRepository;

  @Autowired AppUserService appUserService;

  @Autowired URLRepository urlRepository;

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

      // add the app user to the list of app users
      appUserRepository.save(appUser);

      // Add the user to the map of ids if it was passed
      if (idMap != null) {
        idMap.put(row.get("id"), appUser.getId());
      }
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

      // Create a regular user from the table values
      AppUser appUser =
          appUserService.registerRegularUser(
              row.get("email"), row.get("password"), row.get("firstname"), row.get("lastname"));

      // Add the regular user to the map if it was passed
      // Note: no need to add the app user to the map since we only will be manipulating the regular
      // user instance later on
      if (idMap != null) {
        idMap.put(row.get("id"), appUser.getId());
      }
    }
  }

  /**
   * This method creates and saves domains from a data table
   *
   * @param dataTable The table
   * @param idMap The map of ids
   * @author Wassim Jabbour
   */
  public void createAndSaveDomainsFromTable(DataTable dataTable, Map<String, String> idMap) {

    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      Domain domain = new Domain();
      domain.setName(row.get("name"));

      // Save the domain in memory
      domainRepository.save(domain);

      // Save the domain in the map if it exists
      if (idMap != null) {
        idMap.put(row.get("id"), domain.getId());
      }
    }
  }

  /**
   * This method creates and saves topics from a data table
   *
   * @param dataTable The table
   * @param idMap The map of ids
   * @author Wassim Jabbour
   */
  public void createAndSaveTopicsFromTable(DataTable dataTable, Map<String, String> idMap) {

    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      Topic topic = new Topic();
      topic.setName(row.get("name"));

      // Save the domain in memory
      topicRepository.save(topic);

      // Save the domain in the map if it exists
      if (idMap != null) {
        idMap.put(row.get("id"), topic.getId());
      }
    }
  }

  /**
   * This method creates and saves technologies from a data table
   *
   * @param dataTable The table
   * @param idMap The map of ids
   * @author Wassim Jabbour
   */
  public void createAndSaveTechsFromTable(DataTable dataTable, Map<String, String> idMap) {

    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      Technology tech = new Technology();
      tech.setName(row.get("name"));

      // Save the domain in memory
      technologyRepository.save(tech);

      // Save the domain in the map if it exists
      if (idMap != null) {
        idMap.put(row.get("id"), tech.getId());
      }
    }
  }

  /**
   * This method creates and saves ideas from a data table
   *
   * @param dataTable The table
   * @param idMap The map of ids
   * @author Wassim Jabbour
   */
  public void createAndSaveIdeasFromTable(DataTable dataTable, Map<String, String> idMap) {

    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {

      // Create idea
      Idea idea = new Idea();

      // Set title
      idea.setTitle(row.get("title"));

      // Set date
      idea.setDate(new Date(Integer.parseInt(row.get("date"))));

      // Set domains
      Set<Domain> domainSet = new HashSet<>();
      for (String domainId : row.get("domains").split(","))
        domainSet.add(domainRepository.findDomainById(idMap.get(domainId)));
      idea.setDomains(domainSet);

      // Set topics
      Set<Topic> topicSet = new HashSet<>();
      for (String topicId : row.get("topics").split(","))
        topicSet.add(topicRepository.findTopicById(idMap.get(topicId)));
      idea.setTopics(topicSet);

      // Set technologies
      Set<Technology> techSet = new HashSet<>();
      for (String techId : row.get("techs").split(","))
        techSet.add(technologyRepository.findTechnologyById(idMap.get(techId)));
      idea.setTechs(techSet);

      // Set description
      idea.setDescription(row.get("description"));

      // Set icon
      URL iconUrl = new URL();
      iconUrl.setURL(row.get("iconUrl"));
      iconUrl.setPresetIcon(false);
      urlRepository.save(iconUrl);
      idea.setIconUrl(iconUrl);

      // Set purpose
      idea.setPurpose(row.get("purpose"));

      // Set the user
      AppUser appUser =
          appUserRepository.findAppUserById(idMap.get(row.get("author"))); // Extract app user saved
      RegularUser regularUser =
          regularUserRepository.findRegularUserByAppUserEmail(
              appUser.getEmail()); // Extract regular user from app user
      idea.setUser(regularUser);

      // Save the domain in memory
      ideaRepository.save(idea);

      // Save the domain in the map if it exists
      if (idMap != null) {
        idMap.put(row.get("id"), idea.getId());
      }
    }
  }

  /**
   * Method to generate the HttpHeaders for the basic auth, i.e. when a user first authenticate
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
   * Method to generate the HttpHeaders for the bearer auth, i.e. when a user is already
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
}
