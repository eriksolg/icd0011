package dao;

import model.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Person person) {
        em.persist(person);
    }

    public List<Person> findAll() {
        return em.createQuery("select p from Person p").getResultList();
    }

    public Person findPersonById(Long id) {
        TypedQuery<Person> personTypedQuery = em.createQuery("select p from Person p where p.id = :id", Person.class);
        personTypedQuery.setParameter("id", id);
        return personTypedQuery.getSingleResult();
    }

    @Transactional
    public void updatePerson(Person person) {
        em.merge(person);
    }
}
