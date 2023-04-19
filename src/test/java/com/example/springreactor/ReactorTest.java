package com.example.springreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ReactorTest {
    @Test
    void simpleFlux() {
        var fluxCurrency = Flux.just("RUB", "USD", "EUR");
        StepVerifier.create(fluxCurrency)
                .expectNext("RUB")
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void simpleFluxWithMapAndLog() {
        var fluxCurrency = Flux.just("RUB", "USD", "EUR")
                .map(currency -> currency.charAt(0))
                .log();
        StepVerifier.create(fluxCurrency)
                .expectNext('R')
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void zipFlux() {
        var fluxCurrency = Flux.just("RUB", "USD", "EUR");
        var fluxCurrencyRate = Flux.just(11.85, 0.1452, 0.13257);
        var resultFlux = Flux.zip(fluxCurrency, fluxCurrencyRate)
                .log()
                .map(it -> it.getT1() + " - " + it.getT2());
        StepVerifier.create(resultFlux)
                .expectNext("RUB - 11.85")
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void fluxWithError() {
        var fluxCalc = Flux.just(-1, 0, 1)
                .map(number -> "10 / " + number + " = " + (10 / number)).log();
        StepVerifier.create(fluxCalc)
                .expectNextCount(1)
                .expectErrorMatches(throwable -> throwable instanceof ArithmeticException &&
                        throwable.getMessage().equals("/ by zero"))
                .verify();
    }

    @Test
    void fluxOnErrorReturn() {
        var fluxCalc = Flux.just(-1, 0, 1)
                .map(i -> "10 / " + i + " = " + (10 / i))
                .onErrorReturn(ArithmeticException.class, "Division by 0 not allowed").log();
        StepVerifier.create(fluxCalc)
                .expectNextCount(1)
                .expectNext("Division by 0 not allowed")
                .expectComplete()
                .verify();
    }
}
