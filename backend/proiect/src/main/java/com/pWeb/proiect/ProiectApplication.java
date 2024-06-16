package com.pWeb.proiect;

import com.pWeb.proiect.DataModel.Image;
import com.pWeb.proiect.Services.JmsPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
//@EnableJpaRepositories("com/pWeb/proiect/Infrastructure/Data/Repositories")
public class ProiectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProiectApplication.class, args);

		JmsPublisher j4 = new JmsPublisher();
		Image image = new Image();
		image.setImage("adv");
		image.setEncrypt(true);
		image.setKey("sdv");
		j4.publishImage(image);
	}

//	@Bean
//	public Docket productApi() {
////		return new Docket(DocumentationType.SWAGGER_2).select()
////				.apis(RequestHandlerSelectors.basePackage("com.pWeb.proiect.Controllers")).build();
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any())
//				.build();
//	}

}
