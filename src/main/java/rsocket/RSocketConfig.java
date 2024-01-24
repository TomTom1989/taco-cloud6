package rsocket;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import lombok.extern.slf4j.Slf4j;
import java.time.Instant;

@Configuration
@Slf4j
public class RSocketConfig {

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);
            tcp
                .route("alert")
                .data(new Alert(Alert.Level.RED, "TOM", Instant.now()))
                .send()
                .subscribe();

            log.info("Alert sent");
        };
    }
}
