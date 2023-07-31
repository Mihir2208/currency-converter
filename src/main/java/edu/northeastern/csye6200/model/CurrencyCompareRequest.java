package edu.northeastern.csye6200.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class CurrencyCompareRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private float amount;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Currency baseCurrency;
	private List<Currency> comparisonCurrencies;

	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public Currency getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public List<Currency> getComparisonCurrencies() {
		return comparisonCurrencies;
	}
	public void setComparisonCurrencies(List<Currency> comparisonCurrencies) {
		this.comparisonCurrencies = comparisonCurrencies;
	}
	
	// Might get too verbose in logs for large data
	@Override
	public String toString() {
		return "CurrencyCompareRequest [amount=" + amount + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", baseCurrency=" + baseCurrency + ", comparisonCurrencies size =" 
				+ (comparisonCurrencies != null ? comparisonCurrencies.size() : "Empty")
				+ "]";
	}	
}
