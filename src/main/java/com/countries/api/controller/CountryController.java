package com.countries.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.countries.api.exception.ResourceNotFoundException;
import com.countries.api.model.Country;
import com.countries.api.repository.CountryRepository;

@RestController
@RequestMapping("/api/v1")
public class CountryController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@GetMapping("/countries/all")
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}  
	@PostMapping("/countries")
	public Country createCountry(@RequestBody Country country) {
		return countryRepository.save(country);
	}
	
	@GetMapping("/countries/{value}")
	public List<Country> sortBy(@PathVariable(value = "value") String value) {
		return countryRepository.findAll(Sort.by(Sort.Direction.ASC, value));
	}
	
	@PutMapping("/countries/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") Long countyId,
			@RequestBody Country countryE) throws ResourceNotFoundException {
		Country country = countryRepository.findById(countyId)
				.orElseThrow(() -> new ResourceNotFoundException("Country not found for this id :: " + countyId));
		country.setId(countyId);
		country.setName(countryE.getName());
		country.setCapital(countryE.getCapital());
		country.setRegion(countryE.getRegion());
		country.setSub_region(country.getSub_region());
		country.setArea(country.getArea());
		final Country updatedCountry = countryRepository.save(country);
		return ResponseEntity.ok(updatedCountry);
	}
	
	
	@DeleteMapping("/countries/delete/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long countryId)
			throws ResourceNotFoundException {
		Country country = countryRepository.findById(countryId)
				.orElseThrow(() -> new ResourceNotFoundException("country not found for this id :: " + countryId));

		countryRepository.delete(country);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
