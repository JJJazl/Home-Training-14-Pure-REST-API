package com.example.demo.pure_rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends RepresentationModel<User> {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        this.notes.add(note);
    }
}
