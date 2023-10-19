package com.fabrick.business;

import java.net.URI;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fabrick.data.Transaction;
import com.fabrick.repository.TransactionRepository;
import com.fabrick.utility.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AccountTransactionsManager {

	protected static final Logger logger = LoggerFactory.getLogger(AccountTransactionsManager.class);

	@Autowired
	private RestTemplate rest;

	@Value("${BaseUrl}")
	String BaseUrl;

	@Autowired
	private TransactionRepository repo;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private HttpHeaders headers;

	public List<Transaction> retrieveList(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate)
			throws Exception {
		List<Transaction> result = this.repo.findTransactions(fromAccountingDate, toAccountingDate, accountId);
		// utilizzo il database in memoria come cache, se ho già i risultati salvati, li
		// recupero dal db, piu veloce piuttosto che interrogare l'api esterna
		if (result.size() == 0) {
			logger.debug("non presenti dati sul database in memoria, interrogo API esterna...");
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			Map<String, String> uriParams = new HashMap<>();
			uriParams.put(Constants.ACCOUNT_ID_KEY, accountId.toString());
			URI productUri = UriComponentsBuilder
					.fromUriString(new StringBuilder().append(this.BaseUrl)
							.append("/api/gbs/banking/v4.0/accounts/{accountId}/transactions").toString())
					.queryParam(Constants.FROM_ACCOUNTING_DATE_KEY, fromAccountingDate)
					.queryParam(Constants.TO_ACCOUNTING_DATE_KEY, toAccountingDate).buildAndExpand(uriParams).toUri();
			logger.info("create uri {}", productUri);
			ResponseEntity<Map> response = this.rest.exchange(productUri, HttpMethod.GET, requestEntity, Map.class);
			Map<String, Object> res = (Map<String, Object>) response.getBody().get(Constants.PAYLOAD_KEY);
			TypeReference<List<Transaction>> typeReference = new TypeReference<List<Transaction>>() {
			};
			result = mapper.convertValue((List<Map<String, Object>>) res.get(Constants.LIST_KEY), typeReference);
			// imposto l'id del conto sul mio database locale, mi serve per leggere da li se
			// i dati sono presenti
			result.forEach(transaction -> transaction.setAccountId(accountId));
			logger.debug("recuperato lista movimenti {}", res);

			this.repo.saveAll(result);
			logger.info("salvata lista movimenti in db in memoria");

		}

		return result;
	}
}
