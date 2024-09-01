package com.akhil.journalApp.controller;

import com.akhil.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntry = new HashMap<>();

    @GetMapping("/")
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntry.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry newEntry) {
        journalEntry.put(newEntry.getId(), newEntry);
        return true;
    }

    @GetMapping("/getJournal/{id}")
    public JournalEntry getById(@PathVariable Long id) {
        return journalEntry.get(id);
    }

    @DeleteMapping("/removeJournal/{id}")
    public boolean deleteById(@PathVariable Long id) {
        journalEntry.remove(id);
        return true;
    }

    @PutMapping("update/{id}")
    public JournalEntry updateById(@PathVariable Long id, @RequestBody JournalEntry updateEntry) {
        return journalEntry.put(id, updateEntry);
    }

}
