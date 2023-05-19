package com.lcwd.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
    private int id;
    
    @NotEmpty
    @Size(min=4, message = "Username must have min of 4 characters !!")
	private String name;
    
    @Email(message = "Email must be valid !!")
	private String email;
    
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must have min of 4 and max of 10 characters !!")
	private String password;
    
    @NotEmpty
	private String about;

}
