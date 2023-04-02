package ca.mcgill.purposeful.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.ModeratorRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Moderator;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Role;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Tests for the Moderator services @Author Enzo Benoit-Jeannin */
@ExtendWith(MockitoExtension.class)
public class TestModeratorService {

  // mock all the repositories that the service uses (and any other dependancies
  // handled by spring)
  @Mock private AppUserRepository appUserRepository;

  @Mock private ModeratorRepository moderatorRepository;

  @Mock private PasswordEncoder passwordEncoder;

  // inject mocks into the service you are testing
  @InjectMocks private ModeratorService moderatorService;

  // @Before
  // public void setup(){
  // MockitoAnnotations.openMocks(this);
  // }

  @BeforeEach
  public void setMockOutput() {
    // Set each CRUD method to its mock
    lenient()
        .when(appUserRepository.findAppUserByEmail(any()))
        .thenAnswer(MockRepository::findUserByEmail);

    lenient()
        .when(moderatorRepository.findModeratorByAppUserEmail(any()))
        .thenAnswer(MockRepository::findModeratorByEmail);

    lenient()
        .when(passwordEncoder.encode(anyString()))
        .thenAnswer(
            (InvocationOnMock invocation) -> {
              return invocation.getArgument(0) + "Encoded";
            });

    lenient()
        .when(appUserRepository.save(any(AppUser.class)))
        .thenAnswer(MockRepository::saveAppUser);
    lenient()
        .when(moderatorRepository.save(any(Moderator.class)))
        .thenAnswer(MockRepository::saveModerator);
  }

  /**
   * Method to check that a moderator is modified successfully
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_Success() {
    AppUser modified = null;
    modified =
        moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", "Wassim");
    assertNotNull(modified);
    assertEquals(MockDatabase.appUser1.getEmail(), modified.getEmail());
    assertEquals("Jabbour", modified.getLastname());
    assertEquals("Wassim", modified.getFirstname());
    assertEquals(MockDatabase.appUser1.getPassword(), modified.getPassword());
    assertEquals(MockDatabase.authorities1, modified.getAuthorities());
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with a null email
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NullEmail() {
    try {
      moderatorService.modifyModerator(null, "Jabbour", "Wassim");
    } catch (GlobalException e) {
      assertEquals("Email cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with an empty email
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_EmptyEmail() {
    try {
      moderatorService.modifyModerator("", "Jabbour", "Wassim");
    } catch (GlobalException e) {
      assertEquals("Email cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with a null fristname
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NullFirstname() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", null);
    } catch (GlobalException e) {
      assertEquals("First name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with an empty
   * fristname
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_EmptyFirstname() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", "");
    } catch (GlobalException e) {
      assertEquals("First name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with a null lastname
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NullLastname() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), null, "Wassim");
    } catch (GlobalException e) {
      assertEquals("Last name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with an empty
   * lastname
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_EmptyLastname() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "", "Wassim");
    } catch (GlobalException e) {
      assertEquals("Last name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator taht does not exist
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NotInDb() {
    try {
      moderatorService.modifyModerator("unregistered@gmail.com", "Jabbour", "Wassim");
    } catch (GlobalException e) {
      assertEquals("This account does not exist.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify an appUser that is not a
   * moderator
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NotModerator() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser2.getEmail(), "Jabbour", "Wassim");
    } catch (GlobalException e) {
      assertEquals("User is not a moderator!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that a moderator's password is modified successfully
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_Success() {
    AppUser modified = null;
    modified =
        moderatorService.modifyPassword(
            MockDatabase.appUser1.getEmail(), MockDatabase.VALID_PASSWORD);
    assertNotNull(modified);
    assertEquals(MockDatabase.appUser1.getEmail(), modified.getEmail());
    assertEquals(MockDatabase.appUser1.getLastname(), modified.getLastname());
    assertEquals(MockDatabase.appUser1.getFirstname(), modified.getFirstname());
    assertEquals(MockDatabase.VALID_PASSWORD_ENCODED, modified.getPassword());
    assertEquals(MockDatabase.authorities1, modified.getAuthorities());
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator's password that is
   * incorrect
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_TooShort() {
    try {
      moderatorService.modifyPassword(
          MockDatabase.appUser1.getEmail(), MockDatabase.INVALID_PASSWORD_ONE);
    } catch (GlobalException e) {
      assertEquals(
          "Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! ",
          e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator's password that is
   * incorrect
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NoNumber() {
    try {
      moderatorService.modifyPassword(
          MockDatabase.appUser1.getEmail(), MockDatabase.INVALID_PASSWORD_TWO);
    } catch (GlobalException e) {
      assertEquals(
          "Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! ",
          e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator's password that is
   * incorrect
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NoUpperCase() {
    try {
      moderatorService.modifyPassword(
          MockDatabase.appUser1.getEmail(), MockDatabase.INVALID_PASSWORD_THREE);
    } catch (GlobalException e) {
      assertEquals(
          "Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! ",
          e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator's password that is
   * incorrect
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NoLowerCase() {
    try {
      moderatorService.modifyPassword(
          MockDatabase.appUser1.getEmail(), MockDatabase.INVALID_PASSWORD_FOUR);
    } catch (GlobalException e) {
      assertEquals(
          "Password must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character! ",
          e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator's password but the
   * moderator does not exist
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NotInDB() {
    try {
      moderatorService.modifyPassword("unregisteredemail@gmail.com", MockDatabase.VALID_PASSWORD);
    } catch (GlobalException e) {
      assertEquals("This account does not exist.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator's password but the
   * account is not a moderator
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyPassword_NotModerator() {
    try {
      moderatorService.modifyPassword(
          MockDatabase.appUser2.getEmail(), MockDatabase.VALID_PASSWORD);
    } catch (GlobalException e) {
      assertEquals("User is not a moderator!", e.getMessage());
      return;
    }
    fail();
  }

  /** This class holds all of the mock methods of the CRUD repository. */
  class MockRepository {

    static AppUser findUserByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.appUser1.getEmail())) {
        return MockDatabase.appUser1;
      }
      if (email.equals(MockDatabase.appUser2.getEmail())) {
        return MockDatabase.appUser2;
      }
      return null;
    }

    static Role findModeratorByEmail(InvocationOnMock invocation) {
      String email = (String) invocation.getArgument(0);
      if (email.equals(MockDatabase.appUser1.getEmail())) {
        return MockDatabase.role1;
      }
      return null;
    }

    static Moderator saveModerator(InvocationOnMock invocation) {
      return (Moderator) invocation.getArgument(0);
    }

    static AppUser saveAppUser(InvocationOnMock invocation) {
      return (AppUser) invocation.getArgument(0);
    }
  }

  /** This class mock data for tests. */
  static final class MockDatabase {

    static AppUser appUser1 = new AppUser();
    static AppUser appUser2 = new AppUser();

    static Moderator role1 = new Moderator();
    static RegularUser role2 = new RegularUser();

    static Authority authority1 = Authority.Moderator;
    static Authority authority2 = Authority.User;

    static Set<Authority> authorities1 = new HashSet<Authority>();
    static Set<Authority> authorities2 = new HashSet<Authority>();

    static String VALID_PASSWORD = "Password1";
    static String VALID_PASSWORD_ENCODED = "Password1Encoded";
    static String INVALID_PASSWORD_ONE = "invalid";
    static String INVALID_PASSWORD_TWO = "invalidPassword";
    static String INVALID_PASSWORD_THREE = "invalidpassword1";
    static String INVALID_PASSWORD_FOUR = "INVALIDPASSWORD1";

    static {
      // Set the roles to all appUsers

      // appUser1 Information
      appUser1.setEmail("example@gmail.com");
      appUser1.setFirstname("John");
      appUser1.setLastname("Doe");
      appUser1.setPassword("password");

      // appUser2 Information
      appUser2.setEmail("example2@gmail.com");
      appUser2.setFirstname("Jane");
      appUser2.setLastname("Doe");
      appUser2.setPassword("password2");

      // Initialize a first appUser that is a moderator
      authorities1.add(authority1);
      appUser1.setAuthorities(authorities1);
      role1.setAppUser(appUser1);

      // Initlaize appUser that is just a regular user
      authorities2.add(authority2);
      appUser2.setAuthorities(authorities2);
      role2.setVerifiedCompany(false);
      role2.setAppUser(appUser2);
    }
  }
}
