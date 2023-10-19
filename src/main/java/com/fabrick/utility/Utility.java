package com.fabrick.utility;

import java.util.HashMap;
import java.util.Map;

import com.fabrick.data.RichiestaBonifico;

public final class Utility {

	public static Map createTransferRequest(RichiestaBonifico bonifico) {
		Map res = new HashMap();
		Map creditor = new HashMap();
		creditor.put(Constants.NAME_KEY, bonifico.getCreditorName());
		Map account = new HashMap();
		account.put(Constants.ACCOUNT_CODE_KEY, bonifico.getAccountCode());
		creditor.put(Constants.ACCOUNT_KEY, account);
		res.put(Constants.DESCRIPTION_KEY, bonifico.getDescription());
		res.put(Constants.CURRENCY_KEY, bonifico.getCurrency());
		res.put(Constants.AMOUNT_KEY, bonifico.getAmount());
		res.put(Constants.EXECUTION_DATE_KEY, bonifico.getExecutionDate());
		res.put(Constants.CREDITOR_KEY, creditor);
		return res;
	}

}
