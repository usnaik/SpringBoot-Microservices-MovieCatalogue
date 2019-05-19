package com.upen.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

//import com.upen.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upen.ratingsdataservice.model.Rating;
import com.upen.ratingsdataservice.model.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

//    @RequestMapping("/movies/{movieId}")
	@RequestMapping("/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
		List <Rating> ratings = Arrays.asList(
			new Rating("1234", 4),				
			new Rating("5678", 3)
		);
    	UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
    	//userRating.initData(userId);
        return userRating;

    }

}