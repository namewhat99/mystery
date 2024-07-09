package com.example.docker.user;

import ch.qos.logback.core.util.Loader;
import com.example.docker.entity.User;
import com.example.docker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addDailyUser(String nickname){

        Boolean isExists = this.userRepository.existsUserByNickNameAndDate(nickname , LocalDate.now());

        if(isExists) throw new IllegalArgumentException("닉네임이 이미 존재합니다");
        else {
            User user = User.builder()
                    .nickName(nickname)
                    .date(LocalDate.now())
                    .usedChance(0)
                    .build();

            return this.userRepository.save(user);
        }
    }

    public Boolean checkUserChanceLeft(Long userId){

        User user = this.userRepository.findUserById(userId);

        if(user == null) throw new EntityNotFoundException("해당 id 를 갖는 유저가 없습니다");
        else if(user.getUsedChance() > 10) throw new IllegalStateException("이미 기회를 다 사용하셨습니다");
        else return true;
    }
}
