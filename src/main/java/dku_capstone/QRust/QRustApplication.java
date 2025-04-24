package dku_capstone.QRust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class QRustApplication {

    public static void main(String[] args) {
        SpringApplication.run(QRustApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }

}
