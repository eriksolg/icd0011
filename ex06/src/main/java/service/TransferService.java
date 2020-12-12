package service;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("prototype")
public class TransferService {

    private BankService bankService;

    public TransferService(BankService bankService) {
        this.bankService = bankService;
    }

    public void transfer(Integer amount, String fromAccount, String toAccount) {
        bankService.withdraw(amount, fromAccount);

        bankService.deposit(amount, toAccount);
    }
}