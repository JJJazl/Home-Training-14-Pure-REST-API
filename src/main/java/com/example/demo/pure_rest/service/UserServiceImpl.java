package com.example.demo.pure_rest.service;

import com.example.demo.pure_rest.entity.Note;
import com.example.demo.pure_rest.entity.User;
import com.example.demo.pure_rest.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl implements UserService {
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private final AtomicLong userIdSequence = new AtomicLong(0L);
    private final AtomicLong noteIdSequence = new AtomicLong(0L);

    @Override
    public User save(User user) {
        var generatedId = userIdSequence.incrementAndGet();
        user.setId(generatedId);
        user.getNotes()
                .forEach(note -> note.setId(generateNoteId()));
        userMap.put(generatedId, user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return Optional.ofNullable(userMap.get(id))
                .orElseThrow(() -> new UserNotFoundException(String.format("Can't find user by id: %s", id)));
    }

    @Override
    public Collection<User> findAll() {
        return userMap.values();
    }

    public void removeById(Long id) {
        userMap.remove(findById(id).getId());
    }

    @Override
    public Note addNoteByUserId(Long userId, Note newNote) {
        var user = findById(userId);
        return Optional.ofNullable(newNote)
                .map(note -> saveNewNote(user, note))
                .orElseThrow(() -> new IllegalArgumentException("Note can't be null"));
    }

    @Override
    public List<Note> getUserNotesById(Long userId) {
        return findById(userId).getNotes();
    }

    private Note saveNewNote(User user, Note newNote) {
        newNote.setId(generateNoteId());
        user.addNote(newNote);
        return newNote;
    }

    private Long generateNoteId() {
        return noteIdSequence.incrementAndGet();
    }
}
