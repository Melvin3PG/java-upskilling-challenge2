package net.example.finance.mybank.model.entity;
import net.example.finance.mybank.model.enums.AccountType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;


@Entity
@Table(name= "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;
    @NotNull
    @Column(name = "account_number", unique = true)
    private @Getter @Setter String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private @Getter @Setter AccountType accountType;

    @NotNull
    @Column(name = "balance")
    private @Getter @Setter Float balance;

    @Column(name = "overdrafts")
    private @Getter @Setter Boolean overdrafts;

    @NotNull
    @Column(name = "overdraft")
    private @Getter @Setter Float overdraft;

    protected Account() {}

    public Account(String accountNumber,
                   AccountType accountType,
                   Float balance,
                   Boolean overdrafts,
                   Float overdraft){
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.overdrafts = overdrafts;
        this.overdraft = overdraft;
    }
}

