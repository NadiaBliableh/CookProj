package production_code.core;
import production_code.actors.Customer;
import production_code.core.Meal;
import production_code.core.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Invoice {
	private final UUID invoiceId;
	private final Customer customer;
	private final Order order;
	private final List<Meal> items;
	private final double totalPrice;
	private boolean sent;

	public Invoice(Customer customer, Order order, double totalPrice) {
		this.invoiceId = UUID.randomUUID();
		this.customer = customer;
		this.order = order;
		this.items = new ArrayList<>(order.getMeals());
		this.totalPrice = totalPrice;
		this.sent = false;
	}

	public UUID getInvoiceId() { return invoiceId; }
	public Customer getCustomer() { return customer; }
	public Order getOrder() { return order; }
	public List<Meal> getItems() { return new ArrayList<>(items); }
	public double getTotalPrice() { return totalPrice; }
	public boolean isSent() { return sent; }
	public void setSent(boolean sent) { this.sent = sent; }
}