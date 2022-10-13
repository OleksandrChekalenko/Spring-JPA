package com.example.pet.controller;

import com.example.pet.dto.PersonDto;
import com.example.pet.dto.PersonNameDto;
import com.example.pet.dto.PersonNoteCountDto;
import com.example.pet.entity.Person;
import com.example.pet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

  private static List<Person> PERSONS = Stream.of(
      new Person("John", "Johnson"),
      new Person("Peta", "Squirl"),
      new Person("Alex", "Odinson")
  ).collect(Collectors.toList());

  static {
    PERSONS.forEach(person -> person.setId((long) PERSONS.indexOf(person)));
  }

  private final PersonRepository personRepository;

  @GetMapping("/all")
  @PreAuthorize("hasAnyAuthority('permission:read')")

  public List<Person> getAllPersons() {
    return personRepository.findAll();
  }

  @GetMapping
  public List<Person> getByFirstName(@RequestParam String firstName) {
    return personRepository.findAllByFirstName(firstName);
  }

  @GetMapping("/notes")
  public List<Person> getByFirstNameFetchNotes(@RequestParam String firstName) {
    return personRepository.findAllByFirstNameFetchNotes(firstName);
  }

  @GetMapping("/personsDto")
  public List<PersonDto> getAllPersonsDto() {
    return personRepository.findAllBy(PersonDto.class);
  }

  @GetMapping("/personsNameDto")
  public List<PersonNameDto> getAllPersonsNameDto() {
    return personRepository.findAllBy(PersonNameDto.class);
  }

  @GetMapping("/personsWithNoteCountDto")
  public List<PersonNoteCountDto> getAllPersonsWithNoteCountDto() {
    return personRepository.findAllPersonsWithCount();
  }

  @GetMapping("/personsWithNoteCountDtoPageable")
  public Page<PersonNoteCountDto> getAllPersonsWithNoteCountPageableDto(Pageable pageable) {
    return personRepository.findAllPersonsWithCountPageable(pageable);
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('permission:write')")
  public PersonDto createPerson(@RequestBody PersonDto personDto) {
    PERSONS.add(new Person(personDto.firstName(), personDto.lastName()));
    return personDto;
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('permission:write')")

  public HttpStatus deleteById(@PathVariable Long id) {
    log.warn("Trying to delete user with id {}", id);
    return PERSONS.removeIf(person -> person.getId().equals(id)) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
  }
}
