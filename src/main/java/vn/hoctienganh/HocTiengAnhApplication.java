package vn.hoctienganh;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import vn.hoctienganh.services.TranslationService;

@SpringBootApplication
@EnableFeignClients
public class HocTiengAnhApplication {

	public static void main(String[] args) {
		SpringApplication.run(HocTiengAnhApplication.class, args);
	}


	@Bean
	CommandLineRunner run(TranslationService translationService) {
		return args -> {
			translationService.detectLanguage("Hello World");
		};
	}
}
