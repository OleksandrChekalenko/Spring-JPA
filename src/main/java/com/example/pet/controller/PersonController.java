package com.example.pet.controller;

import com.example.pet.dto.PersonDto;
import com.example.pet.dto.PersonNameDto;
import com.example.pet.dto.PersonNoteCountDto;
import com.example.pet.entity.Person;
import com.example.pet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonRepository personRepository;

    @GetMapping("/all")
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
}
