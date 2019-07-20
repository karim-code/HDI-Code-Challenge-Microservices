package com.challenge.patient.model;

import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "All details about the Patient (including an array of its illness objects)")
public class PatientDetails {

	public PatientDetails() {
		
	}
	
	
	public PatientDetails(String name, String address, String birthdate, String gender, String insuranceNumber,
			ArrayList<Illness> illnesses) {
		this.name = name;
		this.address = address;
		this.birthdate = birthdate;
		this.gender = gender;
		this.insuranceNumber = insuranceNumber;
		this.illnesses = illnesses;
	}
	
	
	@ApiModelProperty(notes = "The database generated patient ID")
	private String id;

	@ApiModelProperty(notes = "Patient's insurance number (should be unique!)")
	private String insuranceNumber;

	@ApiModelProperty(notes = "Patient's name")
	private String name;

	@ApiModelProperty(notes = "Patient's address")
	private String address;

	@ApiModelProperty(notes = "Patient's birthdate")
	private String birthdate;

	@ApiModelProperty(notes = "Patient's gender (M, F or D)")
	private String gender;

	@ApiModelProperty(notes = "Patient's array of illness objects")
	private ArrayList<Illness> illnesses;

	public ArrayList<Illness> getIllnesses() {
		return illnesses;
	}

	public void setIllnesses(ArrayList<Illness> illnesses) {
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
