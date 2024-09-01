package com.akhil.journalApp.controller;

import com.akhil.journalApp.entity.JournalEntry;
import com.akhil.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping("/getJournals")
    public List<JournalEntry> getAll() {
        return journalEntryService.getJournals();
    }

    @PostMapping("/addJournal")
    public JournalEntry createEntry(@RequestBody JournalEntry newEntry) {
        newEntry.setDate(LocalDateTime.now());
        return journalEntryService.addJournal(newEntry);
    }

    @GetMapping("/getJournal/{id}")
    public JournalEntry getById(@PathVariable ObjectId id) {
        return journalEntryService.getJournalById(id).orElse(null);
    }

    @DeleteMapping("/deleteJournal/{id}")
    public boolean deleteById(@PathVariable ObjectId id) {
        return journalEntryService.deleteJournalById(id);
    }

    @PutMapping("updateJournal/{id}")
    public JournalEntry updateById(@PathVariable ObjectId id, @RequestBody JournalEntry updateEntry) {
        return journalEntryService.updateJournal(id, updateEntry);
    }

}
