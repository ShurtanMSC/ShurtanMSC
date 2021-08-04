package uz.neft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//		(exclude = {DataSourceAutoConfiguration.class})
//@EnableScheduling
public class ShurtanMscApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShurtanMscApplication.class, args);
	}

}
