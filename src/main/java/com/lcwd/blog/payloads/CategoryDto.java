package com.lcwd.blog.payloads;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	
	private Integer categoryId;

	@NotEmpty
	@Size(min=4, message = "categoryTitle must have min of 4 characters !!")
	private String categoryTitle;

	@NotEmpty
	@Size(min=10, message = "categoryDescription must have min of 10 characters !!")
	private String categoryDescription;

}
