package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    public Account matchAccount(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    public Account matchAccountId(Integer accountId){
        return accountRepository.findByAccountId(accountId);
    }

}
