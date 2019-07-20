package com.challenge.patient.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about the Illness")
public class Illness {

	public Illness() {

	}

	

	@ApiModelProperty(notes = "The database generated illness ID")
	private String id;

	@ApiModelProperty(notes = "Illness name (should be unique!)")
	private String name;

	@ApiModelProperty(notes = "Illness short description")
	private String description;

	@ApiModelProperty(notes = "An array containing the illness's symptoms")
	private String[] symptoms;

	@ApiModelProperty(notes = "An array containing the illness's possible therapies")
	private String[] therapies;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String[] symptoms) {
		this.symptoms = symptoms;
	}

	public String[] getTherapies() {
		return therapies;
	}

	public void setTherapies(String[] therapies) {
		this.therapies = therapies;
	}

}
