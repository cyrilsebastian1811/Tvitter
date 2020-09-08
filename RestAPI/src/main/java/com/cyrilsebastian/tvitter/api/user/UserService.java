package com.cyrilsebastian.tvitter.api.user;

import com.cyrilsebastian.tvitter.api.role.Role;
import com.cyrilsebastian.tvitter.api.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        Set<Role> roles = user.getRoles().stream().map(role -> {
            if(roleRepository.existsByName(role.getName())) return roleRepository.findByName(role.getName()).get();
            else {
                roleRepository.save(role);
                return role;
            }
        }).collect(Collectors.toSet());
        user.setRoles(roles);
        user.setCreatedAt(new Date());
        userRepository.save(user);
    }

    public void addUserDto(UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        addUser(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    public List<UserDto> getUserDtos() {
        List<User> users = getUsers();
        return users.stream()
                .map(user -> mapper.userToUserDto(user))
                .collect(Collectors.toList());
    }

    public User getUser(UUID id) {
        return (id==null) ? null : userRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "users", key="#id", condition = "#id!=null", unless="#result == null")
    public UserDto getUserDto(UUID id) {
        System.out.println("Cacheable");
        User user = getUser(id);
        return mapper.userToUserDto(user);
    }

    public void updateUser(UserDto userDto, User user) {
        if(userDto.getPassword()!=null) userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        mapper.updateUserFromUserDto(userDto, user);
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }

    @CachePut(cacheNames = "users", key="#userDto.id", condition = "#userDto!=null && #userDto.id!=null", unless="#result == null")
    public UserDto updateUserDto(UserDto userDto) {
        System.out.println("CachePut");
        User user = getUser(userDto.getId());
        if(user!=null) updateUser(userDto, user);
        return mapper.userToUserDto(user);
    }


    @CacheEvict(cacheNames = "users", key="#id", condition = "#id!=null")
    public void deleteUserDto(UUID id) {
        if(userRepository.existsById(id)) userRepository.deleteById(id);
    }
}
