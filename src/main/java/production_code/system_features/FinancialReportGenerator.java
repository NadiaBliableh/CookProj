package production_code.system_features;

import production_code.core.Invoice;

import java.util.ArrayList;
import java.util.List;

public class FinancialReportGenerator {
	private List<Invoice> invoices;
	private double totalRevenue;
	private int invoiceCount;

	public FinancialReportGenerator() {
		this.invoices = new ArrayList<>();
		this.totalRevenue = 0.0;
		this.invoiceCount = 0;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = new ArrayList<>(invoices);
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public void setInvoiceCount(int invoiceCount) {
		this.invoiceCount = invoiceCount;
	}

	public List<Invoice> getInvoices() {
		return new ArrayList<>(invoices);
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public int getInvoiceCount() {
		return invoiceCount;
	}
}