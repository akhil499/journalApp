package com.akhil.journalApp.service;

import com.akhil.journalApp.entity.JournalEntry;
import com.akhil.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry addJournal(JournalEntry newJournal) {
        return journalEntryRepository.save(newJournal);
    }

    public List<JournalEntry> getJournals() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteJournalById(ObjectId id) {
        journalEntryRepository.deleteById(id);
        return true;
    }

    public JournalEntry updateJournal(JournalEntry updatedJournal) {

        journalEntryRepository.save(updatedJournal);
        return updatedJournal;
    }
}
