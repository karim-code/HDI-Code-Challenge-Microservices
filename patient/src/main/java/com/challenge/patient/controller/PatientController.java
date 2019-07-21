package com.challenge.patient.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.patient.model.Illness;
import com.challenge.patient.model.Patient;
import com.challenge.patient.model.PatientDetails;
import com.challenge.patient.repository.PatientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value="Patient Management System", description="Creating/Retrieving patient objects and initialization of a dummy patient collection as well")
@RestController
public class PatientController {

	@Autowired
	private PatientRepository repository;

	@Autowired
	RestTemplate restTemplate;

	
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

	
	@ApiOperation(value = "Create a new patient object", response = ResponseEntity.class)
	@ApiParam(type = "json", required = true, example = "{name: name of the patient, address: address of patient, birthdate: birthdate in form dd.mm.YYYY, gender: gender as M, F or D, insuranceNumber: patient's insurance number, illnesses: String array of illnesses' names}")
	@ApiResponses(value= {
			@ApiResponse(code=409,message="Patient object with same name already exists"),
			@ApiResponse(code=201,message="Patient object successfully created"),
			
	})
	/**
	 * creates a patient object on the mongoDB
	 * 
	 * @param patient       json object representing patient info
	 * @param bindingResult
	 * @param request       request object
	 * @return http status code with the created patient object
	 */
	@RequestMapping(path = "/api/patients/create", method = RequestMethod.POST)
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient, HttpServletRequest request) {

		// check if patient already exists
		Patient patientExists = repository.findByInsuranceNumber(patient.getInsuranceNumber());

		if (patientExists != null) {

			return new ResponseEntity<Patient>(patient, HttpStatus.CONFLICT);

		}

		else {

			// create new patient
			repository.save(new Patient(patient.getName(), patient.getAddress(), patient.getBirthdate(),
					patient.getGender(), patient.getInsuranceNumber(), patient.getIllnesses()));
		}

		// return success response
		return new ResponseEntity<Patient>(patient, HttpStatus.CREATED);

	}

	
	
	@ApiOperation(value = "Get detaild info (including its illnesses) about a patient object using its insurance number", response = ResponseEntity.class)
	@ApiParam(type = "String", required = true, name = "insuranceNumber")
	@ApiResponses(value= {
			@ApiResponse(code=404,message="Patient object not found"),
			@ApiResponse(code=200,message="Patient object found"),
			
	})
	/**
	 * gets patient object including all the information about available illnesses
	 * 
	 * @param insuranceNumber patient's insurance number
	 * @return patient object with all details
	 */
	@RequestMapping(path = "/api/patients/details", method = RequestMethod.GET)
	public ResponseEntity<PatientDetails> findPatientdetailed(@RequestParam String insuranceNumber) {
		// check if patient already exists
		Patient patient = repository.findByInsuranceNumber(insuranceNumber);

		// patient doesn't exist
		if (patient == null) {

			return new ResponseEntity<PatientDetails>(new PatientDetails(), HttpStatus.NOT_FOUND);

		}

		// patient found
		else {

			ArrayList<Illness> illnessObjects = new ArrayList<Illness>();

			// loop through illnesses
			for (String illnessName : patient.getIllnesses()) {
				try {

					HttpHeaders headers = new HttpHeaders();
					headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

					UriComponentsBuilder builder = UriComponentsBuilder
							.fromHttpUrl("http://illness:9090/api/illnesses").queryParam("name", illnessName);

					HttpEntity<?> entity = new HttpEntity<>(headers);

					HttpEntity<Illness> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
							Illness.class);

					illnessObjects.add(response.getBody());
				}

				// illnessObjects.add(response);

				catch (Exception ex) {

				}
			}

			// build response object
			PatientDetails patientDetails = new PatientDetails(patient.getName(), patient.getAddress(),
					patient.getBirthdate(), patient.getGender(), patient.getInsuranceNumber(), illnessObjects);

			// return success response
			return new ResponseEntity<PatientDetails>(patientDetails, HttpStatus.ACCEPTED);

		}

	}

	
	
	@ApiOperation(value = "Get the patient object using its insurance number", response = ResponseEntity.class)
	@ApiParam(type = "String", required = true, name = "insuranceNumber")
	@ApiResponses(value= {
			@ApiResponse(code=404,message="Patient object not found"),
			@ApiResponse(code=200,message="Patient object found"),
			
	})
	/**
	 * gets single patient using the insurance number as a key
	 * @param insuranceNumber key for retrieving the patient
	 * @return patient object
	 */
	@RequestMapping(path = "/api/patients", method = RequestMethod.GET)
	public ResponseEntity<Patient> findPatient(@RequestParam String insuranceNumber) {
		// check if patient already exists
		Patient patient = repository.findByInsuranceNumber(insuranceNumber);

		if (patient == null) {

			return new ResponseEntity<Patient>(patient, HttpStatus.NOT_FOUND);

		}

		// return success response
		return new ResponseEntity<Patient>(patient, HttpStatus.ACCEPTED);

	}
	
	
	
	
	@ApiOperation(value = "Get all saved patient objects", response = ResponseEntity.class)
	@ApiResponses(value= {
			@ApiResponse(code=204,message="Patient collection is empy"),
			@ApiResponse(code=200,message="Patient collection found"),
			
	})
	/**
	 * gets all patients objects from the mongoDB
	 * @return all retrieved patients objects
	 */
	@RequestMapping(path = "/api/patients/all", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> findAllPatients() {

		// loop through the whole collection
		List<Patient> allPatients = repository.findAll();

		if (allPatients.size() == 0) {

			return new ResponseEntity<List<Patient>>(allPatients, HttpStatus.NO_CONTENT);

		}

		// return success response
		return new ResponseEntity<List<Patient>>(allPatients, HttpStatus.ACCEPTED);

	}

	
	
	
	@ApiOperation(value = "Init the patient collection with some dummy data stored in the dummyPatients.json file", response = ResponseEntity.class)
	@ApiResponses(value= {
			@ApiResponse(code=403,message="Patient collection couldn't be initialized"),
			@ApiResponse(code=201,message="Patient collection successfully initialized"),
			
	})
	/**
	 * inits the mongoDB with some dummy patients available on the file dummyPatients.json
	 * @return created patients objects
	 */
	@RequestMapping(path = "/api/patients/init", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> initDummyPatients() {
		List<Patient> patients = null;
		// read json and write to db
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Patient>> typeReference = new TypeReference<List<Patient>>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/templates/dummyPatients.json");
		try {
			patients = mapper.readValue(inputStream, typeReference);
			repository.saveAll(patients);
			System.out.println("Patients Saved!");
		} catch (IOException e) {
			System.out.println("Unable to save patients: " + e.getMessage());

			return new ResponseEntity<List<Patient>>(patients, HttpStatus.FORBIDDEN);

		}

		// return success response
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.CREATED);

	}

}
