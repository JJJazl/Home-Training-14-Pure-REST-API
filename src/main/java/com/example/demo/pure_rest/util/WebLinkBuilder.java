package com.example.demo.pure_rest.util;

import com.example.demo.pure_rest.controller.UserController;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class WebLinkBuilder {
    public static URI buildLocationURI(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public static Link buildLinkToCurrentUser(Long userId) {
        return linkTo(methodOn(UserController.class).getOne(userId)).withRel("getCurrentUser");
    }

    public static Link buildLinkToAllUsers() {
        return linkTo(methodOn(UserController.class).getAll()).withRel("getAllUsers");
    }

    public static Link buildLinkToAllUserNotes(Long userId) {
        return linkTo(methodOn(UserController.class).getAllNotesByUserId(userId)).withRel("getAllUserNotes");
    }

    public static Link buildLinkToAddNewNote(Long userId) {
        return linkTo(methodOn(UserController.class).addNote(userId, null)).withRel("addNewNote");
    }

    public static Link buildLinkToRemoveUser(Long userId) {
        return linkTo(methodOn(UserController.class).remove(userId)).withRel("removeUser");
    }
}
