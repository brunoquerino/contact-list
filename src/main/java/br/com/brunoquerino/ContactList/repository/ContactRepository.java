package br.com.brunoquerino.ContactList.repository;
import br.com.brunoquerino.ContactList.entities.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author bruno
 */
public interface ContactRepository extends JpaRepository<Contact, Long>{
    List<Contact> findByPeopleId(long people_id);
}
