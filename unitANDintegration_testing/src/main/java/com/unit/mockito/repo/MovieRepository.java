package com.unit.mockito.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unit.mockito.entity.Movie;
import java.util.List;


/**
 * @author Naveen K Wodeyaar,
 * @Date 12-Aug-2024
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findByMgenre(String mGenre);
}
