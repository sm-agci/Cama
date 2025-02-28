package cama.otp.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class CamaOtpMockApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamaOtpMockApplication.class, args);
    }
}
