package com.example.springreactor;

import com.example.springreactor.model.Account;
import com.example.springreactor.model.ProfileStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {
    @Autowired
    private WebTestClient webClient;

    @LocalServerPort
    private int randomServerPort;

    @Test
    void getFirstSixAccounts() {
        webClient.get().uri("/accounts?count=6")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void createNewAccount() {
        var newAccount = Account.builder()
                .name("Николай")
                .surname("Иванов")
                .email("nivan@mail.ru")
                .status(ProfileStatus.ACTIVE)
                .build();

        webClient.post().uri("/accounts")
                .body(Mono.just(newAccount), Account.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(newAccount.getName())
                .jsonPath("$.surname").isEqualTo(newAccount.getSurname());
    }

    @Test
    void getFirstDefaultAccounts() {
        webClient.get().uri("/accounts")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Account.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    void parallelRequest() {
        var flux = Flux.fromStream(Stream.of(1L, 2L, 3L))
                .flatMapSequential((index) -> WebClient.create("http://localhost:" + randomServerPort)
                        .get().uri("/accounts/{id}", index)
                        .retrieve()
                        .bodyToMono(Account.class));
        var time = StepVerifier.create(flux)
                .expectNextCount(3).expectComplete().verify();
        assertTrue(time.toMillis() > 2000);
        assertTrue(time.toMillis() < 3000);
    }
}
