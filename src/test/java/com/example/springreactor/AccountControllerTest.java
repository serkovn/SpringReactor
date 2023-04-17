package com.example.springreactor;

import com.example.springreactor.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.springreactor.model.ProfileStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {
    @Autowired
    WebTestClient webClient;

    @LocalServerPort
    int randomServerPort;

    @Test
    void _0_test_getAccounts() {
        webClient.get().uri("/accounts")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class);

    }

    @Test
    void _1_testAddNewAccount() {
        var newAccount = createAccount();

        webClient.post().uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(newAccount), Account.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo(newAccount.getFirstName())
                .jsonPath("$.lastName").isEqualTo(newAccount.getLastName());
    }

    @Test
    void _2_test() {
        webClient.get().uri("/accounts")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Account.class)
                .getResponseBody()
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    void _3_test() {
        Instant start = Instant.now();
        var accounts = Stream.of(1, 2, 3, 4, 5)
                .map(i -> WebClient.create("http://localhost:" + randomServerPort)
                        .get().uri("/accounts/{id}", i).header(HttpHeaders.ACCEPT, "application/json")
                        .retrieve()
                        .bodyToMono(Account.class))
                .collect(Collectors.toList());
        Mono.when(accounts).block();
        assertEquals(Boolean.TRUE, Duration.between(start, Instant.now()).toMillis() < 3000);
    }


    private Account createAccount() {
        Account account = new Account();
        account.setFirstName("some name");
        account.setLastName("some name");
        account.setLocale("ENGLISH");
        account.setStatus(ACTIVE);
        return account;
    }
}
