package tacos;

import java.net.URI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RSocketServerTest {

    @Autowired
    private RSocketRequester.Builder requesterBuilder;

    @LocalServerPort
    private int port;

    private RSocketRequester requester;

    @BeforeEach
    public void setup() {
        this.requester = requesterBuilder.websocket(URI.create("ws://localhost:" + port + "/rsocket"));
    }

    @Test
    public void testGreeting() {
        Mono<String> result = this.requester
                                .route("greeting")
                                .data("Hello RSocket!")
                                .retrieveMono(String.class);

        StepVerifier.create(result)
                    .expectNext("Hello back to you!")
                    .verifyComplete();
    }

    @AfterEach
    public void tearDown() {
        if (this.requester != null) {
            this.requester.rsocket().dispose();
        }
    }
}
