package com.fabrick.api;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MyErrorController  implements ErrorController {

	@RequestMapping("/error")
    public ResponseEntity<String> handleError() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Generic error");
    }
}
