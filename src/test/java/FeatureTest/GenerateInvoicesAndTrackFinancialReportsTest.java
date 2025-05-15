package FeatureTest;
import io.cucumber.java.en.Then;
import production_code.actors.Customer;

import production_code.core.Ingredients;
import production_code.core.Meal;
import production_code.core.Order;
import production_code.core.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.actors.Customer;

import production_code.core.Ingredients;
import production_code.core.Meal;
import production_code.core.Order;
import production_code.core.Invoice;
import production_code.core.Mainn;
import production_code.system_features.FinancialReportGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;



public class GenerateInvoicesAndTrackFinancialReportsTest {
	private Mainn app;
	private FinancialReportGenerator lastFinancialReport;

	public GenerateInvoicesAndTrackFinancialReportsTest() {
		app = new Mainn();
	}

	@Given("multiple customers have completed purchases")
	public void multiple_customers_have_completed_purchases() {
		// Customer 1: John Doe
		Customer customer1 = new Customer("John Doe", "Vegan", new ArrayList<>());
		app.addCustomer(customer1);
		List<Ingredients> saladIngredients = new ArrayList<>();
		saladIngredients.add(new Ingredients("Tomato", true));
		saladIngredients.add(new Ingredients("Lettuce", true));
		Meal salad = new Meal(saladIngredients);
		List<Meal> meals1 = new ArrayList<>();
		meals1.add(salad);
		Order order1 = new Order(meals1);
		app.addOrder(order1);
		app.addPastOrder(customer1, "Salad");
		app.createInvoice(customer1, order1);

		// Customer 2: Jane Doe
		Customer customer2 = new Customer("Jane Doe", "Vegetarian", new ArrayList<>());
		app.addCustomer(customer2);
		List<Ingredients> soupIngredients = new ArrayList<>();
		soupIngredients.add(new Ingredients("Carrot", true));
		soupIngredients.add(new Ingredients("Onion", true));
		Meal soup = new Meal(soupIngredients);
		List<Meal> meals2 = new ArrayList<>();
		meals2.add(salad);
		meals2.add(soup);
		Order order2 = new Order(meals2);
		app.addOrder(order2);
		app.addPastOrder(customer2, "Salad");
		app.addPastOrder(customer2, "Soup");
		app.createInvoice(customer2, order2);
	}

	@Given("no customers have completed purchases")
	public void no_customers_have_completed_purchases() {
		// No setup needed; app starts with empty salesRecords
		app.resetLists();
	}

	@When("the administrator requests a financial report")
	public void the_administrator_requests_a_financial_report() {
		// Generate financial report using MyApplication
		lastFinancialReport = app.generateFinancialReport();
	}

	@Then("a financial report is generated with total revenue and invoice details")
	public void a_financial_report_is_generated_with_total_revenue_and_invoice_details() {
		assertNotNull("Financial report should be generated", lastFinancialReport);
		assertEquals("Report should include all invoices", 2, lastFinancialReport.getInvoiceCount());
		assertEquals("Total revenue should reflect all invoices", 30.0, lastFinancialReport.getTotalRevenue(), 0.01); // 10 + 20
		assertFalse("Report invoices should not be empty", lastFinancialReport.getInvoices().isEmpty());
	}

	@Then("a financial report is generated with zero revenue and no invoices")
	public void a_financial_report_is_generated_with_zero_revenue_and_no_invoices() {
		assertNotNull("Financial report should be generated", lastFinancialReport);
		assertEquals("Report should have no invoices", 0, lastFinancialReport.getInvoiceCount());
		assertEquals("Total revenue should be zero", 0.0, lastFinancialReport.getTotalRevenue(), 0.01);
		assertTrue("Report invoices should be empty", lastFinancialReport.getInvoices().isEmpty());
	}
}