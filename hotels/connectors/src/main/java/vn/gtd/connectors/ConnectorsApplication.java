package vn.gtd.connectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConnectorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectorsApplication.class, args);
    }

}

//@RefreshScope
@RestController
class MessageRestController {
    @Value("${message}")
    private String message = "test message";

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    String message() {
        return this.message;
    }
}
