package com.challenge.illness.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.illness.model.Illness;
import com.challenge.illness.repository.IllnessRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value="Illness Management System", description="Creating/Retrieving illness objects and initialization of a dummy illness collection as well")
@RestController
public class IllnessController {

	@Autowired
	private IllnessRepository repository;
	
	
	
	@ApiOperation(value = "Check the health of the API", response = String.class)
	/**
	 * checks the health of the api
	 * 
	 * @return string indicating that service is running
	 */
	@RequestMapping(path = "/api/health", method = GET)
	public String checkHealth() {
		return "Service is running! Response on: " + new Date();
	}
	
	
	
	
	
	
	@ApiOperation(value = "Create a new illness object", response = ResponseEntity.class)
	@ApiParam(type = "json", required = true, example = "{name: name of the illness, description: a short description of th illness, symptoms: String array of imporant symptoms, thrapies: String array of most useful therapies}")
	@ApiResponses(value= {
			@ApiResponse(code=409,message="Illness object with same name already exists"),
			@ApiResponse(code=201,message="Illness object successfully created"),
			
	})
	/**
	 * creates a illness object on the mongoDB
	 * @param illness json object representing the illness
	 * @param request http request
	 * @return http status code with the created illness object
	 */
	@RequestMapping(path = "/api/illnesses/create", method = RequestMethod.POST)
	public ResponseEntity<Illness> createPatient(@RequestBody Illness illness,
			HttpServletRequest request) {

		// check if patient already exists
		Illness illnessExists = repository.findByName(illness.getName());

		if (illnessExists != null) {

			return new ResponseEntity<Illness>(illness, HttpStatus.CONFLICT);

		}

		else {

			// create new patient
			repository.save(new Illness(illness.getName(),illness.getDescription(),illness.getSymptoms(),illness.getTherapies()));
		}

		// return success response
		return new ResponseEntity<Illness>(illness, HttpStatus.CREATED);

	}

	
	
	@ApiOperation(value = "Get a illness object using its name", response = ResponseEntity.class)
	@ApiParam(type = "String", required = true, name = "name")
	@ApiResponses(value= {
			@ApiResponse(code=404,message="Illness object not found"),
			@ApiResponse(code=200,message="Illness object found"),
			
	})
	/**
	 * gets single illness using the name as a key
	 * @param name key for retrieving the illness
	 * @return illness object
	 */
	@RequestMapping(path = "/api/illnesses", method = RequestMethod.GET)
	public ResponseEntity<Illness> findIllness(@RequestParam String name) {
		// check if patient already exists
		Illness illness = repository.findByName(name);

		if (illness == null) {

			return new ResponseEntity<Illness>(illness, HttpStatus.NOT_FOUND);

		}

		// return success response
		return new ResponseEntity<Illness>(illness, HttpStatus.ACCEPTED);

	}

	
	
	
	@ApiOperation(value = "Get all saved illness objects", response = ResponseEntity.class)
	@ApiResponses(value= {
			@ApiResponse(code=204,message="Illness collection is empy"),
			@ApiResponse(code=200,message="Illness collection found"),
			
	})
	/**
	 * gets all illness objects from the mongoDB
	 * @return all retrieved illness objects
	 */
	@RequestMapping(path = "/api/illnesses/all", method = RequestMethod.GET)
	public ResponseEntity<List<Illness>> findAllIllnesses() {

		// loop through the whole collection
		List<Illness> allIllnesses = repository.findAll();

		if (allIllnesses.size() == 0) {

			return new ResponseEntity<List<Illness>>(allIllnesses, HttpStatus.NO_CONTENT);

		}

		// return success response
		return new ResponseEntity<List<Illness>>(allIllnesses, HttpStatus.ACCEPTED);

	}
	
	
	
	@ApiOperation(value = "Init the illness collection with some dummy data stored in the dummyIllnesses.json file", response = ResponseEntity.class)
	@ApiResponses(value= {
			@ApiResponse(code=403,message="Illness collection couldn't be initialized"),
			@ApiResponse(code=201,message="Illness collection successfully initialized"),
			
	})
	/**
	 * inits the mongoDB with some dummy illnesses available on the file dummyIllnesses.json
	 * @return created illnesses objects
	 */
	@RequestMapping(path = "/api/illnesses/init", method = RequestMethod.GET)
	public ResponseEntity<List<Illness>> initDummyIllnesses() {

		List<Illness> illnesses = null;
		// read json and write to db
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Illness>> typeReference = new TypeReference<List<Illness>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/templates/dummyIllnesses.json");
		try {
		    illnesses = mapper.readValue(inputStream, typeReference);
			repository.saveAll(illnesses);
			System.out.println("Illnesses Saved!");
		} catch (IOException e) {
			System.out.println("Unable to save illnesses: " + e.getMessage());
					
			return new ResponseEntity<List<Illness>>(illnesses, HttpStatus.FORBIDDEN);

		}

		// return success response
		return new ResponseEntity<List<Illness>>(illnesses, HttpStatus.CREATED);

	}
}
