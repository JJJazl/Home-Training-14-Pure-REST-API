package com.example.demo.pure_rest.controller;

import com.example.demo.pure_rest.entity.Note;
import com.example.demo.pure_rest.entity.User;
import com.example.demo.pure_rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.example.demo.pure_rest.util.WebLinkBuilder.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<EntityModel<User>> create(@RequestBody User user) {
        userService.save(user);
        var userId = user.getId();
        var userModel = EntityModel.of(user)
                .add(buildLinkToCurrentUser(userId))
                .add(buildLinkToAllUsers())
                .add(buildLinkToAllUserNotes(userId));
        var locationURI = buildLocationURI(userId);
        return ResponseEntity
                .created(locationURI)
                .body(userModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getOne(@PathVariable Long id) {
        var user = userService.findById(id);
        var userModel = EntityModel.of(user)
                .add(buildLinkToAllUserNotes(id))
                .add(buildLinkToAddNewNote(id))
                .add(buildLinkToAllUsers())
                .add(buildLinkToRemoveUser(id));
        return ResponseEntity
                .ok()
                .body(userModel);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        var users = userService.findAll();
        users.forEach(user -> user.add(buildLinkToCurrentUser(user.getId())));
        return ResponseEntity
                .ok()
                .body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        userService.removeById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{userId}/notes")
    public ResponseEntity<EntityModel<Note>> addNote(@PathVariable Long userId, @RequestBody Note note) {
        var newNote = userService.addNoteByUserId(userId, note);
        var locationURI = buildLocationURI(newNote.getId());
        var noteModel = EntityModel
                .of(newNote)
                .add(buildLinkToAllUserNotes(userId))
                .add(buildLinkToCurrentUser(userId))
                .add(buildLinkToAllUsers());
        return ResponseEntity
                .created(locationURI)
                .body(noteModel);
    }

    @GetMapping("/{userId}/notes")
    public ResponseEntity<CollectionModel<Note>> getAllNotesByUserId(@PathVariable Long userId) {
        var noteDtos = CollectionModel
                .of(userService.getUserNotesById(userId))
                .add(buildLinkToCurrentUser(userId))
                .add(buildLinkToAllUsers());
        return ResponseEntity
                .ok()
                .body(noteDtos);
    }
}
