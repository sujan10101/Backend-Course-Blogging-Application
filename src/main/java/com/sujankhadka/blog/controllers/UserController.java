package com.sujankhadka.blog.controllers;

import com.sujankhadka.blog.payloads.ApiResponse;
import com.sujankhadka.blog.payloads.UserDto;
import com.sujankhadka.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

   @Autowired
   private UserService userService;

   @PostMapping("/")
   public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
       UserDto createUserDto = this.userService.createUser(userDto);
       return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
   }

   @PutMapping("/{userId}")
   public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer id){
       UserDto updatedUser = this.userService.updateUser(userDto,id);
       return ResponseEntity.ok(updatedUser);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{userId}")
   public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id){
       this.userService.deleteUser(id);
       return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
   }

   @GetMapping("/")
   public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
   }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

}
