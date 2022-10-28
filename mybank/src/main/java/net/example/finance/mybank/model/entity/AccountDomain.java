package net.example.finance.mybank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
//import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotBlank(message = "Please Add Account Number")
    private Long accountNumber;

    private String accountType; // 1:SAVING, 2:CHECKING
    private Float balance;
    private Boolean overdrafts;
    private Float overdraftAmount;
}
