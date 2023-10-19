package com.fabrick.business;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fabrick.api.ApiController;
import com.fabrick.data.RichiestaBonifico;
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

	@Value("${BaseUrl}")
	String BaseUrl;

	@Value("${Auth-Schema}")
	String authSchema;

	@Value("${Api-Key}")
	String apiKey;

	@Value("${accountId}")
	String accountId;

	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@SuppressWarnings("unchecked")
	public Map<String, Object> letturaSaldo(long accountId) throws ApiException {

		try {
			HttpHeaders headers = httpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
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

	@SuppressWarnings("unchecked")
	public Map<String, Object> bonifico(Long accountId, RichiestaBonifico data) {

		try {
			HttpHeaders headers = httpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<>(Utility.createTransferRequest(data), headers);
			logger.info("create request {}", entity);
			Map response = this.rest.postForObject(new StringBuilder().append("/api/gbs/banking/v4.0/accounts/")
					.append(accountId).append("/payments/money-transfers").toString(), entity, Map.class);

			logger.info("ricevuta risposta dal money transfer {}", response);

			return (Map<String, Object>) response.get(Constants.PAYLOAD_KEY);
		} catch (Exception e) {
			logger.warn("errore nell'esecuzione del money transfer", e);
			throw new ApiException(e);
		}
	}

	public List<Transaction> letturaTransazioni(Long accountId, LocalDate fromAccountingDate,
			LocalDate toAccountingDate) {
		try {
			HttpHeaders headers = httpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			return transactionsManager.retrieveList(accountId, fromAccountingDate, toAccountingDate, headers);
		} catch (Exception e) {
			logger.warn("errore nel recupero lista movimenti", e);
			throw new ApiException(e);
		}
	}

	private HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.AUTH_SCHEMA_KEY, authSchema);
		headers.set(Constants.BASE_URL_KEY, BaseUrl);
		headers.set(Constants.API_KEY_KEY, apiKey);
		headers.set(Constants.ACCOUNT_ID_KEY, accountId);
		return headers;
	}

}
