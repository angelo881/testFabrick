package com.fabrick.business;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fabrick.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionsManagerTest {

	@Mock
	private RestTemplate rest;

	@Mock
	private TransactionRepository repo;
	@Mock
	private ObjectMapper mapper;

	@MockBean
	private HttpHeaders httpHeaders;

	@InjectMocks
	private AccountTransactionsManager manager = new AccountTransactionsManager();

	@Test
	public void testListaTransazioniEsterno() throws Exception {

		Map<String, Object> resource = new HashMap<>();
		Map<String, Object> payload = new HashMap<>();
		payload.put("list", new ArrayList<>());
		resource.put("payload", payload);
		Mockito.when(mapper.convertValue(Mockito.any(Object.class), Mockito.any(TypeReference.class)))
				.thenReturn(Collections.EMPTY_LIST);
		ResponseEntity<Map> res = ResponseEntity.status(HttpStatus.OK).body(resource);
		Mockito.when(rest.exchange(Mockito.any(URI.class), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class),
				Mockito.eq(Map.class))).thenReturn(res);
		manager.retrieveList(100023L, LocalDate.now(), LocalDate.now(), new HttpHeaders());
	}

	@Test
	public void testListaTransazioniMemoryDB() throws Exception {

		Mockito.when(
				repo.findTransactions(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.anyLong()))
				.thenReturn(Collections.EMPTY_LIST);
		Mockito.when(mapper.convertValue(Mockito.any(Object.class), Mockito.any(TypeReference.class)))
				.thenReturn(Collections.EMPTY_LIST);

		Map<String, Object> resource = new HashMap<>();
		Map<String, Object> payload = new HashMap<>();
		payload.put("list", new ArrayList<>());
		resource.put("payload", payload);
		ResponseEntity<Map> res = ResponseEntity.status(HttpStatus.OK).body(resource);
		Mockito.when(rest.exchange(Mockito.any(URI.class), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class),
				Mockito.eq(Map.class))).thenReturn(res);
		manager.retrieveList(100023L, LocalDate.now(), LocalDate.now(), new HttpHeaders());
		Mockito.verify(rest).exchange(Mockito.any(URI.class), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class),
				Mockito.eq(Map.class));
	}
}
