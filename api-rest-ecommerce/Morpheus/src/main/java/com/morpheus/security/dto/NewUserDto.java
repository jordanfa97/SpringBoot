package com.morpheus.security.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@Email
	private String email;
	
	private Set<String> roles = new HashSet<>();

}
