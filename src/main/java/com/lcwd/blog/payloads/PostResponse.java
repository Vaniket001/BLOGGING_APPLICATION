package com.lcwd.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

	private List<PostDto> contents;
	private int pageNo;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private boolean isLastPage;
}
