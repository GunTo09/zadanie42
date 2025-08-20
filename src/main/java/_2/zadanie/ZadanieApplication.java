package _2.zadanie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ZadanieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZadanieApplication.class, args);
	}

}
