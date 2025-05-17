package production_code.core;

import production_code.actors.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final Customer customer;
    private final List<Meal> meals;

    public Order(Customer customer, List<Meal> meals) {
        if (customer == null || meals == null) {
            throw new IllegalArgumentException("Customer and meals cannot be null");
        }
        this.customer = customer;
        this.meals = new ArrayList<>(meals);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return customer.equals(order.customer) && meals.equals(order.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, meals);
    }

    @Override
    public String toString() {
        return "Order{customer=" + customer + ", meals=" + meals + "}";
    }
}