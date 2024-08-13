package com.unit.mockito.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen K Wodeyaar,
 * @Date 12-Aug-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TEST_MOVIE")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mId;
	
	private String mName;
	
	private String mgenre;
	
	private LocalDate mReleaseDate;
	
}