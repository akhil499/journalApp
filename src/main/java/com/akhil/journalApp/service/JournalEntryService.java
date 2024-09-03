package com.akhil.journalApp.service;

import com.akhil.journalApp.entity.JournalEntry;
import com.akhil.journalApp.entity.User;
import com.akhil.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void addJournal(JournalEntry newJournal, String userName) {
        newJournal.setDate(LocalDateTime.now());
        User user = userService.findByUserName(userName);
        JournalEntry saved =  journalEntryRepository.save(newJournal);
        user.getJournalEntryList().add(saved);
        userService.addUser(user);
    }

    public List<JournalEntry> getJournals() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntryList().removeIf(x -> x.getId().equals(id));
        userService.addUser(user);
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateJournal(JournalEntry updatedJournal) {

        journalEntryRepository.save(updatedJournal);
        return updatedJournal;
    }
}
