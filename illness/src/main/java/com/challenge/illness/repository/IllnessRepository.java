package com.challenge.illness.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.challenge.illness.model.Illness;



public interface IllnessRepository extends MongoRepository<Illness, String>, IllnessRepositoryCustom{

	Illness findByName(String name);
	List<Illness> findAll();
	
	
}
