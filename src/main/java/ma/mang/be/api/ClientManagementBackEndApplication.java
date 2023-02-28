package ma.mang.be.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * General Config Class
 * @author Achraf
 * @version v0.1
 */
@SpringBootApplication(exclude = IntegrationAutoConfiguration.class)
@EnableAutoConfiguration
@EnableSwagger2
public class ClientManagementBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientManagementBackEndApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
