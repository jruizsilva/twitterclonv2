package twitterclonv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
            System.out.println("user clave:");
            System.out.println(passwordEncoder.encode("user"));
            System.out.println("user sand:");
            System.out.println(passwordEncoder.encode("sand"));
        };
    }*/
}
