package ca.mcgill.purposeful.util;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.CollaborationRequestRepository;
import ca.mcgill.purposeful.dao.CollaborationResponseRepository;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.ReactionRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.dao.URLRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.CollaborationRequest;
import ca.mcgill.purposeful.model.CollaborationResponse;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.Reaction;
import ca.mcgill.purposeful.model.Reaction.ReactionType;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Status;
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

  @Autowired private TopicRepository topicRepository;

  @Autowired private TechnologyRepository technologyRepository;

  @Autowired private URLRepository urlRepository;

  @Autowired private IdeaRepository ideaRepository;

  @Autowired private DomainRepository domainRepository;

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private RegularUserRepository regularUserRepository;

  @Autowired private ReactionRepository reactionRepository;

  @Autowired private CollaborationResponseRepository collaborationResponseRepository;

  @Autowired private AppUserService appUserService;

  @Autowired private CollaborationRequestRepository collaborationRequestRepository;

  @Autowired private PasswordEncoder passwordEncoder;

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
      // set the authorities of the app user
      Set<Authority> setOfAuthorities = new HashSet<Authority>();
      String[] authorities = row.get("authorities").split(",");
      for (String authority : authorities) {
        setOfAuthorities.add(Authority.valueOf(authority));
      }

      // add the app user to the list of app users
      appUserRepository.save(appUser);

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
      AppUser appUser =
          appUserService.registerRegularUser(
              row.get("email"), row.get("password"), row.get("firstname"), row.get("lastname"));

      // Add the regular user to the map if it was passed
      // Note: no need to add the app user to the map since we only will be
      // manipulating the regular
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
   * This method creates and saves Ideas from a data table
   *
   * @param dataTable a data table containing the ideas to be created
   * @param idMap a map containing the ids of the users and domains
   * @implNote {@code idMap} CANNOT BE NULL
   * @author Thibaut Baguette
   */
  public void createAndSaveIdeasFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      Idea idea = new Idea();
      idea.setTitle(row.get("title"));
      idea.setPurpose(row.get("purpose"));
      Date date = new Date();
      date.setHours(0);

      // set date to a value if the idea table has date column
      if (row.get("date") != null) {
        idea.setDate(new Date(Integer.parseInt(row.get("date"))));
      } else {
        // otherwise current date
        idea.setDate(date);
      }
      idea.setDescription("");

      // user
      AppUser user = appUserRepository.findAppUserById(idMap.get(row.get("user")));
      RegularUser role = regularUserRepository.findRegularUserByAppUserEmail(user.getEmail());
      idea.setUser(role);

      // domains
      Set<Domain> domains = new HashSet<Domain>();
      String[] domainIds = row.get("domains").split(",");
      for (String domainId : domainIds) {
        domains.add(domainRepository.findDomainById(idMap.get(domainId.replaceAll("\\s", ""))));
      }
      idea.setDomains(domains);

      // topics
      Set<Topic> topics = new HashSet<Topic>();
      String[] topicIds = row.get("topics").split(",");
      for (String topicId : topicIds) {
        topics.add(topicRepository.findTopicById(idMap.get(topicId.replaceAll("\\s", ""))));
      }
      idea.setTopics(topics);

      // techs
      Set<Technology> techs = new HashSet<Technology>();
      String[] techIds = row.get("techs").split(",");
      for (String techId : techIds) {
        techs.add(technologyRepository.findTechnologyById(idMap.get(techId.replaceAll("\\s", ""))));
      }
      idea.setTechs(techs);

      // urls
      List<URL> supportingUrls = new ArrayList<URL>();
      String urlString = row.get("supportingImageUrls");
      if (urlString == null) {
        urlString = "";
      }
      String[] urlIds = urlString.split(",");
      for (String urlId : urlIds) {
        if (!urlId.equals("")) {
          supportingUrls.add(urlRepository.findURLById(idMap.get(urlId)));
        }
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

  /**
   * This method creates and saves URLs from a data table
   *
   * @param dataTable a data table containing the URLs to be created
   * @param idMap a map containing the ids of the users and domains
   * @author Thibaut Baguette
   */
  public void createAndSaveURLsFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      URL url = new URL();
      url.setURL(row.get("url"));
      urlRepository.save(url);

      if (idMap != null) {
        idMap.put(row.get("id"), url.getId());
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

  /**
   * This method creates and saves reactions from a data table
   *
   * @param dataTable
   * @param idMap
   * @author Athmane Benarous
   */
  public void createAndSaveReactionsFromTable(DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      Reaction reaction = new Reaction();
      reaction.setReactionType(ReactionType.valueOf(row.get("reactionType")));
      Date date = new Date();
      date.setHours(0);
      reaction.setDate(date);
      reaction.setIdea(ideaRepository.findIdeaById(idMap.get(row.get("idea_id"))));
      reaction.setRegularUser(
          regularUserRepository.findRegularUserByAppUser_Id(idMap.get(row.get("user"))));

      reactionRepository.save(reaction);
      idMap.put(row.get("id"), reaction.getId());
    }
  }

  /**
   * This method creates and saves CollaborationResponses from a data table
   *
   * @param dataTable
   * @param idMap
   * @author Thibaut Baguette
   */
  public void createAndSaveCollaborationResponsesFromTable(
      DataTable dataTable, Map<String, String> idMap) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      CollaborationResponse collaborationResponse = new CollaborationResponse();
      collaborationResponse.setMessage(row.get("message"));
      collaborationResponse.setAdditionalContact(row.get("additionalContact"));
      collaborationResponse.setStatus(Status.valueOf(row.get("status")));

      collaborationResponseRepository.save(collaborationResponse);
      idMap.put(row.get("id"), collaborationResponse.getId());
    }
  }

  /**
   * This method creates and saves collaboration requests from a data table
   *
   * @param dataTable The table
   * @param idMap The id map
   * @author Wassim Jabbour
   */
  public void createAndSaveCollaborationRequestsFromTable(
      DataTable dataTable, Map<String, String> idMap) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      CollaborationRequest collaborationRequest = new CollaborationRequest();
      collaborationRequest.setIdea(ideaRepository.findIdeaById(idMap.get(row.get("ideaId"))));
      collaborationRequest.setRequester(
          regularUserRepository.findRegularUserByAppUser_Id(idMap.get(row.get("userId"))));
      collaborationRequest.setMessage(row.get("message"));
      collaborationRequest.setAdditionalContact(row.get("additionalContact"));
      collaborationRequest.setCollaborationResponse(
          collaborationResponseRepository.findCollaborationResponseById(
              idMap.get(row.get("collaborationResponseId"))));

      collaborationRequestRepository.save(collaborationRequest);

      if (idMap != null) {
        idMap.put(row.get("id"), collaborationRequest.getId());
      }
    }
  }
}
