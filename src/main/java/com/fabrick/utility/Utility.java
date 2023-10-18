package com.fabrick.utility;

import java.time.LocalDate;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public final class Utility {

	public static MultiValueMap<String, Object> createTransferRequest(String creditorName, String accountCode,
			String description, String currency, Double amount, LocalDate executionDate) {
		MultiValueMap<String, Object> res = new LinkedMultiValueMap();
		MultiValueMap<String, Object> creditor = new LinkedMultiValueMap();
		creditor.add(Constants.NAME_KEY, creditorName);
		MultiValueMap<String, Object> account = new LinkedMultiValueMap();
		account.add(Constants.ACCOUNT_CODE_KEY, accountCode);
		creditor.add(Constants.ACCOUNT_KEY, account);
		res.add(Constants.DESCRIPTION_KEY, description);
		res.add(Constants.CURRENCY_KEY, currency);
		res.add(Constants.AMOUNT_KEY, amount);
		res.add(Constants.EXECUTION_DATE_KEY, executionDate);
		res.add(Constants.CREDITOR_KEY, creditor);
		return res;
	}

}
