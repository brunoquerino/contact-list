/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoquerino.ContactList.resources;

import br.com.brunoquerino.ContactList.entities.People;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/")
public class PeopleResource {

    @Autowired
    PeopleRepository peopleRepository;

    @ApiOperation(value = "Return people list")
    @GetMapping("/peoples")
    public List<People> getPeoples() {
        return peopleRepository.findAll();
    }

    @ApiOperation(value = "Return unique people")
    @GetMapping("/peoples/{id}")
    public People getUniquePeople(@PathVariable(value = "id") long id) {
        return peopleRepository.findById(id);
    }
    @ApiOperation(value = "add people")
    @PostMapping("/peoples")
    public People createPeople(@Valid @RequestBody People people) {
        return peopleRepository.save(people);
    }
    @ApiOperation(value = "update people")
    @PutMapping("/peoples/{peopleId}")
    public People updatePeople(@PathVariable Long peopleId,
            @Valid @RequestBody People questionRequest) throws RuntimeException {
        return peopleRepository.findById(peopleId)
                .map(people -> {
                    people.setName(questionRequest.getName());
                    //question.setDescription(questionRequest.getDescription());
                    return peopleRepository.save(people);
                }).orElseThrow(() -> new RuntimeException("People not found with id " + peopleId));
    }
    @ApiOperation(value = "delete people")
    @DeleteMapping("/peoples/{peopleId}")
    public ResponseEntity<?> deletePeople(@PathVariable Long peopleId) throws RuntimeException{
        return peopleRepository.findById(peopleId)
                .map(people -> {
                    peopleRepository.delete(people);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new RuntimeException("People not found with id " + peopleId));
    }
}
