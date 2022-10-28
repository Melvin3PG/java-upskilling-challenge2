package net.example.finance.mybank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class AccountDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Add Account Number")
    @NotNull
    //@Column(name = "accountNumber", nullable = false)
    private Long accountNumber;
    private String accountType; // 1:SAVING, 2:CHECKING
    private Float balance;
    private Boolean overdrafts;
    private Float overdraftAmount;
}
