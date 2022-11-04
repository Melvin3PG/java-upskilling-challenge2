package net.example.finance.mybank.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class for user domain 
 * @author aline.perez
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable {
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -7182651483878687555L;

	/** User identifier */
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotNull
	/** User name registered */
	private String userName;
	
	/** User's full name*/
	@NotNull
	private String fullName;
	
	/** Password needed for login */
	@JsonIgnore
	@NotNull
	private String password;
	
	/** User's email */
	@Email
	private String email;
	
	/** User's status */
	private Integer status;
	
	
}
