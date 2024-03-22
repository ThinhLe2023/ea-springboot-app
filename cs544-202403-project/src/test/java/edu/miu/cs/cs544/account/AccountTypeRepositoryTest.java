package edu.miu.cs.cs544.account;
import edu.miu.cs.cs544.domain.AccountType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.miu.cs.cs544.repository.AccountTypeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Disabled
public class AccountTypeRepositoryTest {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Test
    public void testFindByType() {
        // Given
        String type = "Test Type";
        AccountType accountType = new AccountType();
        accountType.setType(type);
        accountTypeRepository.save(accountType);

        // When
        AccountType foundAccountType = accountTypeRepository.findByType(type);

        // Then
        assertNotNull(foundAccountType);
        assertEquals(type, foundAccountType.getType());
    }
}

