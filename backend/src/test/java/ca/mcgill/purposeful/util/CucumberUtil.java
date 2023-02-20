package ca.mcgill.purposeful.util;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Role;
import io.cucumber.datatable.DataTable;
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

@Configuration
public class CucumberUtil {

  @Autowired private AppUserRepository appUserRepository;

  @Autowired private RegularUserRepository regularUserRepository;

  @Autowired PasswordEncoder passwordEncoder;

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
      // Note: no need to add the app user to the map since we only will be manipulating the regular
      // user instance later on
      if (idMap != null) {
        idMap.put(row.get("id"), regularUser.getId());
      }
    }
  }

  public void createAndSaveDomainsFromTable() {
    // TODO: Implement this method
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
