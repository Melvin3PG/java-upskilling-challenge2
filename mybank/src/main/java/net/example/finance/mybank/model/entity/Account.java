package net.example.finance.mybank.model.entity;
import net.example.finance.mybank.model.enums.AccountType;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name= "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 16)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull
    private Float balance;

    private Boolean overdrafts;

    @NotNull
    private Float overdraft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Boolean getOverdrafts() {
        return overdrafts;
    }

    public void setOverdrafts(Boolean overdrafts) {
        this.overdrafts = overdrafts;
    }

    public Float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Float overdraft) {
        this.overdraft = overdraft;
    }
}

