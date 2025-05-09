package FeatureTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/Features",
        glue = "FeatureTest",
		plugin = {"html:target/cucumber-report/report.html"},
		monochrome = true,
		snippets = CucumberOptions.SnippetType.CAMELCASE)


public class AccTest {}