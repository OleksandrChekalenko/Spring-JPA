package com.example.pet.repository;

import com.example.pet.dto.PersonNoteCountDto;
import com.example.pet.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM Person p WHERE p.firstName = :firstName")
        //    List<Person> findAllByFirstName(String firstName);
    List<Person> findAllByFirstName(@Param("firstName") String firstName);

    @Query("SELECT p FROM Person p left join fetch p.notes WHERE p.firstName = :firstName")
        //    List<Person> findAllByFirstName(String firstName);
    List<Person> findAllByFirstNameFetchNotes(@Param("firstName") String firstName);

    <T> List<T> findAllBy(Class<T> type);

    @Query("SELECT new com.example.pet.dto.PersonNoteCountDto(p.firstName, p.lastName, n.size) FROM Person p left join p.notes n")
    List<PersonNoteCountDto> findAllPersonsWithCount();

    @Query("SELECT new com.example.pet.dto.PersonNoteCountDto(p.firstName, p.lastName, n.size) FROM Person p left join p.notes n")
    Page<PersonNoteCountDto> findAllPersonsWithCountPageable(Pageable pageable);
}
