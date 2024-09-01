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

    public JournalEntry updateJournal(ObjectId id, JournalEntry updatedJournal) {
        JournalEntry existingEntry = journalEntryRepository.findById(id).orElse(null);
        if(existingEntry != null) {
            existingEntry.setContent(updatedJournal.getContent() != null && !updatedJournal.getContent().equals("") ? updatedJournal.getContent() : existingEntry.getContent());
            existingEntry.setTitle(updatedJournal.getTitle() != null && !updatedJournal.getTitle().equals("") ? updatedJournal.getTitle() : existingEntry.getTitle());
        }
        journalEntryRepository.save(existingEntry);
        return existingEntry;
    }
}
