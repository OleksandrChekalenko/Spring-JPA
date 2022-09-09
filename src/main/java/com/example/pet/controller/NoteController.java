package com.example.pet.controller;

import com.example.pet.dto.NoteDto;
import com.example.pet.entity.Note;
import com.example.pet.repository.NoteRepository;
import com.example.pet.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepository noteRepository;
    private final NotesService notesService;

    @GetMapping
    public Page<Note> getAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @GetMapping("/slice")
    public Slice<Note> getAllBySlice(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    /**
     * Do 3 requests to the base:
     * Hibernate: select note0_.id as id1_0_, note0_.body as body2_0_, note0_.person_id as person_i3_0_ from notes note0_
     * Hibernate: select person0_.id as id1_1_0_, person0_.first_name as first_na2_1_0_, person0_.last_name as last_nam3_1_0_ from persons person0_ where person0_.id=?
     * Hibernate: select person0_.id as id1_1_0_, person0_.first_name as first_na2_1_0_, person0_.last_name as last_nam3_1_0_ from persons person0_ where person0_.id=?
     * Hibernate: select person0_.id as id1_1_0_, person0_.first_name as first_na2_1_0_, person0_.last_name as last_nam3_1_0_ from persons person0_ where person0_.id=?
     */
    @GetMapping("/dtoMap")
    public List<NoteDto> getAllDtoMap() {
        return notesService.getAllNotes();
    }

    /**
     * Do 1 requests to the base:
     * Hibernate: select note0_.body as col_0_0_, person1_.first_name as col_1_0_, person1_.last_name as col_2_0_ from notes note0_ cross join persons person1_ where note0_.person_id=person1_.id
     */
    @GetMapping("/dtoQuery")
    public List<NoteDto> getAllDtoQuery() {
        return noteRepository.getAllDtos();
    }

    /**
     * Spring Data very smart. All previous method should be done as this. The best way
     * Do 1 requests to the base:
     * Hibernate: select note0_.body as col_0_0_, person1_.first_name as col_1_0_, person1_.last_name as col_2_0_ from notes note0_ inner join persons person1_ on note0_.person_id=person1_.id
     */
    @GetMapping("/dtoSpringData")
    public List<NoteDto> getAllDtoSpringData() {
        return noteRepository.getAllBy();
    }

    /**
     * If you need to get more than 500 records it will be much better to use the Stream.
     * He will get open Transaction and Stream will get record when he needs.
     * Much better for Java memory than List<>.
     */
    @GetMapping("/notesByBodyStart")
    public List<NoteDto> getAllByBodyStartStream(@RequestParam String bodyStart) {
        return notesService.getAllByBodyStart(bodyStart);
    }
}
