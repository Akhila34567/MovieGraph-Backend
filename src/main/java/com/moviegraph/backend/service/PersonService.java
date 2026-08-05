package com.moviegraph.backend.service;

import com.moviegraph.backend.dto.PersonRequest;
import com.moviegraph.backend.dto.PersonResponse;
import com.moviegraph.backend.exception.ResourceNotFoundException;
import com.moviegraph.backend.model.Person;
import com.moviegraph.backend.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Create Person
    public PersonResponse createPerson(PersonRequest request) {

        Person person = new Person(
                null,
                request.getName(),
                request.getBirthYear(),
                request.getNationality(),
                request.getRole()
        );

        Person savedPerson = personRepository.create(person);

        return convertToResponse(savedPerson);
    }

    // Get All Persons
    public List<PersonResponse> getAllPersons() {

        return personRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get Person By ID
    public PersonResponse getPersonById(String personId) {

        Person person = personRepository.findById(personId);

        if (person == null) {
            throw new ResourceNotFoundException(
                    "Person not found with ID: " + personId
            );
        }

        return convertToResponse(person);
    }

    // Update Person
    public PersonResponse updatePerson(String personId, PersonRequest request) {

        Person person = new Person(
                personId,
                request.getName(),
                request.getBirthYear(),
                request.getNationality(),
                request.getRole()
        );

        Person updatedPerson = personRepository.update(personId, person);

        if (updatedPerson == null) {
            throw new ResourceNotFoundException(
                    "Person not found with ID: " + personId
            );
        }

        return convertToResponse(updatedPerson);
    }

    // Delete Person
    public void deletePerson(String personId) {

        boolean deleted = personRepository.delete(personId);

        if (!deleted) {
            throw new ResourceNotFoundException(
                    "Person not found with ID: " + personId
            );
        }
    }

    // Convert Entity to DTO
    private PersonResponse convertToResponse(Person person) {

        return new PersonResponse(
                person.getPersonId(),
                person.getName(),
                person.getBirthYear(),
                person.getNationality(),
                person.getRole()
        );
    }
}