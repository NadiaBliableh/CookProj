Feature: Generate Financial Reports
  As a system administrator, I want to generate financial reports so that I can analyze revenue and track business performance.

  Scenario: Administrator generates a financial report for multiple purchases
    Given multiple customers have completed purchases
    When the administrator requests a financial report
    Then a financial report is generated with total revenue and invoice details

  Scenario: Administrator generates a financial report with no purchases
    Given no customers have completed purchases
    When the administrator requests a financial report
    Then a financial report is generated with zero revenue and no invoices