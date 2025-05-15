
package production_code.system_features;
import production_code.core.Invoice;

import java.util.ArrayList;
import java.util.List;

public class FinancialReportGenerator {
	private final double totalRevenue;
	private final int invoiceCount;
	private final List<Invoice> invoices;

	public FinancialReportGenerator(double totalRevenue, List<Invoice> invoices) {
		this.totalRevenue = totalRevenue;
		this.invoiceCount = invoices.size();
		this.invoices = new ArrayList<>(invoices);
	}

	public double getTotalRevenue() { return totalRevenue; }
	public int getInvoiceCount() { return invoiceCount; }
	public List<Invoice> getInvoices() { return new ArrayList<>(invoices); }
}