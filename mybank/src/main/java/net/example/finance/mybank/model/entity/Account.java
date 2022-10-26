package net.example.finance.mybank.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account implements Serializable {
    private @Id Long id; // will be set when persisting
    private String accountNumber;
    private String accountType; // set enum
    private float balance;
    private boolean isOverdraft;
    private float overdraftAmount;

    public Account(String accountNumber, String accountType, float balance, boolean isOverdraft, float overdraftAmount) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.isOverdraft = isOverdraft;
        this.overdraftAmount = overdraftAmount;
    }
}
