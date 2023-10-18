package com.fabrick.business;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
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

import com.fabrick.exception.ApiException;

@ExtendWith(MockitoExtension.class)
public class BusinessTest {

	@Mock
	private RestTemplate rest;

	@MockBean
	private HttpHeaders headers;

	@InjectMocks
	private Business busi = new Business();

	@Test
	public void testLetturaSaldoOK() {

		Map<String, Object> resource = new HashMap<>();
		resource.put("payload", new HashMap<>());
		ResponseEntity<Map> res = ResponseEntity.status(HttpStatus.OK).body(resource);
		Mockito.when(rest.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
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

}
