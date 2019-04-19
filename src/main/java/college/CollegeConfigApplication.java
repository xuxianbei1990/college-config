package college;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"college.config", "college"})
@EnableWebMvc
public class CollegeConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeConfigApplication.class, args);
    }
}
