package com.softserve.sprint15;

import com.softserve.sprint15.entity.Marathon;
import com.softserve.sprint15.repository.MarathonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MarathonRepositoryTest {

    @Autowired
    private MarathonRepository marathonRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertNotNull(marathonRepository);
        Assertions.assertNotNull(entityManager);
    }

    @Test
    public void newMarathonTest() {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathonRepository.save(marathon);

        Marathon actual = marathonRepository.findMarathonByTitle("Marathon1");

        Assertions.assertEquals("Marathon1", actual.getTitle());
    }

    @Test
    public void getMarathonTest() {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathonRepository.save(marathon);

        Marathon marathon2 = marathonRepository.findMarathonByTitle("Marathon1");
        Assertions.assertNotNull(marathon);
        Assertions.assertEquals(marathon2.getTitle(), marathon.getTitle());
    }

    @Test
    public void deleteMarathonTest() {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathonRepository.save(marathon);

        marathonRepository.delete(marathon);
        Throwable e = new Throwable();
        try {
            marathonRepository.getOne(marathon.getId());
        } catch (JpaObjectRetrievalFailureException ex) {
            e = ex;
        }
        Assertions.assertEquals(JpaObjectRetrievalFailureException.class, e.getClass());
    }

    @Test
    public void findAllMarathonsTest() {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        marathonRepository.save(marathon);

        Assertions.assertNotNull(marathonRepository.findAll());
    }

    @Test
    public void deleteByMarathonIdTest() {
        Marathon marathon = new Marathon();
        marathon.setTitle("Marathon1");
        Marathon tempMarathon = marathonRepository.save(marathon);

        marathonRepository.deleteById(tempMarathon.getId());

        Throwable e = new Throwable();
        try {
            marathonRepository.getOne(marathon.getId());
        } catch (JpaObjectRetrievalFailureException ex) {
            e = ex;
        }
        Assertions.assertEquals(JpaObjectRetrievalFailureException.class, e.getClass());
    }
}
