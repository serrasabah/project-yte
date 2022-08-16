package yte.app.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
/*
final long period = 1000;
	@Scheduled(fixedDelay = period)
	public void cronJobSch() throws Exception {
		String result = "";
		try {
			URL urlObj = new URL("https://open.spotify.com/");
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
			// Set connection timeout
			con.setConnectTimeout(3000);
			con.connect();

			int code = con.getResponseCode();
			System.out.println(code);
			if (code == 200) {
				result = "On";
			}
		} catch (Exception e) {
			result = "Off";
		}
		System.out.println(result);
	}
	//@Scheduled(fixedDelay = 1000)
*/


}
