package com.example.pet.service;

import com.example.pet.dto.NoteDto;
import com.example.pet.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotesService {
    private final NoteRepository noteRepository;

    @Transactional(readOnly = true)
    public List<NoteDto> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(note ->
                        new NoteDto(note.getBody(),
                                note.getPerson().getFirstName(),
                                note.getPerson().getLastName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NoteDto> getAllByBodyStart(String bodyStart) {
        try (var noteStream = noteRepository.findAllByBodyStartingWith(bodyStart)) {
            return noteStream.map(note -> new NoteDto(note.getBody(),
                    note.getPerson().getFirstName(),
                    note.getPerson().getLastName())
            ).toList();
        }
    }
}
