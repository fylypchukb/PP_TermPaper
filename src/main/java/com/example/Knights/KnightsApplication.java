package com.example.Knights;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication()
public class KnightsApplication {

	public static void main(String[] args) {
		Application.launch(KnightsApplicationFX.class, args);
	}
	@Bean
	public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
		return new SpringFxWeaver(applicationContext);
	}
}
