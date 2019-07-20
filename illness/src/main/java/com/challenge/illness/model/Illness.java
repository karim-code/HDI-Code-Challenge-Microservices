package com.challenge.illness.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "All details about the Illness")
@Document(collection = "illness")
public class Illness {

	public Illness() {

	}

	public Illness(String name, String description, String[] symptoms, String[] therapies) {

		this.name = name;
		this.description = description;
		this.symptoms = symptoms;
		this.therapies = therapies;
	}

	
	@ApiModelProperty(notes = "The database generated illness ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	
	@ApiModelProperty(notes = "Illness name (should be unique!)")
	@Indexed(unique = true)
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
