package ca.mcgill.purposeful.dto;

/**
 * Data transfer object for the AppUser class.
 */
public class AppUserDto {

  private String id;
  private String email;
  private String password;
  private String firstname;
  private String lastname;

  /**
   * Default constructor for AppUserDto.
   *
   */
  public AppUserDto() {}

  /**
   * Constructor for AppUserDto.
   *
   * @param email - the email of the user
   * @param password - the password of the user
   * @param firstname - the firstname of the user
   * @param lastname - the lastname of the user
   * @author Shidan Javaheri
   */
  public AppUserDto(String email, String password, String firstname, String lastname) {
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  /**
   * Get the id of the user.
   * @return the id of the user
   */
  public String getId() {
    return id;
  }

    /**
     * Set the id of the user.
     * @param id - the id of the user
     */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get the email of the user.
   * @return the email of the user
   */
  public String getEmail() {
    return email;
  }

    /**
     * Set the email of the user.
     * @param email - the email of the user
     */
  public void setEmail(String email) {
    this.email = email;
  }

    /**
     * Get the firstname of the user.
     * @return the firstname of the user
     */
  public String getFirstname() {
    return firstname;
  }

    /**
     * Set the firstname of the user.
     * @param firstname - the firstname of the user
     */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

    /**
     * Get the lastname of the user.
     * @return the lastname of the user
     */
  public String getLastname() {
    return lastname;
  }

    /**
     * Set the lastname of the user.
     * @param lastname - the lastname of the user
     */
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

    /**
     * Get the password of the user.
     * @return the password of the user
     */
  public String getPassword() {
    return password;
  }

    /**
     * Set the password of the user.
     * @param password - the password of the user
     */
  public void setPassword(String password) {
    this.password = password;
  }
}
