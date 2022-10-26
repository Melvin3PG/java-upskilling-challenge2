package net.example.finance.mybank.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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
    private String accountNum;
    private String accountType;
    private float balance;
    private Boolean overdrafts;
    private float overdraftAmt;
}
