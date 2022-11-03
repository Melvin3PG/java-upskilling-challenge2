package net.example.finance.mybank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.mvnprg.openapi.model.AccountObject.AccountTypeEnum;
import com.example.mvnprg.openapi.model.CustomerObject.CustomerTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Customers")
public class Customer {
    @Id
    private Long customerNumber;
    private CustomerTypeEnum customerType;
    private Boolean active;
    private Boolean atDate;
}
