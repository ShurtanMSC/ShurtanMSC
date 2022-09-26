package uz.neft;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
//		(exclude = {DataSourceAutoConfiguration.class})
//@EnableScheduling
public class ShurtanMscApplication extends SpringBootServletInitializer {

	@Value("${custom.timeout}")
	private boolean timeout;
	@Value("${custom.request.timeout}")
	private int connectionRequestTimeout;

	@Value("${custom.connect.timeout}")
	private int connectTimeout;

	@Value("${custom.read.timeout}")
	private int readTimeout;
	public static void main(String[] args) {
		SpringApplication.run(ShurtanMscApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		System.out.println(timeout);
		if (timeout){
			HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
			httpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
			httpRequestFactory.setConnectTimeout(connectTimeout);
			httpRequestFactory.setReadTimeout(readTimeout);

			return new RestTemplate(httpRequestFactory);
		}
		return new RestTemplate();

	}
}

