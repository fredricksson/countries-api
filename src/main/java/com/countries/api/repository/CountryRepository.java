package com.countries.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countries.api.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long>{

}
