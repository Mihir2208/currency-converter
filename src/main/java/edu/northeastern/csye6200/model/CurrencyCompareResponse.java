package edu.northeastern.csye6200.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CurrencyCompareResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDate fromDate;
	private LocalDate toDate;
	private Currency baseCurrency;
	
	// Map key is the currency code
	private Map<String, List<ExchangeRate>> data;

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
	public Map<String, List<ExchangeRate>> getData() {
		return data;
	}
	public void setData(Map<String, List<ExchangeRate>> data) {
		this.data = data;
	}

	// Might get too verbose in logs for large data
	@Override
	public String toString() {
		return "CurrencyCompareResponse [fromDate=" + fromDate + ", toDate=" + toDate + ", baseCurrency=" + baseCurrency
				+ ", data=" + data + "]";
	}
}
