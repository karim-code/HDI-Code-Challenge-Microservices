package com.challenge.patient.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.challenge.patient.model.Patient;

public interface PatientRepository extends MongoRepository<Patient, String>, PatientRepositoryCustom{
	
	Patient findByInsuranceNumber(String insuranceNumber);
	Patient findByNameAndBirthdate(String name, String birthdate);
	List<Patient> findAll();

}
