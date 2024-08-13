package com.unit.mockito.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.unit.mockito.entity.Movie;

@DataJpaTest
public class MovieRepositoryTest{
	
	private static final Logger log = LoggerFactory.getLogger(MovieRepositoryTest.class);
	
	@Autowired
	private MovieRepository movieRepository;
	
	private Movie movie;
	private Movie movie1;
	private Movie movie2;
	private Movie movie3;
	
	@BeforeEach
	void init() {
		movie = new Movie();
		movie.setMName("VIP");
		movie.setMgenre("Sentiment");
		movie.setMReleaseDate(LocalDate.of(2020, 05, 06));
		
		movie1 = new Movie();
		movie1.setMName("VIP2");
		movie1.setMgenre("Dramedy");
		movie1.setMReleaseDate(LocalDate.of(202, 05, 06));
		
		movie2 = new Movie();
		movie2.setMName("VIP3");
		movie2.setMgenre("Action");
		movie2.setMReleaseDate(LocalDate.of(2020, 05, 06));
		
		movie3 = new Movie();
		movie3.setMName("VIP4");
		movie3.setMgenre("Dramedy");
		movie3.setMReleaseDate(LocalDate.of(202, 05, 06));
		
	}
	
	@Test
	@DisplayName("save(),")
	 void save() {
		//Arrange,
				
		// Act,
		Movie mv = movieRepository.save(movie);
		
		// Assert,
		assertNotNull(mv);
		assertThat(mv.getMId()).isNotEqualTo(null);;
	}
	
	@Test
	@DisplayName("findAll(),")
	 void getMovies() {
		// Act,
		movieRepository.save(movie);
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		List<Movie> movieList = movieRepository.findAll();
		System.out.println("List found:"+movieList);
		log.warn(String.valueOf(movieList.size()));
		// Assert,
		assertNotNull(movieList);
//		assertThat(movieList.size()).isNotEqualTo(0);
//		assertEquals(4, movieList.size());
	}
	
	@Test
	@DisplayName("/getById/?")
	void getMovieById() {
		
		movieRepository.save(movie);
		Movie mv = movieRepository.findById(movie.getMId()).get();
		assertNotNull(mv);
		assertEquals("VIP", mv.getMName());
	}

	@Test
	@DisplayName("Update")
	void updateMovie() {

		movieRepository.save(movie);
		
		Optional<Movie> findById = movieRepository.findById(movie.getMId());
						findById.get().setMgenre("ACTION");
						findById.get().setMName("VIP-4");
						movieRepository.save(findById.get());
				
		assertEquals("ACTION", findById.get().getMgenre());
	}
	
	@Test
	@DisplayName("deleteByid")
	void delete() {
		movieRepository.save(movie);
		
		movieRepository.save(movie1);
		
		movieRepository.deleteById(movie1.getMId());
		List<Movie> findAll = movieRepository.findAll();
		assertEquals(1,findAll.size());

	}
	
	@Test
	@DisplayName("/findByMGenre()")
	void findByMGenre() {

		movieRepository.save(movie);
		
		movieRepository.save(movie1);
		
		movieRepository.save(movie2);
		
		movieRepository.save(movie3);
		
		List<Movie> findByMGenre = movieRepository.findByMgenre("Dramedy");
		assertNotNull(findByMGenre);
		
		assertThat(findByMGenre.size()).isEqualTo(2);
		assertEquals(2,findByMGenre.size());
	}

}
