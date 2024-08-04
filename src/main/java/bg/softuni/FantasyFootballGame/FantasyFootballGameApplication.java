package bg.softuni.FantasyFootballGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FantasyFootballGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyFootballGameApplication.class, args);
	}

}
