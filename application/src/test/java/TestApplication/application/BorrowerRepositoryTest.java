package TestApplication.application;

import models.Borrower;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import repositories.BorrowerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BorrowerRepositoryTest {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Test
    public void testSaveBorrower() {
        Borrower borrower = new Borrower();
        borrower.setName("Faizan");
        borrower.setEmail("faizan@gmail.com");

        Borrower savedBorrower = borrowerRepository.save(borrower);

        assertThat(savedBorrower.getId()).isNotNull();
        assertThat(savedBorrower.getName()).isEqualTo("Faizan");
        assertThat(savedBorrower.getEmail()).isEqualTo("faizan@gmail.com");
    }
}
