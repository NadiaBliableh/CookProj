Feature: Generate invoices and track financial reports

  Scenario: Send invoice to customer
    Given a customer has completed an order
    When the order is confirmed
    Then the system generates an invoice
    And the customer receives the invoice

  Scenario: Generate financial report
    Given the system has recorded all transactions
    When the administrator requests a financial report
    Then the system generates the report
    And the administrator can review revenue and performance

