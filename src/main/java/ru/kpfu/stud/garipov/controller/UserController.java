package ru.kpfu.stud.garipov.controller;

import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;
import ru.kpfu.stud.garipov.helper.PasswordHelper;
import ru.kpfu.stud.garipov.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.stud.garipov.repository.UserRepository;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static ru.kpfu.stud.garipov.dto.UserDto.fromModel;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Iterable<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserDto::fromModel).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public UserDto get(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user.equals(null))
            return null;
        return fromModel(user);
    }

    @PostMapping("/user")
    public UserDto createUser(@Valid @RequestBody CreateUserDto user) {
        return fromModel(userRepository.save(
                new User(user.getName(), user.getEmail(), PasswordHelper.encrypt(user.getPassword())))
        );
    }
}
