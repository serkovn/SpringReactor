package com.example.springreactor.service;

import com.example.springreactor.model.Account;
import com.example.springreactor.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Flux<Account> getFirsCount(int count) {
        return accountRepository.findAll().take(count);
    }

    @Transactional(readOnly = true)
    public Mono<Account> getOne(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public Mono<Account> create(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public Mono<Account> update(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public Mono<Void> delete(Long accountId) {
        return accountRepository.deleteById(accountId);
    }
}
