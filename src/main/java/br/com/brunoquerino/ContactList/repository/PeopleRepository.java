/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoquerino.ContactList.repository;

import br.com.brunoquerino.ContactList.entities.People;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author bruno
 */


public interface PeopleRepository  extends JpaRepository<People, Long>{
    People findById(long id);

}
