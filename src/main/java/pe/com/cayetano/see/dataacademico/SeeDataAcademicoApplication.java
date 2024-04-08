package pe.com.cayetano.see.dataacademico;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition
/*@EnableDiscoveryClient*/
public class SeeDataAcademicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeeDataAcademicoApplication.class, args);
	}

}
