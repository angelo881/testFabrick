package com.fabrick.business;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fabrick.api.ApiController;
import com.fabrick.data.Transaction;
import com.fabrick.exception.ApiException;
import com.fabrick.utility.Constants;
import com.fabrick.utility.Utility;

@Component
public class Business {

	@Autowired
	private RestTemplate rest;

	@Autowired
	private AccountTransactionsManager transactionsManager;

	@Autowired
	private HttpHeaders headers;

	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@SuppressWarnings("unchecked")
	public Map<String, Object> letturaSaldo(long accountId) throws ApiException {

		try {
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			logger.info("create request {}", requestEntity);
			ResponseEntity<Map> response = this.rest
					.exchange(
							new StringBuilder().append("/api/gbs/banking/v4.0/accounts/").append(accountId)
									.append("/balance").toString(),
							HttpMethod.GET, requestEntity, Map.class, new HashMap<String, Object>());
			Map<String, Object> res = response.getBody();
			logger.info("recuperato saldo {}", res);
			return (Map<String, Object>) res.get(Constants.PAYLOAD_KEY);
		} catch (Exception e) {
			logger.warn("errore nel recupero saldo", e);
			throw new ApiException(e);
		}
	}

	public Map<String, Object> bonifico(Long accountId, String creditorName, String accountCode, String description,
			String currency, Double amount, LocalDate executionDate) {

		try {
			HttpEntity<?> entity = new HttpEntity<>(Utility.createTransferRequest(creditorName, accountCode,
					description, currency, amount, executionDate), headers);
			logger.info("create request {}", entity);
			ResponseEntity<Map> response = this.rest.exchange(
					new StringBuilder().append("/api/gbs/banking/v4.0/accounts/").append(accountId)
							.append("/payments/money-transfers").toString(),
					HttpMethod.POST, entity, Map.class, new HashMap<String, Object>());

			logger.info("ricevuta risposta dal money transfer {}", response);
			Map<String, Object> res = response.getBody();
			return (Map<String, Object>) res.get(Constants.PAYLOAD_KEY);
		} catch (Exception e) {
			logger.warn("errore nell'esecuzione del money transfer", e);
			throw new ApiException(e);
		}
	}

	public List<Transaction> letturaTransazioni(Long accountId, LocalDate fromAccountingDate,
			LocalDate toAccountingDate) {
		try {

			return transactionsManager.retrieveList(accountId, fromAccountingDate, toAccountingDate);
		} catch (Exception e) {
			logger.warn("errore nel recupero lista movimenti", e);
			throw new ApiException(e);
		}
	}

}
