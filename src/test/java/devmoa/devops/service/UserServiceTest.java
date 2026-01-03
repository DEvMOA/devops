package devmoa.devops.service;

import devmoa.devops.entity.User;
import devmoa.devops.repository.UserRepository;
import devmoa.devops.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User(1, "John", "john@example.com");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getUsername());
        assertEquals("john@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User(1, "John", "john@example.com"),
                new User(2, "Jane", "jane@example.com")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        User user = new User(1, "John", "john@example.com");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(99);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(99);
    }

    @Test
    void testUpdateUser_Success() {
        User user = new User(1, "Updated", "updated@example.com");
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(1, user);

        assertNotNull(result);
        assertEquals("Updated", result.getUsername());
        assertEquals(1, result.getId());
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_NotFound() {
        User user = new User(99, "Unknown", "unknown@example.com");
        when(userRepository.existsById(99)).thenReturn(false);

        User result = userService.updateUser(99, user);

        assertNull(result);
        verify(userRepository, times(1)).existsById(99);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}
