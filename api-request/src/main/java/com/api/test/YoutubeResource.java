package com.api.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping(value="/youtube")
public class YoutubeResource {
	@GetMapping(path="/{id}")
	public String getChannel(@PathVariable String id) throws IOException, InterruptedException {
		String url = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet&key=AIzaSyAEACpp5L0nlqwkP4aUCBW7h5j_5eyOUZ8&id=" + id;
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
		HttpClient httpClient = HttpClient.newBuilder().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}
	
	
	
	
	
	
}
