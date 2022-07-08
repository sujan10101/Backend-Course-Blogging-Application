package com.sujankhadka.blog.services;
import com.sujankhadka.blog.payloads.UserDto;


import java.util.List;

public interface UserService {

	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

}
