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
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api")
public class ApiController {
	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private Business business;

	@ApiOperation(value = "Legge il saldo del conto", notes = "Legge il saldo")
	@GetMapping("/conti/{accountId}")
	public ResponseEntity<?> letturaSaldo(@PathVariable @Parameter(name = "accountId", description = "Conto di cui si vuole il saldo", example = "14537780") Long accountId) {
		logger.debug("ricevuta richiesta letturaSaldo {}", accountId);
		try {
			Map<String, Object> res = this.business.letturaSaldo(accountId);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore generico nel recupero saldo");
		}
	}

	@ApiOperation(value = "Esegue un bonifico", notes = "Esegue il bonifico")
	@PostMapping("/conti/{accountId}/operazioni/bonifico")
	public ResponseEntity<?> bonifico(@PathVariable @Parameter(name = "accountId", description = "Conto da cui si vuole effettuare bonifico", example = "14537780")  Long accountId,
			@RequestParam("creditorName") @Parameter(name = "creditorName", description = "Nome beneficiario")  String creditorName,
			@RequestParam("accountCode") @Parameter(name = "accountCode", description = "IBAN beneficiario")  String accountCode,
			@RequestParam("description")  @Parameter(name = "description", description = "Descrizione movimento")  String description,
			@RequestParam("currency")@Parameter(name = "currency", description = "Valuta") String currency,
			@RequestParam("amount") @Parameter(name = "amount", description = "Importo")  Double amount,
			@RequestParam("executionDate") @Parameter(name = "executionDate", description = "Data esecuzione") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate executionDate) {
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
	@GetMapping("/conti/{accountId}/operazioni/letturaTransazioni")
	public ResponseEntity<?> letturaTransazioni(@RequestParam @Parameter(name = "accountId", description = "Conto di cusi desidera l'elenco transazioni", example = "14537780") Long accountId,
			@RequestParam("fromAccountingDate") @Parameter(name = "fromAccountingDate", description = "Data di partenza intervallo transazioni", example = "2019-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromAccountingDate,
			@RequestParam("toAccountingDate")  @Parameter(name = "toAccountingDate", description = "Data di partenza intervallo transazioni", example = "2019-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toAccountingDate) {
		logger.debug("ricevuta richiesta letturaTransazioni con parametri conto {}, from {}, to {}", accountId,
				fromAccountingDate, toAccountingDate);
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(this.business.letturaTransazioni(accountId, fromAccountingDate, toAccountingDate));
		} catch (ApiException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore generico nell'esecuzione bonifico");
		}
	}

}
