package com.sujankhadka.blog.services.impl;

import com.sujankhadka.blog.config.AppConstants;
import com.sujankhadka.blog.entities.Role;
import com.sujankhadka.blog.entities.User;
import com.sujankhadka.blog.payloads.UserDto;
import com.sujankhadka.blog.repositories.RoleRepository;
import com.sujankhadka.blog.repositories.UserRepository;
import com.sujankhadka.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.sujankhadka.blog.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private ModelMapper modelMapper;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto , User.class);

        //encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //roles
       Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();

       user.getRoles().add(role);

       User newUser = this.userRepository.save(user);

       return this.modelMapper.map(newUser, UserDto.class);


    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user= this.dtoToUser(userDto);
        User savedUser= this.userRepository.save(user);
        return this.userToDto(savedUser);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatesUser= this.userRepository.save(user);
        UserDto userDto1= this.userToDto(updatesUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users= this.userRepository.findAll();
        List<UserDto> userDtos= users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        this.userRepository.delete(user);

    }

    private User dtoToUser(UserDto userDto)
    {
        User user= this.modelMapper.map(userDto, User.class);
//        User user=new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user){
         UserDto userDto= this.modelMapper.map(user, UserDto.class);
//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
