package com.akhil.journalApp.controller;

import com.akhil.journalApp.entity.JournalEntry;
import com.akhil.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping("/getJournals")
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> journals = journalEntryService.getJournals();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @PostMapping("/addJournal")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry newEntry) {
        try {
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.addJournal(newEntry);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getJournal/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id) {
        Optional<JournalEntry> entry =  journalEntryService.getJournalById(id);
        if(entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteJournal/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
        journalEntryService.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("updateJournal/{id}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry updateEntry) {
            JournalEntry existingEntry = journalEntryService.getJournalById(id).orElse(null);
            if(existingEntry != null) {
                existingEntry.setContent(updateEntry.getContent() != null && !updateEntry.getContent().equals("") ? updateEntry.getContent() : existingEntry.getContent());
                existingEntry.setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("") ? updateEntry.getTitle() : existingEntry.getTitle());
                journalEntryService.updateJournal(existingEntry);
                return new ResponseEntity<>(existingEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

}
