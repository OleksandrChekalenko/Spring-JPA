package com.example.pet.repository;

import com.example.pet.dto.NoteDto;
import com.example.pet.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT new com.example.pet.dto.NoteDto(n.body, n.person.firstName, n.person.lastName) FROM Note n")
    List<NoteDto> getAllDtos();

    List<NoteDto> getAllBy();

    Stream<Note> findAllByBodyStartingWith(String body);
}
