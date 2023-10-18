package com.fabrick.business;

import java.util.HashMap;
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
import com.fabrick.data.ResponseBonifico;
import com.fabrick.exception.ApiException;

@Component
public class Business {

	@Autowired
	private RestTemplate rest;

	@Autowired
	private HttpHeaders headers;

	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@SuppressWarnings("unchecked")
	public Map<String, Object> letturaSaldo(long accountId) throws ApiException {

		try {
			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			ResponseEntity<Map> response = this.rest.exchange(
					"/api/gbs/banking/v4.0/accounts/" + accountId + "/balance", HttpMethod.GET, requestEntity,
					Map.class, new HashMap<String, Object>());
			Map<String, Object> res = response.getBody();
			logger.info("recuperato saldo {}", res);
			return (Map<String, Object>) res.get("payload");
		} catch (Exception e) {
			logger.warn("errore nel recupero saldo", e);
			throw new ApiException(e);
		}
	}

	public ResponseBonifico bonifico() {
		return null;
	}

	public ResponseBonifico letturaTransazioni() {
		return null;
	}

}
