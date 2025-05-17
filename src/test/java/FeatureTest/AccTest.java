package FeatureTest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runs Cucumber tests for the kitchen management system.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = "FeatureTest",
		plugin = {"html:target/cucumber-report/report.html", "json:target/cucumber.json"},
		monochrome = true,
		snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class AccTest {
}