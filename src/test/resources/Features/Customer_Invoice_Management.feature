Feature: Customer Invoice Management As a restaurant system I want to generate and manage customer invoices So that customers receive accurate billing details

  Scenario: Generating an invoice for a single purchase
    Given a customer has completed a purchase
    When the system processes the purchase
    Then an invoice is generated with purchase details
    And the invoice is sent to the customer's email

  Scenario: Generating an invoice for multiple items
    Given a customer has purchased multiple items
    When the system processes the purchase
    Then an invoice is generated listing all items with their prices
    And the invoice is sent to the customer's email

  Scenario: Regenerating a lost invoice
    Given a customer has lost their original invoice
    When the customer requests a new invoice
    Then the system regenerates the invoice with the same details
    And the invoice is sent to the customer's email