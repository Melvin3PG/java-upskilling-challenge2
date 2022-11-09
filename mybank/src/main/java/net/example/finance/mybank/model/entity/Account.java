package net.example.finance.mybank.model.entity;

import com.example.mvnprg.openapi.model.AccountObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @GeneratedValue(strategy = IDENTITY)
    private @Id Long id; // will be set when persisting
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private AccountType type;
    @Column(nullable = false)
    private float balance;
    @Column(nullable = false)
    private boolean overdraft;
    @Column(nullable = false)
    private float amount;

    public void setTypeAccount(AccountObject.AccountTypeEnum accountType) {
    }

    public enum AccountType {
        SAVING,
        CHECKING;
    }

    public Account(String accountNumber, AccountType accountType, float balance, boolean isOverdraft, float overdraftAmount) {
        this.number = accountNumber;
        this.type = accountType;
        this.balance = balance;
        this.overdraft = isOverdraft;
        this.amount = overdraftAmount;
    }
}
