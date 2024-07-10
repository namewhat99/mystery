package com.example.docker.suspect;

import com.example.docker.dto.SuspectChatDto;
import com.example.docker.dto.SuspectsInfoDto;
import com.example.docker.entity.Chat;
import com.example.docker.entity.Suspect;
import com.example.docker.entity.User;
import com.example.docker.repository.ChatRepository;
import com.example.docker.repository.SuspectRepository;
import com.example.docker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuspectService {

    private final SuspectRepository suspectRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public List<SuspectsInfoDto> findSuspects(){
        List<Suspect> suspectsByDate = this.suspectRepository.findSuspectsByDate(LocalDate.now());

        List<SuspectsInfoDto> suspectsInfoDtoList = new ArrayList<>();

        for (Suspect suspect : suspectsByDate) {
            SuspectsInfoDto suspectsInfoDto = new SuspectsInfoDto();
            suspectsInfoDto.setSuspectNumber(suspect.getId());
            suspectsInfoDto.setSuspectName(suspect.getSuspectName());
            suspectsInfoDto.setSuspectAge(suspect.getSuspectAge());
            suspectsInfoDto.setSuspectGender(suspect.getSuspectGender());
            suspectsInfoDto.setSuspectImageUrl(suspect.getSuspectImage());
            suspectsInfoDto.setSuspectOccupation(suspect.getSuspectOccupation());
            suspectsInfoDtoList.add(suspectsInfoDto);
        }

        return suspectsInfoDtoList;
    }

    public SuspectChatDto findSuspectChat(Integer suspectNumber , Long userId){

        Suspect suspect = this.suspectRepository.findSuspectById(Long.valueOf(suspectNumber));
        User user = this.userRepository.findUserById(userId);

        if(suspect == null) throw new IllegalArgumentException("없는 용의자 혹은 용의자 번호입니다");
        else if (user == null) throw new IllegalArgumentException("없는 유저 번호 혹은 유저입니다");

        List<Chat> chats = this.chatRepository.findChatsByUserIdAndSuspectNumberOrderByIdAsc(userId, Long.valueOf(suspectNumber));
        List<String> chatList = new ArrayList<>();

        for (Chat chat : chats) {
            chatList.add(chat.getChatContent());
        }
        return SuspectChatDto.builder().firstLine(suspect.getSuspectTrait()).chatList(chatList).build();

    }

    public Flux<String> getSuspectAnswer(){

    }


}
