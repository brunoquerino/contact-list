/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoquerino.ContactList.resources;

import br.com.brunoquerino.ContactList.entities.Contact;
import br.com.brunoquerino.ContactList.entities.People;
import br.com.brunoquerino.ContactList.repository.ContactRepository;
import br.com.brunoquerino.ContactList.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ContactResource {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @ApiOperation(value = "Return peoples Contacts list")
    @GetMapping("/peoples/{people_id}/contacts")
    public List<Contact> getContacts(@PathVariable Long people_id) {
        return contactRepository.findByPeopleId(people_id);
    }
    
    @ApiOperation(value = "insert contact from people")
    @PostMapping("/peoples/{people_id}/contacts")
    public Contact addContact(@PathVariable Long people_id,
            @Valid @RequestBody Contact contact) {
        return peopleRepository.findById(people_id)
                .map(people -> {
                    contact.setPeople(people);
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new RuntimeException("People not found with id " + people_id));
    }
    @ApiOperation(value = "update contact from people")
    @PutMapping("/peoples/{peopleId}/contacts/{contactId}")
    public Contact updateContact(@PathVariable Long peopleId,
            @PathVariable Long contactId,
            @Valid @RequestBody Contact contactRequest) throws RuntimeException {
        if (!peopleRepository.existsById(peopleId)) {
            throw new RuntimeException("People not found with id " + peopleId);
        }

        return contactRepository.findById(contactId)
                .map(contact -> {
                    contact.setTypename(contactRequest.getTypename());
                    contact.setValue(contactRequest.getValue());
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new RuntimeException("Contact not found with id " + contactId));
    }
    @ApiOperation(value = "delete contact from people")
    @DeleteMapping("/peoples/{peopleId}/contacts/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable Long peopleId,
            @PathVariable Long contactId) throws RuntimeException {
        if (!peopleRepository.existsById(peopleId)) {
            throw new RuntimeException("People not found with id " + peopleId);
        }
        return contactRepository.findById(contactId)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new RuntimeException("Contact not found with id " + contactId));
    }
}
