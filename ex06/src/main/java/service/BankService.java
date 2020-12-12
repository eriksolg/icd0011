package service;

import org.springframework.stereotype.Component;

@Component
public interface BankService {

    void deposit(Integer amount, String toAccount);

    void withdraw(Integer amount, String fromAccount);

}