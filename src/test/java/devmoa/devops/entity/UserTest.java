package devmoa.devops.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User(1, "John", "john@example.com");
        assertEquals(1, user.getId());
        assertEquals("John", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();

        user.setId(10);
        assertEquals(10, user.getId());

        user.setUsername("TestUser");
        assertEquals("TestUser", user.getUsername());

        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testSetId() {
        User user = new User();
        user.setId(5);
        assertEquals(5, user.getId());
    }

    @Test
    void testSetUsername() {
        User user = new User();
        user.setUsername("NewUsername");
        assertEquals("NewUsername", user.getUsername());
    }

    @Test
    void testSetEmail() {
        User user = new User();
        user.setEmail("new@email.com");
        assertEquals("new@email.com", user.getEmail());
    }
}
