package cama.otp.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class CamaOtpApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamaOtpApplication.class, args);
    }
}
