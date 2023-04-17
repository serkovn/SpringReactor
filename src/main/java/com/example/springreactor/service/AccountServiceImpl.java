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
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Account> getOne(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    @Transactional
    public Mono<Account> create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Mono<Account> update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long accountId) {
        return accountRepository.deleteById(accountId);
    }
}
