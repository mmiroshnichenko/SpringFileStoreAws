package com.mmyrosh.springfilestoreaws.service;

import com.mmyrosh.springfilestoreaws.model.Role;
import com.mmyrosh.springfilestoreaws.model.Status;
import com.mmyrosh.springfilestoreaws.model.User;
import com.mmyrosh.springfilestoreaws.repository.RoleRepository;
import com.mmyrosh.springfilestoreaws.repository.UserRepository;
import com.mmyrosh.springfilestoreaws.service.impl.UserServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {
    private UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    private RoleRepository mockRoleRepository = Mockito.mock(RoleRepository.class);
    private BCryptPasswordEncoder mockPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    private String encryptedPassword = "$2a$12$DaBywwAaqaVmjApxaoZkq.iJZPnribf/FoBFO3XXPq2Gnepg9zpQS";

    private UserService userService = new UserServiceImpl(mockUserRepository, mockRoleRepository, mockPasswordEncoder);

    private User getSavedUser() {
        User user = getNewUser();
        user.setPassword(encryptedPassword);
        List<Role> roles = new ArrayList<>();
        roles.add(getRole());
        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);

        return user;
    }

    private User getNewUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("michael");
        user.setFirstName("Michael");
        user.setLastName("Mir");
        user.setEmail("test@test.com");
        user.setPassword("test");
        user.setCreated(new Date());
        user.setUpdated(new Date());

        return user;
    }

    private Role getRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        return role;
    }

    @Test
    public void shouldSaveUserTest() {
        User newUser = getNewUser();

        Mockito.when(mockRoleRepository.findByName("ROLE_USER")).thenReturn(getRole());
        Mockito.when(mockPasswordEncoder.encode(newUser.getPassword())).thenReturn(encryptedPassword);
        Mockito.when(mockUserRepository.save(getSavedUser())).thenReturn(getSavedUser());
        User savedMockUser = userService.registerUser(getNewUser());

        assertEquals(savedMockUser, getSavedUser());
    }



}
