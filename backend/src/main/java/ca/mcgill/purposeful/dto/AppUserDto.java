package ca.mcgill.purposeful.dto;

public class AppUserDto {

  private String id;
  private String email;
  private String password;
  private String firstname;
  private String lastname;

  public AppUserDto() {
  }

  /**
   * Constructor for AppUserDto.
   *
   * @param email     - the email of the user
   * @param password  - the password of the user
   * @param firstname - the firstname of the user
   * @param lastname  - the lastname of the user
   * @author: Shidan Javaheri
   */
  public AppUserDto(String email, String password, String firstname, String lastname) {
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
