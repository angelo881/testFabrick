package com.fabrick.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fabrick.business.Business;
import com.fabrick.exception.ApiException;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {
	@Mock
	private Business business;

	@InjectMocks
	private ApiController controller = new ApiController();

	@Test
	public void testLetturaSaldo() {
		ResponseEntity<?> resp = controller.letturaSaldo(100023L);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@Test
	public void testLetturaSaldoKO() {
		Mockito.when(business.letturaSaldo(Mockito.anyLong())).thenThrow(ApiException.class);
		ResponseEntity<?> resp = controller.letturaSaldo(100023L);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}

	@Test
	public void testBonifico() {
		ResponseEntity<?> resp = controller.bonifico(100023L, "test", "test", "test", "test", 3121.312, LocalDate.now());
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@Test
	public void testBonificoKO() {
		Mockito.when(business.bonifico(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyDouble(), Mockito.any(LocalDate.class))).thenThrow(ApiException.class);
		ResponseEntity<?> resp = controller.bonifico(100023L, "test", "test", "test", "test",3121.312, LocalDate.now());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}

	@Test
	public void testLetturaTransazioni() {
		ResponseEntity<?> resp = controller.letturaTransazioni(100023L, LocalDate.now(), LocalDate.now());
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@Test
	public void testLetturaTransazioniKO() {
		Mockito.when(business.letturaTransazioni(Mockito.anyLong(), Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class))).thenThrow(ApiException.class);
		ResponseEntity<?> resp = controller.letturaTransazioni(100023L, LocalDate.now(), LocalDate.now());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}

}
