package ca.mcgill.purposeful.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Class that allows all other cucumber classes to be run
 * @author Shidan Javaheri
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber-report.html"},
    features = {"src/test/resources"},
    extraGlue = "io.tpd.springbootcucumber.bagcommons")
// annotations to configure spring boot tests with a fake client, and to let us autowire objects
// into the cucumber class
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class RunCucumberTest {}
