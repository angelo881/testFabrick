package com.fabrick.data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import io.hypersistence.utils.hibernate.type.json.JsonType;

@Entity
@Table(name = "transactions")
@TypeDef(name = "json", typeClass = JsonType.class)
public class Transaction {

	@Id
	private String transactionId;

	@Column(name = "operationId")
	private String operationId;

	@Column(name = "accountingDate")
	private LocalDate accountingDate;

	@Column(name = "accountId")
	private Long accountId;

	@Column(name = "valueDate")
	private LocalDate valueDate;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "description")
	private String description;

	@Type(type = "json")
	@Column(name = "type", columnDefinition = "json")
	private Map<String, String> type = new HashMap<>();

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public LocalDate getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(LocalDate accountingDate) {
		this.accountingDate = accountingDate;
	}

	public LocalDate getValueDate() {
		return valueDate;
	}

	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getType() {
		return type;
	}

	public void setType(Map<String, String> type) {
		this.type = type;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", operationId=" + operationId + ", accountId="
				+ accountId + ", accountingDate=" + accountingDate + ", valueDate=" + valueDate + ", amount=" + amount
				+ ", currency=" + currency + ", description=" + description + "]";
	}
}
