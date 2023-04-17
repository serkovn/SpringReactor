package com.example.springreactor.service;

import com.example.springreactor.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Flux<Account> getAll();

    Mono<Account> getOne(Long id);

    Mono<Account> create(Account account);

    Mono<Account> update(Account account);

    Mono<Void> delete(Long accountId);
}
