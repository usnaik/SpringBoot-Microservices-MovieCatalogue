package com.upen.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.upen.moviecatalogservice.models.CatalogItem;
import com.upen.moviecatalogservice.models.Movie;
import com.upen.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		// Option 1 - Synchronous call - returns object right away.
		// Call movie info service to get the movie details 
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),Movie.class);
		// Put them all together
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", rating.getRating());		
	}
}
