package ru.kpfu.stud.garipov.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.stud.garipov.model.User;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryUnitTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setName("Ivan");
        user.setPassword("testTEST");
        user.setVerificationCode("1234");

        testEntityManager.persistAndFlush(user);
    }

    @Test
    public void testGetUserByEmail() {
        Optional<User> result = userRepository.getUserByEmail("test@mail.ru");
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testFindAll() {
        List<User> result = userRepository.findAll();
        Assert.assertEquals("Ivan", result.get(0).getName());
    }

    @Test
    public void testFindByVerificationCode() {
        User result = userRepository.findByVerificationCode("1234");
        Assert.assertEquals("Ivan", result.getName());
    }
}
