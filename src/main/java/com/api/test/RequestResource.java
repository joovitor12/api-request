package com.api.test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.models.SearchResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestResource {
	
	@GetMapping(path="/{channel}/{username}")
	// localhost:8080/instagram/jv__machado
	public SearchResponse getPage(@PathVariable String channel, @PathVariable String username) throws IOException, InterruptedException {
		SearchResponse response = new SearchResponse();
		
		
		if(channel.equalsIgnoreCase("instagram")) {
			
			
			String url = "https://www.instagram.com/" + username + "/?__a=1&__d=dis";
			HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
			HttpClient httpClient = HttpClient.newBuilder().build();
			HttpResponse<String> responseJson = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(responseJson.body());
			
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonResponse = objMapper.readValue(responseJson.body(), JsonNode.class);
			
			String id = jsonResponse.get("graphql").get("user").get("id").asText();
			String imageUrl = jsonResponse.get("graphql").get("user").get("profile_pic_url").asText();
			String profilePageUrl = "www.instagram.com/" + username ;
			response = new SearchResponse(id, username,imageUrl, profilePageUrl );			
			
					
			return response;
			
		} else if(channel.equalsIgnoreCase("youtube")) {
			
			String url = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet&key=AIzaSyAEACpp5L0nlqwkP4aUCBW7h5j_5eyOUZ8&id=" + username;
			HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
			HttpClient httpClient = HttpClient.newBuilder().build();
			HttpResponse<String> responseJson = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			ObjectMapper obj = new ObjectMapper();
		    JsonNode jsonResponse = obj.readValue(responseJson.body(), JsonNode.class);
		    
		    String channelUsername = jsonResponse.get("items").get(0).get("snippet").get("localized").get("title").textValue();
		    String imageUrl = jsonResponse.get("items").get(0).get("snippet").get("thumbnails").get("default").get("url").textValue();
		    String profilePageUrl = "www.youtube.com/channel/" + username ;
		    response = new SearchResponse(username, channelUsername , imageUrl, profilePageUrl );

			
			return response;
			
		} else if(channel.equalsIgnoreCase("twitter")) {
			HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.twitter.com/1.1/users/show.json?screen_name=" + username))
                    .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAMFVhgEAAAAAuEfzWvgeHMGx%2BwWJlhFexSlRZIM%3D3p3H2qiKMoool6th16BhsufTuH4xBFUaVaZcQNs82we28RQh13")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpResponse<String> responseJson = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            ObjectMapper objMapper = new ObjectMapper();
            JsonNode jsonResponse = objMapper.readValue(responseJson.body(), JsonNode.class);
            
            String id = jsonResponse.get("id_str").textValue();
            String channelUsername = jsonResponse.get("profile_image_url").textValue();
            String profilePageUrl = "www.twitter.com/" + username;
            response = new SearchResponse(id, username, channelUsername , profilePageUrl );
            return response;
            
		} else if(channel.equalsIgnoreCase("facebook")) {
			String acessToken ="EAAKdB6xsE3sBAECVxNjdyGCebesmbZBS0ENhXJft5i5WbVcQLcCxCfSrY97JkTMoML8vRKcNxOiTFZBCc7Jpa6wZC7vRdvTHxvU8NZA2ZCF78Uc4La6xA7r4ylM7H3mOfjiiXZCgberdswLwLcq2SUWy8LlHcZAJcwD56CZC0Sh9pizMwYNUQqtOhZB5yZAsXUAWoZD";
			HttpRequest request = HttpRequest.newBuilder()
		            .GET()
		                .uri(URI.create("https://graph.facebook.com/v15.0/"+username+"?fields=%20id%2Cname%2Cpicture&access_token=" + acessToken
		                    ))
		            .build();
		    HttpClient httpClient = HttpClient.newBuilder().build();
		    HttpResponse<String> responseJson = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		    
		    ObjectMapper objMapper = new ObjectMapper();
		    JsonNode jsonResponse = objMapper.readValue(responseJson.body(), JsonNode.class);
		    response = new SearchResponse(jsonResponse.get("id").textValue(),
		    		jsonResponse.get("name").textValue()
		    		, jsonResponse.get("picture").get("data").get("url").textValue()
		    		, "www.web.facebook.com/" + username );
		    
		    return response;
		}
		return response;
	}
	
}
