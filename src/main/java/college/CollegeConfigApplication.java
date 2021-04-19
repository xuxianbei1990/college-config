package college;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"college.config", "college"})
public class CollegeConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegeConfigApplication.class, args);
    }
}
