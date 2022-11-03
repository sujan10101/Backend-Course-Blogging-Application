package com.sujankhadka.blog.payloads;

import com.sujankhadka.blog.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters!!")
	private String name;

	@Email(message = "Email address is not valid!!")
	@NotEmpty(message = "Email is required!!")
	@Column(unique = true)
	private String email;

	@NotEmpty
	@Size(min = 3, max = 4, message = "Password must be min of 3 chars and max of 10 chars!!")
	private String password;

	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();

}
