package edu.northeastern.csye6200.model;

import java.io.Serializable;
import java.time.LocalDate;

public class ExchangeRate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Currency fromCurrency;
	private Currency toCurrency;
	private LocalDate date;
	private long timestamp;
	private String dateString;
	private float rate;
	private float value;
	private float convertedValue;
	
	public Currency getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(Currency fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	public Currency getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(Currency toCurrency) {
		this.toCurrency = toCurrency;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public float getConvertedValue() {
		return convertedValue;
	}
	public void setConvertedValue(float convertedValue) {
		this.convertedValue = convertedValue;
	}

	@Override
	public String toString() {
		return "ExchangeRate [fromCurrency=" + fromCurrency + ", toCurrency=" + toCurrency + ", date=" + date
				+ ", timestamp=" + timestamp + ", dateString=" + dateString + ", rate=" + rate + ", value=" + value
				+ ", convertedValue=" + convertedValue + "]";
	}
}
