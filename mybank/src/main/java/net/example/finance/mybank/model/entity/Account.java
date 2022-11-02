package net.example.finance.mybank.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Account {
    @Id
    private Long accountNumber;
    private AccountTypeEnum accountType;
    private Float balance;
    private Boolean overdraftAllowed;
    private Float overdraftAmount;
}
