package com.fabrick.data;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class RichiestaBonifico {
	private String creditorName;
	private String accountCode;
	private String description;
	private String currency;
	private Double amount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate executionDate;

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}

	@Override
	public String toString() {
		return "RichiestaBonifico [creditorName=" + creditorName + ", accountCode=" + accountCode + ", description="
				+ description + ", currency=" + currency + ", amount=" + amount + ", executionDate=" + executionDate
				+ "]";
	}
}
