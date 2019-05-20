package com.upen.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.upen.moviecatalogservice.models.CatalogItem;
import com.upen.moviecatalogservice.models.Movie;
import com.upen.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired		// Option 1 - Using "Rest Template" Synchronous Calls
	private RestTemplate restTemplate;
		
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//Get all rated movie IDs
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
		
		// For each movie id, call movie info service to get the movie details 
		return ratings.getUserRating().stream()
			.map (rating -> {
				
				// Option 1 - Synchronous call - returns object right away.
				// Call movie info service to get the movie details 
				Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),Movie.class);
				
				// Put them all together
				return new CatalogItem(movie.getName(), "Transformer - Test Description", rating.getRating());			
			})
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
