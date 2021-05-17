package com.sellandsign.test.dto;

import com.sellandsign.test.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	@NotBlank
	private String username;
	@NotBlank
	private String password;

	public User toEntity() {
		return new User(username, password, true);
	}
}
