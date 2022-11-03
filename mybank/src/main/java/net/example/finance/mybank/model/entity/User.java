package net.example.finance.mybank.model.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Users")
public class User {
    @Id
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Integer status;
    private String Password;
}
