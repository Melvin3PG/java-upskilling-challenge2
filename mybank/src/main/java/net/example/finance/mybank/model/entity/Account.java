package net.example.finance.mybank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Primary;

import com.example.mvnprg.openapi.model.AccountObject.AccountTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Accounts")
public class Account {
    @Id
    private Long accountNumber;
    private AccountTypeEnum accountType;
    private Float balance;
    private Boolean overdraftAllowed;
    private Float overdraftAmount;
    private Long customerNumber;
}
