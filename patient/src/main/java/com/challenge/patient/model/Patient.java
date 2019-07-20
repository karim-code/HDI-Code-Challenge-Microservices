package com.challenge.patient.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "All details about the Patient")
@Document(collection = "patient")
public class Patient {

	public Patient() {

	}

	public Patient(String name, String address, String birthdate, String gender, String insuranceNumber,
			String[] illnesses) {
		this.name = name;
		this.address = address;
		this.birthdate = birthdate;
		this.gender = gender;
		this.insuranceNumber = insuranceNumber;
		this.illnesses = illnesses;
	}

	@ApiModelProperty(notes = "The database generated patient ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@ApiModelProperty(notes = "Patient's insurance number (should be unique!)")
	@Indexed(unique = true)
	private String insuranceNumber;

	@ApiModelProperty(notes = "Patient's name")
	private String name;

	@ApiModelProperty(notes = "Patient's address")
	private String address;

	@ApiModelProperty(notes = "Patient's birthdate")
	private String birthdate;

	@ApiModelProperty(notes = "Patient's gender (M, F or D)")
	private String gender;

	@ApiModelProperty(notes = "Patient's array of illness names")
	private String[] illnesses;

	public String[] getIllnesses() {
		return illnesses;
	}

	public void setIllnesses(String[] illnesses) {
		this.illnesses = illnesses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
