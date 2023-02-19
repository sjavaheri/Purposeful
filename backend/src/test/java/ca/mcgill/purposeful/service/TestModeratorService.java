package ca.mcgill.purposeful.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Before;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;


import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.ModeratorRepository;

import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Moderator;
import ca.mcgill.purposeful.model.Owner;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.model.Role;

/**
 * Tests for the Moderator services
 *
 * @Author Enzo Benoit-Jeannin
 */
@ExtendWith(MockitoExtension.class)
public class TestModeratorService {

  // mock all the repositories that the service uses (and any other dependancies
  // handled by spring)
  @Mock
  private AppUserRepository appUserRepository;

  @Mock
  private ModeratorRepository moderatorRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  // inject mocks into the service you are testing
  @InjectMocks
  private ModeratorService moderatorService;

  @Before
  public void setup(){
    MockitoAnnotations.openMocks(this);
  }

  // @BeforeEach
  // public void setMockOutput() {
  //   // Set each CRUD method to its mock
  //   lenient().when(appUserRepository.findAppUserByEmail(any()))
  //       .thenAnswer(MockRepository::findUserByEmail);

  //   lenient().when(passwordEncoder.encode(anyString()))
  //   .thenAnswer((InvocationOnMock invocation) -> {
  //     return invocation.getArgument(0) + "Encoded";
  //   });
  
  //   lenient().when(appUserRepository.save(any(AppUser.class)))
  //       .thenAnswer(MockRepository::saveAppUser);
  //   lenient().when(moderatorRepository.save(any(Moderator.class)))
  //       .thenAnswer(MockRepository::saveModerator);
  // }
  private static final String VALID_REGULARUSER_EMAIL_ONE = "regular.user.one@email.com";
  private static final String VALID_REGULARUSER_EMAIL_TWO = "regular.user.two@email.com";
  private static final String VALID_REGULARUSER_FIRSTNAME_ONE = "Rob";
  private static final String VALID_REGULARUSER_FIRSTNAME_TWO = "Marwan";
  private static final String VALID_REGULARUSER_LASTNAME_ONE = "Sab";
  private static final String VALID_REGULARUSER_LASTNAME_TWO = "Kanaan";

  private static final String VALID_MODERATOR_EMAIL_ONE = "moderator.user.one@email.com";
  private static final String VALID_MODERATOR_EMAIL_TWO = "moderator.user.two@email.com";
  private static final String VALID_MODERATOR_FIRSTNAME_ONE = "Rob";
  private static final String VALID_MODERATOR_FIRSTNAME_TWO = "Marwan";
  private static final String VALID_MODERATOR_LASTNAME_ONE = "Kanaan";
  private static final String VALID_MODERATOR_LASTNAME_TWO = "Sab";

  private static final String VALID_PASSWORD = "Password1";
  private static final String VALID_PASSWORD_ENCODED = "Password1Encoded";

  private static final String INVALID_EMAIL = "invalid.email";
  private static final String INVALID_PASSWORD_ONE = "invalid";
  private static final String INVALID_PASSWORD_TWO = "invalidPassword";
  private static final String INVALID_PASSWORD_THREE = "invalidpassword1";
  private static final String INVALID_PASSWORD_FOUR = "INVALIDPASSWORD1";
  /**
   * Mocking the repositories
   *
   * @author Siger Ma
   */
  @BeforeEach
  public void setMockOutput() {
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(appUserRepository.findAppUserByEmail(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(VALID_REGULARUSER_EMAIL_TWO)) {
            AppUser appUser = new AppUser();
            appUser.setEmail(VALID_REGULARUSER_EMAIL_TWO);
            appUser.setFirstname(VALID_REGULARUSER_FIRSTNAME_TWO);
            appUser.setLastname(VALID_REGULARUSER_LASTNAME_TWO);
            appUser.setPassword(VALID_PASSWORD);
            appUser.getAuthorities().add(Authority.User);
            return appUser;
          } else if (invocation.getArgument(0).equals(VALID_MODERATOR_EMAIL_TWO)) {
            AppUser appUser = new AppUser();
            appUser.setEmail(VALID_MODERATOR_EMAIL_TWO);
            appUser.setFirstname(VALID_MODERATOR_FIRSTNAME_TWO);
            appUser.setLastname(VALID_MODERATOR_LASTNAME_TWO);
            appUser.setPassword(VALID_PASSWORD);
            appUser.getAuthorities().add(Authority.Moderator);
            return appUser;
          } else {
            return null;
          }
        });

    lenient().when(passwordEncoder.encode(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          return invocation.getArgument(0) + "Encoded";
        });

    lenient().when(appUserRepository.save(any(AppUser.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(moderatorRepository.save(any(Moderator.class)))
        .thenAnswer(returnParameterAsAnswer);
  }

  /**
   * Method to check that a moderator is modified successfully
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_Success() {
    AppUser modified = null;
    modified = moderatorService.modifyModerator(VALID_REGULARUSER_EMAIL_ONE, "Jabbour", "Wassim", MockDatabase.authorities1);
    assertNotNull(modified);
    assertEquals(MockDatabase.appUser1.getEmail(), modified.getEmail());
    assertEquals("Jabbour", modified.getLastname());
    assertEquals("Wassim", modified.getFirstname());
    assertEquals(MockDatabase.appUser1.getPassword(), modified.getPassword());
    assertEquals(MockDatabase.authorities1, modified.getAuthorities());
    assertEquals(MockDatabase.roles1, modified.getRole());
    }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with a null email
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NullEmail() {
    try {
      moderatorService.modifyModerator(null, "Jabbour", "Wassim", MockDatabase.authorities1);
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
      moderatorService.modifyModerator("", "Jabbour", "Wassim", MockDatabase.authorities1);
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
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", null, MockDatabase.authorities1);
    } catch (GlobalException e) {
      assertEquals("First name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }


  /**
   * Method to check that an error is thrown when we try to modify a moderator with an empty fristname
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_EmptyFirstname() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", "", MockDatabase.authorities1);
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
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), null, "Wassim", MockDatabase.authorities1);
    } catch (GlobalException e) {
      assertEquals("Last name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }


  /**
   * Method to check that an error is thrown when we try to modify a moderator with an empty lastname
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_EmptyLastname() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "", "Wassim", MockDatabase.authorities1);
    } catch (GlobalException e) {
      assertEquals("Last name cannot be left empty! ", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with a null set of authorities
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NullAuthorities() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", "Wassim", null);
    } catch (GlobalException e) {
      assertEquals("Authorities cannot be null! ", e.getMessage());
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
      moderatorService.modifyModerator("unregistered@gmail.com", "Jabbour", "Wassim", MockDatabase.authorities1);
    } catch (GlobalException e) {
      assertEquals("This account does not exist.", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * Method to check that an error is thrown when we try to modify a moderator with auhtorities that does not contain moderator
   *
   * @author Enzo Benoit-Jeannin
   */
  @Test
  public void testModifyModerator_NoModeratorAuthority() {
    try {
      moderatorService.modifyModerator(MockDatabase.appUser1.getEmail(), "Jabbour", "Wassim", MockDatabase.authorities2);
    } catch (GlobalException e) {
      assertEquals("New authorities do not include the moderator authority!", e.getMessage());
      return;
    }
    fail();
  }

  /**
   * This class holds all of the mock methods of the CRUD repository.
   */
  class MockRepository{
    static AppUser findUserByEmail(InvocationOnMock invocation) {
        String email = (String) invocation.getArgument(0);
        if (email.equals(MockDatabase.appUser1.getEmail())) {
          return MockDatabase.appUser1;
        }
        if (email.equals(MockDatabase.appUser2.getEmail())) {
          return MockDatabase.appUser2;
        }
        if (email.equals(MockDatabase.appUser3.getEmail())) {
          return MockDatabase.appUser3;
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

  /**
   * This class mock data for tests.
   */
  final static class MockDatabase {
    static AppUser appUser1 = new AppUser();
    static AppUser appUser2 = new AppUser();
    static AppUser appUser3 = new AppUser();

    static Role role1 = new Moderator();
    static Role role2 = new Owner();
    static Role role3 = new RegularUser();

    static List<Role> roles1 = new ArrayList<Role>();
    static List<Role> roles2 = new ArrayList<Role>();
    static List<Role> roles3 = new ArrayList<Role>();

    static Authority authority1 = Authority.Moderator;
    static Authority authority2 = Authority.Owner; 
    static Authority authority3 = Authority.User; 

    static Set<Authority> authorities1 = new HashSet<Authority>();
    static Set<Authority> authorities2 = new HashSet<Authority>();
    static Set<Authority> authorities3 = new HashSet<Authority>();


    static {
        // Set the roles to all appUsers

        // Initialize a first appUser that is a moderator
        roles1.add(role1);
        authorities1.add(authority1);
        appUser1.setRole(roles1);
        appUser1.setAuthorities(authorities1);

        // Initlaize appUser that is just an owner
        roles2.add(role2);
        authorities2.add(authority2);
        appUser2.setRole(roles2);
        appUser2.setAuthorities(authorities2);

        // Initialize appUser that is just a regularUser
        roles3.add(role3);
        authorities3.add(authority3);
        appUser3.setRole(roles3);
        appUser3.setAuthorities(authorities3);

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

        // appUser3 Information
        appUser3.setEmail("example3@gmail.com");
        appUser3.setFirstname("Adrien");
        appUser3.setLastname("Schaal");
        appUser3.setPassword("password3");

    }
  }
}


