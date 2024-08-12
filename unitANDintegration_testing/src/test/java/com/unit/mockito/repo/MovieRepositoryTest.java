package com.unit.mockito.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.unit.mockito.entity.Movie;

@DataJpaTest
public class MovieRepositoryTest{
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Test
	@DisplayName("save(),")
	 void save() {
		//Arrange,
		Movie movie = new Movie();
				movie.setMName("VIP");
				movie.setMGenre("Dramedy");
				movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
				
		// Act,
		Movie mv = movieRepository.save(movie);
		
		// Assert,
		assertNotNull(mv);
		assertThat(mv.getMId()).isNotEqualTo(null);;
	}
	
	@Test
	@DisplayName("findAll(),")
	 void getMovies() {
		//Arrange,
		Movie movie = new Movie();
				movie.setMName("VIP");
				movie.setMGenre("Dramedy");
				movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
				
		Movie movie1 = new Movie();
		movie1.setMName("VIP2");
		movie1.setMGenre("Dramedy");
		movie1.setMReleaseDate(LocalDate.of(2022, 05, 06));
				
		// Act,
		List<Movie> movieList = movieRepository.findAll();
		
		// Assert,
		assertNotNull(movieList);
		assertThat(movieList.size()).isNotEqualTo(null);
		assertEquals(2, movieList.size());
	}
	
	@Test
	@DisplayName("/getById/?")
	void getMovieById() {
	//Arrange,
		Movie movie = new Movie();
				movie.setMName("VIP");
				movie.setMGenre("Dramedy");
				movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
				movieRepository.save(movie);
		
		Movie movie2 = movieRepository.findById(movie.getMId()).get();
		assertNotNull(movie2);
		assertEquals("VIP", movie2.getMName());
	}

	@Test
	@DisplayName("Update")
	void updateMovie() {
		Movie movie = new Movie();
		movie.setMName("VIP");
		movie.setMGenre("Dramedy");
		movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
		movieRepository.save(movie);
		
		Movie movie2 = movieRepository.findById(movie.getMId()).get();
				movie2.setMName("VIP");
				movie2.setMGenre("Sentiment");
				movie2.setMReleaseDate(LocalDate.of(2019, 04, 05));
				movieRepository.save(movie2);
				
		assertEquals("Sentiment", movie2.getMGenre());
	}
	
	@Test
	@DisplayName("deleteByid")
	void delete() {
		Movie movie = new Movie();
		movie.setMName("VIP");
		movie.setMGenre("Sentiment");
		movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
		movieRepository.save(movie);
		
		Movie movie1 = new Movie();
		movie1.setMName("VIP2");
		movie1.setMGenre("Dramedy");
		movie1.setMReleaseDate(LocalDate.of(202, 05, 06));
		movieRepository.save(movie1);
		
		movieRepository.deleteById(movie1.getMId());
		Optional<Movie> mv= movieRepository.findById(movie1.getMId());
		assertNotNull(mv.isPresent());
		assertThat(mv).isEqualTo(null);
//		assertEquals(1, mv.getMId()>0);;
	}
	
	@Test
	@DisplayName("/findByMGenre()")
	void findByMGenre() {
		Movie movie = new Movie();
		movie.setMName("VIP");
		movie.setMGenre("Sentiment");
		movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
		movieRepository.save(movie);
		
		Movie movie1 = new Movie();
		movie1.setMName("VIP2");
		movie1.setMGenre("Dramedy");
		movie1.setMReleaseDate(LocalDate.of(202, 05, 06));
		movieRepository.save(movie1);
		
		Movie movie2 = new Movie();
		movie2.setMName("VIP3");
		movie2.setMGenre("Action");
		movie2.setMReleaseDate(LocalDate.of(2020, 05, 06));
		movieRepository.save(movie2);
		
		Movie movie3 = new Movie();
		movie3.setMName("VIP4");
		movie3.setMGenre("Dramedy");
		movie3.setMReleaseDate(LocalDate.of(202, 05, 06));
		movieRepository.save(movie3);
		
		List<Movie> findByMGenre = movieRepository.findByMGenre("Dramedy");
		assertNotNull(findByMGenre);
		
		assertThat(findByMGenre.size()).isEqualTo(2);
		assertEquals(2,findByMGenre.size());
	}

}
