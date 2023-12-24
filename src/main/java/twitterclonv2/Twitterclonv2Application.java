package twitterclonv2;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Twitterclonv2Application {
    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public static void main(String[] args) {
        SpringApplication.run(Twitterclonv2Application.class,
                              args);
    }

    /*@Bean
    public CommandLineRunner createPasswordsCommand() {
        return args -> {
            System.out.println("admin clave:");
            System.out.println(passwordEncoder.encode("admin"));
            System.out.println("customer clave:");
            System.out.println(passwordEncoder.encode("customer"));
        };
    }*/

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                   .bearerFormat("JWT")
                                   .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                                                     addList("Bearer Authentication"))
                            .components(new Components().addSecuritySchemes
                                                                ("Bearer Authentication",
                                                                 createAPIKeyScheme()))
                            .info(new Info().title("TwitterClon API"));
    }
}
