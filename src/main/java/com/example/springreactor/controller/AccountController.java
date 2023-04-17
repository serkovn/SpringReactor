package com.example.springreactor.controller;

import com.example.springreactor.model.Account;
import com.example.springreactor.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public Flux<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("{accountId}")
    public Mono<Account> getOne(@PathVariable Long accountId) {
        sleep();
        log.debug("Получение информации о пользователе");
        return accountService.getOne(accountId);
    }

    @PostMapping
    public Mono<Account> create(@RequestBody Account account) {
        log.debug("Создание пользователя");
        return accountService.create(account);
    }

    @PutMapping("{accountId}")
    public Mono<Account> update(@PathVariable Long accountId, @RequestBody Account account) {
        log.debug("Обновление информации о пользователе");
        account.setId(accountId);
        return accountService.update(account);
    }

    @DeleteMapping("{accountId}")
    public Mono<Void> delete(@PathVariable Long accountId) {
        log.debug("Удаление пользователя");
        return accountService.delete(accountId);
    }

    @SneakyThrows
    private void sleep() {
        Thread.sleep(2000);
    }
}
