package com.example.docker.user;

import ch.qos.logback.core.util.Loader;
import com.example.docker.entity.User;
import com.example.docker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addDailyUser(HttpServletRequest request){

        HttpSession sessionId = request.getSession(false);

        if(sessionId == null){
            User user = User.builder()
                    .sessionId(String.valueOf(request.getSession(true)))
                    .date(LocalDate.now())
                    .usedChance(0)
                    .build();

            return this.userRepository.save(user);

        }else{

            return null;
        }
    }

    public Boolean checkUserChanceLeft(Long userId){

        User user = this.userRepository.findUserById(userId);

        if(user == null) throw new EntityNotFoundException("해당 id 를 갖는 유저가 없습니다");
        else return user.getUsedChance() <= 10;

    }
}
