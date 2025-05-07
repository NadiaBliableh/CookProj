package FeatureTest;
import static production_code.files_manager.DataManager.*;
import static production_code.system_features.FinancialReportGenerator.calculateAndPrintRevenue;
import production_code.actors.Customer;
import production_code.core.Invoice;
import production_code.core.Main;
import production_code.core.Meal;
import production_code.core.Specialty;
import production_code.customer_features.MealOrder;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;

public class GenerateInvoicesAndTrackFinancialReportsTest {
	private Main main;
	private final Customer customer;
	private boolean confirmed;
	
	public GenerateInvoicesAndTrackFinancialReportsTest(Main main) {
		this.main = main;
		this.customer = new Customer("customer", "custpass");
		addCustomer(customer.getUsername(), customer);
		this.confirmed = false;
	}
	
	@Given("a customer has completed an order")
	public void a_customer_has_completed_an_order() {
		List<String> ingredients = new ArrayList<>();
		ingredients.add("Beef");
		ingredients.add("Cheese");
		ingredients.add("Lettuce");
		Meal meal = new Meal(
				ingredients, "BeefMeal", Specialty.BEEF_DISHES, 45
		);
		addMeal("BeefMeal", meal);
		
		MealOrder mealOrder = new MealOrder(customer.getUsername(), meal);
		confirmed = mealOrder.confirmOrder(mealOrder, getMealMap(), customer, getChefSpeciality());
		assert confirmed;
	}
	
	@When("the order is confirmed")
	public void the_order_is_confirmed() {
		assert confirmed;
	}
	
	@Then("the system generates an invoice")
	public void the_system_generates_an_invoice() {
		new Invoice(customer);
		assert Invoice.isInvoiceCreated;
	}
	
	@Then("the customer receives the invoice")
	public void the_customer_receives_the_invoice() {
		assert Invoice.isInvoiceCreated;
	}
	
	@Given("the system has recorded all transactions")
	public void the_system_has_recorded_all_transactions() {
		assert !getCustomersOrders().isEmpty();
	}
	
	@When("the administrator requests a financial report")
	public void the_administrator_requests_a_financial_report() {
		assert calculateAndPrintRevenue();
	}
	
	@Then("the system generates the report")
	public void the_system_generates_the_report() {
		assert calculateAndPrintRevenue();
	}
	
	@Then("the administrator can review revenue and performance")
	public void the_administrator_can_review_revenue_and_performance() {
		assert calculateAndPrintRevenue();
	}
}