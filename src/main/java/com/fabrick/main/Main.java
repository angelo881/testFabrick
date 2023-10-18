package com.fabrick.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
@ComponentScan({"com.fabrick.api","com.fabrick.business"})
public class Main {

	  static Logger logger = LoggerFactory.getLogger(Main.class);
	  
		public static void main(String[] args) throws Exception {
			SpringApplication app = new SpringApplication(Main.class);

			app.run(args);

			logger.info("main started");

		}
	
}
