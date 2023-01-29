package posmy.interview.boot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import posmy.interview.boot._enum.Role;
import posmy.interview.boot.entity.User;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_Save_ReturnUser(){
        User user = User.builder()
                .username("test1")
                .password("test1")
                .role(Role.LIBRARIAN)
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);
    }

    @Test
    public void UserRepository_FindAll_ReturnMoreThanOneUser(){
        User user1 = User.builder()
                .username("test1")
                .password("test1")
                .role(Role.LIBRARIAN)
                .build();
        User user2 = User.builder()
                .username("test2")
                .password("test2")
                .role(Role.MEMBER)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        Assertions.assertNotNull(userList);
        Assertions.assertTrue(userList.size() >= 2);
    }

    @Test
    public void UserRepository_FindByUsername_ReturnUser(){
        User user1 = User.builder()
                .username("test1")
                .password("test1")
                .role(Role.LIBRARIAN)
                .build();

        userRepository.save(user1);

        Optional<User> savedUser = userRepository.findByUsername("test1");

        Assertions.assertNotNull(savedUser);
    }

    @Test
    public void UserRepository_UpdateUser_ReturnUserNotNull(){
        User user1 = User.builder()
                .username("test1")
                .password("test1")
                .role(Role.LIBRARIAN)
                .build();

        userRepository.save(user1);
        Optional<User> userOptional = userRepository.findByUsername("test1");
        User user = userOptional.get();
        user.setRole(Role.MEMBER);
        User updatedUser = userRepository.save(user);
        Assertions.assertNotNull(updatedUser.getRole());
    }

    @Test
    public void UserRepository_DeleteUser_ReturnUserIsEmpty(){
        User user1 = User.builder()
                .username("test1")
                .password("test1")
                .role(Role.LIBRARIAN)
                .build();

        User savedUser = userRepository.save(user1);
        userRepository.deleteById(savedUser.getId());
        Optional<User> userOptional = userRepository.findByUsername("test1");

        Assertions.assertTrue(userOptional.isEmpty());
    }
}
