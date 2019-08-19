package com.upen.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.upen.moviecatalogservice.models.CatalogItem;
import com.upen.moviecatalogservice.models.Movie;
import com.upen.moviecatalogservice.models.Rating;
import com.upen.moviecatalogservice.models.UserRating;
import com.upen.moviecatalogservice.services.MovieInfo;
import com.upen.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
		
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		// 1. Call RatingData Service : Get all rated movie IDs for a User
		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		// 2. Call Movie Info Service : For each movie id, call movie info service to get the movie details 
		return ratings.getRatings().stream()
			.map (rating -> movieInfo.getCatalogItem(rating))
			.collect(Collectors.toList());
		
	}

}

/*
@Autowired		// Option 2 Using - Reactive Async method
private WebClient.Builder webClientBuilder;
*/

/*
// Option 2 - "Reactive Asynchronous" WebClient.Builder - return object at a later point
Movie movie = webClientBuilder.build()
	.get()
	.uri("http://localhost:8082/movies/" + rating.getMovieId())
	.retrieve()
	.bodyToMono(Movie.class)
	.block();
*/
