package ca.mcgill.purposeful.features;

import java.util.ArrayList;
import ca.mcgill.purposeful.dao.DomainRepository;
import ca.mcgill.purposeful.dao.IdeaRepository;
import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.dao.TopicRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.util.CucumberUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.purposeful.service.AppUserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;


public class ID015_createIdeaStepDefinitions {
  
  @Autowired
  AppUserService appUserService;
  
  @Autowired 
  DomainRepository domainRepository;
  
  @Autowired 
  TechnologyRepository technologyRepository;
  
  @Autowired 
  TopicRepository topicRepository;
  
  @Autowired 
  IdeaRepository ideaRepository;
  
  @Autowired
  private TestRestTemplate client;

  @Autowired
  private CucumberUtil cucumberUtil;

  private String jwtToken;
  
  private ResponseEntity<?> response;
  
  @Given("the database contains the following user account:")
  public void the_database_contains_the_following_user_account(io.cucumber.datatable.DataTable dataTable) {
    // create app user
    appUserService.registerRegularUser(
          dataTable.cell(1, 2), dataTable.cell(1, 3), dataTable.cell(1, 0), dataTable.cell(1, 1));
  }

  @Given("the number of ideas in the database is {int}")
  public void the_number_of_ideas_in_the_database_is(Integer int1) {
      assertEquals(((ArrayList<Idea>)ideaRepository.findAll()).size(), int1);
  }

  @Given("the database contains the following domains:")
  public void the_database_contains_the_following_domains(io.cucumber.datatable.DataTable dataTable) {
      for(String domain_name: dataTable.asList()) {
        Domain domain = new Domain();
        domain.setName(domain_name);
        domainRepository.save(domain);
      }
  }

  @Given("the database contains the following topics:")
  public void the_database_contains_the_following_topics(io.cucumber.datatable.DataTable dataTable) {
    for(String topic_name: dataTable.asList()) {
      Topic topic = new Topic();
      topic.setName(topic_name);
      topicRepository.save(topic);
    }
  }

  @Given("the database contains the following techs:")
  public void the_database_contains_the_following_techs(io.cucumber.datatable.DataTable dataTable) {
    for(String tech_name: dataTable.asList()) {
      Technology tech = new Technology();
      tech.setName(tech_name);
      technologyRepository.save(tech);
    }
  }

  @Given("that the user is logged in with the email {string} and the password {string}")
  public void that_the_user_is_logged_in_with_the_email_and_the_password(String string, String string2) {
      if(SecurityContextHolder.getContext().getAuthentication() == null) {
        // Login as the owner
        HttpEntity<String> requestEntity = new HttpEntity<>(
            cucumberUtil.basicAuthHeader(string, string2));
        this.response = client.exchange("/login", HttpMethod.POST, requestEntity, String.class);

        // check that the login was successful
        assertEquals(200, this.response.getStatusCode().value());

        // save the jwt token
        this.jwtToken = this.response.getBody().toString();
      }
  }

  @When("^the user creates an idea with title (.+), purpose (.+), description (.+), paid status (.+), progress status (.+), private status (.+), domains (.+), topics (.+),  techs (.+), icon (.+), supporting images (.+)$")
  public void the_user_creates_an_idea_with_title_purpose_description_paid_status_progress_status_private_status_domains_topics_techs_icon_supporting_images(String title, String purpose, String description, String ispaid, String inprogress, String isprivate, String domains, String topics, String techs, String iconurl, String supportingimageurls) throws Throwable {
      // Write code here that turns the phrase above into concrete actions
    Boolean isPaid = Boolean.parseBoolean(ispaid);
    Boolean inProgress = Boolean.parseBoolean(inprogress);
    Boolean isPrivate = Boolean.parseBoolean(isprivate);
      throw new io.cucumber.java.PendingException();
  }

  @Then("a new idea exists in the database with title Smart Schedule Manager, icon schedule.png")
  public void a_new_idea_exists_in_the_database_with_title_smart_schedule_manager_icon_schedule_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title exampleTitle, purpose improve testing, description Example description, paid status True, progress status False, private status False, domains Software,Other, topics AI,Machine Learning,  techs , icon schedule.png, supporting images ")
  public void the_user_creates_an_idea_with_title_example_title_purpose_improve_testing_description_example_description_paid_status_true_progress_status_false_private_status_false_domains_software_other_topics_ai_machine_learning_techs_icon_schedule_png_supporting_images() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("a new idea exists in the database with title exampleTitle, icon schedule.png")
  public void a_new_idea_exists_in_the_database_with_title_example_title_icon_schedule_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title , purpose Save people time, description AI daily schedule builder, paid status True, progress status False, private status False, domains Software,Science, topics AI,Machine Learning,  techs Tensorflow,Python, icon schedule.png, supporting images img.png,img2.png")
  public void the_user_creates_an_idea_with_title_purpose_save_people_time_description_ai_daily_schedule_builder_paid_status_true_progress_status_false_private_status_false_domains_software_science_topics_ai_machine_learning_techs_tensorflow_python_icon_schedule_png_supporting_images_img_png_img2_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error Idea titles cannot be empty shall be raised")
  public void the_following_error_idea_titles_cannot_be_empty_shall_be_raised() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title Smart Schedule Manager, purpose , description AI daily schedule builder, paid status True, progress status False, private status False, domains Software,Science, topics AI,Machine Learning,  techs Tensorflow,Python, icon schedule.png, supporting images img.png,img2.png")
  public void the_user_creates_an_idea_with_title_smart_schedule_manager_purpose_description_ai_daily_schedule_builder_paid_status_true_progress_status_false_private_status_false_domains_software_science_topics_ai_machine_learning_techs_tensorflow_python_icon_schedule_png_supporting_images_img_png_img2_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error Purpose cannot be empty shall be raised")
  public void the_following_error_purpose_cannot_be_empty_shall_be_raised() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title Smart Schedule Manager, purpose Save people time, description , paid status True, progress status False, private status False, domains Software,Science, topics AI,Machine Learning,  techs Tensorflow,Python, icon schedule.png, supporting images img.png,img2.png")
  public void the_user_creates_an_idea_with_title_smart_schedule_manager_purpose_save_people_time_description_paid_status_true_progress_status_false_private_status_false_domains_software_science_topics_ai_machine_learning_techs_tensorflow_python_icon_schedule_png_supporting_images_img_png_img2_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error Description cannot be empty shall be raised")
  public void the_following_error_description_cannot_be_empty_shall_be_raised() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title exampleTitle, purpose improve testing, description Example description, paid status True, progress status , private status False, domains , topics AI,Machine Learning,  techs , icon schedule.png, supporting images ")
  public void the_user_creates_an_idea_with_title_example_title_purpose_improve_testing_description_example_description_paid_status_true_progress_status_private_status_false_domains_topics_ai_machine_learning_techs_icon_schedule_png_supporting_images() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error You must specify at least {int} domain shall be raised")
  public void the_following_error_you_must_specify_at_least_domain_shall_be_raised(Integer int1) {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title exampleTitle, purpose improve testing, description Example description, paid status True, progress status , private status False, domains Software,Science, topics ,  techs , icon schedule.png, supporting images ")
  public void the_user_creates_an_idea_with_title_example_title_purpose_improve_testing_description_example_description_paid_status_true_progress_status_private_status_false_domains_software_science_topics_techs_icon_schedule_png_supporting_images() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error You must specify at least {int} topic shall be raised")
  public void the_following_error_you_must_specify_at_least_topic_shall_be_raised(Integer int1) {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title Smart Schedule Manager, purpose Save people time, description AI daily schedule builder, paid status True, progress status False, private status False, domains Software,Science, topics AI,Machine Learning,  techs Tensorflow,Python, icon , supporting images img.png,img2.png")
  public void the_user_creates_an_idea_with_title_smart_schedule_manager_purpose_save_people_time_description_ai_daily_schedule_builder_paid_status_true_progress_status_false_private_status_false_domains_software_science_topics_ai_machine_learning_techs_tensorflow_python_icon_supporting_images_img_png_img2_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error Icon URL cannot be empty shall be raised")
  public void the_following_error_icon_url_cannot_be_empty_shall_be_raised() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title Smart Schedule Manager, purpose Save people time, description AI daily schedule builder, paid status True, progress status False, private status False, domains Software,Science, topics AI,Machine Learning,  techs Tensorflow,Python, icon schedule.pdf, supporting images ")
  public void the_user_creates_an_idea_with_title_smart_schedule_manager_purpose_save_people_time_description_ai_daily_schedule_builder_paid_status_true_progress_status_false_private_status_false_domains_software_science_topics_ai_machine_learning_techs_tensorflow_python_icon_schedule_pdf_supporting_images() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error Invalid icon format shall be raised")
  public void the_following_error_invalid_icon_format_shall_be_raised() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @When("the user creates an idea with title wfbypsX1842YKvElZNfDYCtjMU6UZXXF15KltjAsMdpYt8VFBLbpaS28R8PyiUrUJX79y77dtmEyrnfzOk0aAIp2wz8wX3J6wjynwqe, purpose Save people time, description AI daily schedule builder, paid status True, progress status False, private status False, domains Software,Science, topics AI,Machine Learning,  techs Tensorflow,Python, icon schedule.png, supporting images img.png,img2.png")
  public void the_user_creates_an_idea_with_title_wfbyps_x1842y_kv_el_z_nf_dy_ctj_mu6uzxxf15kltj_as_mdp_yt8vfb_lbpa_s28r8pyi_ur_ujx79y77dtm_eyrnfz_ok0a_a_ip2wz8w_x3j6wjynwqe_purpose_save_people_time_description_ai_daily_schedule_builder_paid_status_true_progress_status_false_private_status_false_domains_software_science_topics_ai_machine_learning_techs_tensorflow_python_icon_schedule_png_supporting_images_img_png_img2_png() {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }

  @Then("the following error Idea titles cammot exceed {int} characters shall be raised")
  public void the_following_error_idea_titles_cammot_exceed_characters_shall_be_raised(Integer int1) {
      // Write code here that turns the phrase above into concrete actions
      throw new io.cucumber.java.PendingException();
  }
}
