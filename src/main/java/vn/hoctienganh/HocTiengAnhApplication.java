package vn.hoctienganh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HocTiengAnhApplication {

	public static void main(String[] args) {
		SpringApplication.run(HocTiengAnhApplication.class, args);
	}

}
