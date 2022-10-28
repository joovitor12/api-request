package com.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class SearchResponse {
	
	public SearchResponse() {
		// TODO Auto-generated constructor stub
	}
	private String id;
	private String name;
	private String imageUrl;
	private String pageUrl;
}
