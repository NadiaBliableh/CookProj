package production_code.core;

import production_code.actors.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class Invoice {
	private final String invoiceId;
	private final Order order;
	private boolean sent;
	private double totalPrice;


	public Invoice(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		this.invoiceId = UUID.randomUUID().toString();
		this.order = order;
		this.sent = false;
		this.totalPrice = calculateTotalPrice();
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public Order getOrder() {
		return order;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public double getTotalPrice() {
		return totalPrice;
	}


	private double calculateTotalPrice() {
		double total = 0.0;
		for (Meal meal : order.getMeals()) {
			total += meal.getIngredients().size() * 5.0; // Charge 5.0 per ingredient
		}
		return total;
	}


	public void markAsSent() {
		this.sent = true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Invoice invoice = (Invoice) o;
		return invoiceId.equals(invoice.invoiceId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(invoiceId);
	}

	@Override
	public String toString() {
		return "Invoice{invoiceId='" + invoiceId + "', sent=" + sent + ", totalPrice=" + totalPrice + "}";
	}


	public Meal[] getItems() {
		List<Meal> meals = order.getMeals();
		if (meals == null) {
			return new Meal[0];
		}
		return meals.toArray(new Meal[0]);
	}


	public Customer getCustomer() {
		return order.getCustomer();
	}
}