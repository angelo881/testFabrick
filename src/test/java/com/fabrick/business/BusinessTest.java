package com.fabrick.business;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fabrick.data.RichiestaBonifico;
import com.fabrick.exception.ApiException;

@ExtendWith(MockitoExtension.class)
public class BusinessTest {

	@Mock
	private RestTemplate rest;

	@Mock
	private AccountTransactionsManager manager;

	@Mock
	private HttpHeaders httpHeaders;

	@InjectMocks
	private Business busi = new Business();

	@Test
	public void testLetturaSaldoOK() {

		Map<String, Object> resource = new HashMap<>();
		resource.put("payload", new HashMap<>());
		ResponseEntity<Map> res = ResponseEntity.status(HttpStatus.OK).body(resource);
		Mockito.when(rest.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class),
				Mockito.eq(Map.class), Mockito.anyMap())).thenReturn(res);
		busi.letturaSaldo(100023L);
	}

	@Test
	public void testLetturaSaldoKO() {

		Assertions.assertThrows(ApiException.class, () -> {
			Mockito.when(rest.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
					Mockito.any(HttpEntity.class), Mockito.eq(Map.class), Mockito.anyMap()))
					.thenThrow(IllegalArgumentException.class);
			busi.letturaSaldo(100023L);
		});
	}

	@Test
	public void testBonificoOK() {

		Map<String, Object> resource = new HashMap<>();
		resource.put("payload", new HashMap<>());
		Mockito.when(rest.postForObject(Mockito.anyString(), Mockito.any(HttpEntity.class), Mockito.eq(Map.class)))
				.thenReturn(resource);
		busi.bonifico(100023L, Mockito.mock(RichiestaBonifico.class));
	}

	@Test
	public void testBonificoKO() {

		Assertions.assertThrows(ApiException.class, () -> {
			Mockito.when(rest.postForObject(Mockito.anyString(), Mockito.any(HttpEntity.class), Mockito.eq(Map.class)))
					.thenThrow(IllegalArgumentException.class);
			busi.bonifico(100023L, Mockito.mock(RichiestaBonifico.class));

		});
	}

	@Test
	public void testListaTransazioniOK() throws Exception {

		Mockito.when(manager.retrieveList(Mockito.anyLong(), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class),
				Mockito.any(HttpHeaders.class))).thenReturn(Collections.EMPTY_LIST);
		busi.letturaTransazioni(100023L, LocalDate.now(), LocalDate.now());
	}

	@Test
	public void testListaTransazioniKO() {

		Assertions.assertThrows(ApiException.class, () -> {
			Mockito.when(manager.retrieveList(Mockito.anyLong(), Mockito.any(LocalDate.class),
					Mockito.any(LocalDate.class), Mockito.any(HttpHeaders.class)))
					.thenThrow(IllegalArgumentException.class);
			busi.letturaTransazioni(100023L, LocalDate.now(), LocalDate.now());
		});
	}

}
