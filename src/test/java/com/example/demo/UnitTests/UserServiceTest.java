package com.example.demo.UnitTests;

import com.example.demo.models.dtos.UserDTO;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.ExerciseRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserMapper;
import com.example.demo.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Collections;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setPassword("password");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");
        userDTO.setPassword("password");
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<User> userList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertEquals(1, result.size());
        assertEquals(userDTO, result.get(0));
    }

    @Test
    public void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.getUserById(1L);

        // Assert
        assertEquals(userDTO, result);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        when(exerciseRepository.findAllById(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserDTO result = userService.createUser(userDTO);

        // Assert
        assertEquals(userDTO, result);
    }

    @Test
    public void testAssociateExercises() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(exerciseRepository.findAllById(any())).thenReturn(Collections.emptyList());
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.associateExercises(1L, Collections.singletonList(2L));

        // Assert
        assertEquals(userDTO, result);
    }
    @Test
    public void testUpdateUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserDTO result = userService.updateUser(1L, userDTO);

        // Assert
        assertEquals(userDTO, result);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserByIdNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        userService.getUserById(1L);

        // Assert
        // Expects a RuntimeException to be thrown
    }

    @Test
    public void testCreateUserWithExercises() {
        // Arrange
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        Set<Long> exerciseIds = new HashSet<>(Arrays.asList(1L, 2L));
        when(exerciseRepository.findAllById(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserDTO result = userService.createUser(userDTO);

        // Assert
        assertEquals(userDTO, result);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateUserWithInvalidExerciseId() {
        // Arrange
        when(userMapper.toEntity(userDTO)).thenReturn(user);

        // Mocking findAllById to throw an exception when called
        doThrow(new RuntimeException("Invalid exercise ID"))
                .when(exerciseRepository).findAllById(anyList());

        // Simulate an invalid exercise ID
        userDTO.setExerciseIds(Collections.singleton(-1L));

        // Act
        userService.createUser(userDTO);

        // Assert
        // Expects a RuntimeException to be thrown
    }


}
