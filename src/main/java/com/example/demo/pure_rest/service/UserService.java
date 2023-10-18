package com.example.demo.pure_rest.service;

import com.example.demo.pure_rest.entity.Note;
import com.example.demo.pure_rest.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User save(User user);

    User findById(Long id);

    void removeById(Long id);

    Note addNoteByUserId(Long userId, Note newNote);

    Collection<User> findAll();

    List<Note> getUserNotesById(Long userId);
}
