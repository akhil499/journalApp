package com.akhil.journalApp.controller;

import com.akhil.journalApp.entity.JournalEntry;
import com.akhil.journalApp.entity.User;
import com.akhil.journalApp.service.JournalEntryService;
import com.akhil.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("/getJournals/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalsOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> journals = user.getJournalEntryList();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @PostMapping("/addJournal/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry newEntry, @PathVariable String userName) {
        try {
            journalEntryService.addJournal(newEntry, userName);
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

    @DeleteMapping("/deleteJournal/{userName}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id, @PathVariable String userName) {
        journalEntryService.deleteJournalById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("updateJournal/{userName}/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id,
                                        @RequestBody JournalEntry updateEntry,
                                        @PathVariable String userName
    ) {
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
