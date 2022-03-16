package ru.kpfu.stud.garipov.service;

import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    UserDto getByEmail(String email);

    UserDto getById(Integer id);

    List<UserDto> getAll();

    UserDto signUp(CreateUserDto createUserDto, String url) throws MessagingException;

    void sendVerificationMail(String mail, String name, String code, String url) throws MessagingException;

    boolean verify(String verificationCode);
}
