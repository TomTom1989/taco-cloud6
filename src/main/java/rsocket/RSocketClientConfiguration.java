package rsocket;

import java.net.URI;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;



@Configuration
public class RSocketClientConfiguration {

 @Bean
 public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
     return args -> {
         Mono<String> response = requesterBuilder
             .websocket(URI.create("ws://localhost:7000/rsocket"))
             .route("greeting")
             .data("World")
             .retrieveMono(String.class)
             .doOnSubscribe(subscription -> System.out.println("Attempting to connect..."))
             .doOnSuccess(s -> System.out.println("Received: " + s));

         response.subscribe();
     };
 }
}

