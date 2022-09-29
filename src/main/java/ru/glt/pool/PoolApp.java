package ru.glt.pool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class PoolApp {
	private static final TimeZone systemTimeZone = TimeZone.getDefault();

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(PoolApp.class, args);
	}

	public static TimeZone getSystemTimeZone() {
		return systemTimeZone;
	}
}
