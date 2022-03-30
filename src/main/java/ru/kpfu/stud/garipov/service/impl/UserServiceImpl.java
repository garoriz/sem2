package ru.kpfu.stud.garipov.service.impl;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.stud.garipov.config.MailConfig;
import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;
import ru.kpfu.stud.garipov.model.User;
import ru.kpfu.stud.garipov.repository.UserRepository;
import ru.kpfu.stud.garipov.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JavaMailSender javaMailSender;
    private final MailConfig mailConfig;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder encoder,
            JavaMailSender javaMailSender,
            MailConfig mailConfig
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.javaMailSender = javaMailSender;
        this.mailConfig = mailConfig;
    }

    @Override
    public UserDto getById(Integer id) {
        return userRepository.findById(id).map(UserDto::fromModel).orElse(null);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserDto::fromModel).collect(Collectors.toList());
    }

    @Override
    public UserDto signUp(CreateUserDto createUserDto, String url) {
        String code = RandomString.make(64);
        String encodedPassword = encoder.encode(createUserDto.getPassword());
        User user = new User(createUserDto.getName(), createUserDto.getEmail(), encodedPassword, code);
        User savedUser = userRepository.save(user);
        sendVerificationMail(createUserDto.getEmail(), createUserDto.getName(), code, url);
        return UserDto.fromModel(user);
    }

    @Override
    public void sendVerificationMail(String mail, String name, String code, String url) {

        String from = mailConfig.getFrom();
        String sender = mailConfig.getSender();
        String subject = mailConfig.getSubject();
        String content = mailConfig.getContent();


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(from, sender);

            helper.setTo(mail);
            helper.setSubject(subject);

            content = content.replace("{name}", name);
            content = content.replace("{url}", url + "/verification?code=" + code);

            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user != null) {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
