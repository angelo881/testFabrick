package com.fabrick.api;

import java.time.LocalDate;
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
import com.fabrick.exception.ApiException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class ApiController {
	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private Business business;

	@ApiOperation(value = "Legge il saldo del conto", notes = "Legge il saldo")
	@GetMapping("/conti/{accountId}")
	public ResponseEntity<?> letturaSaldo(@PathVariable @ApiParam(example = "14537780") Long accountId) {
		logger.debug("ricevuta richiesta letturaSaldo {}", accountId);
		try {
			Map<String, Object> res = this.business.letturaSaldo(accountId);
			return ResponseEntity.status(HttpStatus.OK).body(res.get("availableBalance"));
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore generico nel recupero saldo");
		}
	}

	@ApiOperation(value = "Esegue un bonifico", notes = "Esegue il bonifico")
	@PostMapping("/conti/operazioni/bonifico")
	public ResponseEntity<?> bonifico(@RequestParam @ApiParam(example = "14537780") Long accountId,
			@RequestParam("creditorName") @ApiParam String creditorName,
			@RequestParam("accountCode") @ApiParam String accountCode,
			@RequestParam("description") @ApiParam String description,
			@RequestParam("currency") @ApiParam String currency, @RequestParam("amount") @ApiParam String amount,
			@RequestParam("executionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate executionDate) {
		logger.debug(
				"ricevuta richiesta bonifico con parametri conto {}, creditorName {}, accountCode {}, description {}, currency {}, amount {}, executionDate {}",
				accountId, creditorName, accountCode, description, currency, amount, executionDate);

		try {
			Map<String, Object> res = this.business.bonifico(accountId, creditorName, accountCode, description,
					currency, amount, executionDate);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore generico nell'esecuzione bonifico");
		}
	}

	@ApiOperation(value = "Legge la lista transazioni", notes = "Legge le transazioni")
	@GetMapping("/conti/operazioni/letturaTransazioni")
	public ResponseEntity<?> letturaTransazioni(@RequestParam @ApiParam(example = "14537780") Long accountId,
			@RequestParam("fromAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromAccountingDate,
			@RequestParam("toAccountingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toAccountingDate) {
		logger.debug("ricevuta richiesta letturaTransazioni con parametri conto {}, from {}, to {}", accountId,
				fromAccountingDate, toAccountingDate);
		try {
			Map<String, Object> res = this.business.letturaTransazioni(accountId, fromAccountingDate, toAccountingDate);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore generico nell'esecuzione bonifico");
		}
	}

}
