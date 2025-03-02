package cama.geofencing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class CamaGeoFencingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamaGeoFencingApplication.class, args);
    }
}
