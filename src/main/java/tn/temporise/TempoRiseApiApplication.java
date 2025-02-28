package tn.temporise.tempo_rise_api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "tn.temporise")
@EnableJpaRepositories(basePackages = "tn.temporise")
@EntityScan(basePackages = "tn.temporise.domain.model")
public class TempoRiseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TempoRiseApiApplication.class, args);}
}
