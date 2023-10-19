package com.fabrick.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabrick.business.Business;
import com.fabrick.data.RichiestaBonifico;
import com.fabrick.data.Transaction;
import com.fabrick.data.response.Response;
import com.fabrick.data.response.ResponseCode;
import com.fabrick.exception.ApiException;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class ApiController {
	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private Business business;

	@ApiOperation(value = "Legge il saldo del conto", notes = "Legge il saldo")
	@GetMapping("/conti/{accountId}")
	public ResponseEntity<Response<Map>> letturaSaldo(
			@PathVariable @Parameter(name = "accountId", description = "Conto di cui si vuole il saldo", example = "14537780") Long accountId) {
		logger.debug("ricevuta richiesta letturaSaldo {}", accountId);
		Response<Map> resp;
		try {
			resp = new Response<>(this.business.letturaSaldo(accountId));
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} catch (ApiException e) {
			resp = new Response<>(ResponseCode.ERROR, "errore chiamata api");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}

	@ApiOperation(value = "Esegue un bonifico", notes = "Esegue il bonifico")
	@PostMapping("/conti/{accountId}/operazioni/bonifico")
	public ResponseEntity<Response<Map>> bonifico(
			@PathVariable @Parameter(name = "accountId", description = "Conto da cui si vuole effettuare bonifico", example = "14537780") Long accountId,
			@RequestBody @Parameter(description = "dati relativi al bonifico") RichiestaBonifico data) {
		logger.debug("ricevuta richiesta bonifico con parametri conto {}, data {}", accountId, data);
		Response<Map> resp;
		try {
			resp = new Response<>(this.business.bonifico(accountId, data));
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} catch (ApiException e) {
			resp = new Response<>(ResponseCode.ERROR, "errore chiamata api");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}

	@ApiOperation(value = "Legge la lista transazioni", notes = "Legge le transazioni")
	@GetMapping("/conti/{accountId}/operazioni/letturaTransazioni")
	public ResponseEntity<Response<List<Transaction>>> letturaTransazioni(
			@RequestParam @Parameter(name = "accountId", description = "Conto di cusi desidera l'elenco transazioni", example = "14537780") Long accountId,
			@RequestParam("fromAccountingDate") @Parameter(name = "fromAccountingDate", description = "Data di partenza intervallo transazioni", example = "2019-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromAccountingDate,
			@RequestParam("toAccountingDate") @Parameter(name = "toAccountingDate", description = "Data di partenza intervallo transazioni", example = "2019-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toAccountingDate) {
		logger.debug("ricevuta richiesta letturaTransazioni con parametri conto {}, from {}, to {}", accountId,
				fromAccountingDate, toAccountingDate);
		Response<List<Transaction>> resp;
		try {
			resp = new Response<>(this.business.letturaTransazioni(accountId, fromAccountingDate, toAccountingDate));
			return ResponseEntity.status(HttpStatus.OK).body(resp);
		} catch (ApiException e) {
			resp = new Response<>(ResponseCode.ERROR, "errore chiamata api");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
		}
	}

}
