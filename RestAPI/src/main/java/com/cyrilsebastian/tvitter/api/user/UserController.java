package com.cyrilsebastian.tvitter.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public void signUp(@RequestBody(required = true) UserDto userDto) {
        userService.addUserDto(userDto);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUserDtos();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable(required = true) UUID userId) {
        return userService.getUserDto(userId);
    }

    @PutMapping("/{userId}")
    public void putUser(@PathVariable(required = true) UUID userId, @RequestBody(required = true) UserDto userDto) {
        userService.updateUserDto(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable(required = true) UUID userId) {
        userService.deleteUserDto(userId);
    }
}
