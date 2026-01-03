package devmoa.devops.controller;

import devmoa.devops.entity.User;
import devmoa.devops.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User(1, "John", "john@example.com");
        when(userService.saveUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getUsername());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User(1, "John", "john@example.com"),
                new User(2, "Jane", "jane@example.com")
        );
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Found() {
        User user = new User(1, "John", "john@example.com");
        when(userService.getUserById(1)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getUserById(99)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateUser_Success() {
        User user = new User(1, "Updated", "updated@example.com");
        when(userService.updateUser(1, user)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(1, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getUsername());
    }

    @Test
    void testUpdateUser_NotFound() {
        User user = new User(99, "Unknown", "unknown@example.com");
        when(userService.updateUser(99, user)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(99, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1);

        ResponseEntity<Void> response = userController.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1);
    }
}
