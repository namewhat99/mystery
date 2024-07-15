package com.example.docker.suspect;

import com.example.docker.dto.ChatStreamResponseDto;
import com.example.docker.dto.SuspectChatDto;
import com.example.docker.dto.SuspectChatRequestDto;
import com.example.docker.dto.SuspectsInfoDto;
import com.example.docker.entity.*;
import com.example.docker.repository.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuspectService {

    private final SuspectRepository suspectRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;
    private final ResultRepository resultRepository;
    private final WebClient webClient;

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
            suspectsInfoDto.setSuspectSpeciality(suspect.getSuspectSpeciality());
            suspectsInfoDto.setSuspectOccupation(suspect.getSuspectOccupation());
            suspectsInfoDtoList.add(suspectsInfoDto);
        }

        return suspectsInfoDtoList;
    }

    public SuspectChatDto findSuspectChat(Integer suspectNumber , Long userId){

        Suspect suspect = this.suspectRepository.findSuspectById(Long.valueOf(suspectNumber));
        User user = this.userRepository.findUserById(userId);

        System.out.println("user = " + user);
        System.out.println("suspect = " + suspect);

        if(suspect == null) throw new IllegalArgumentException("없는 용의자 혹은 용의자 번호입니다");
        else if (user == null) throw new IllegalArgumentException("없는 유저 번호 혹은 유저입니다");

        List<Chat> chats = this.chatRepository.findChatsByUserIdAndSuspectNumberOrderByIdAsc(userId, Long.valueOf(suspectNumber));
        List<String> chatList = new ArrayList<>();

        for (Chat chat : chats) {
            chatList.add(chat.getChatContent());
        }
        return SuspectChatDto.builder().firstLine(suspect.getSuspectTrait()).chatList(chatList).build();

    }

    public Flux<String> getSuspectAnswer(Integer suspectNumber , SuspectChatRequestDto suspectChatRequestDto){
        Long userId = suspectChatRequestDto.getUserId().longValue();
        String question = suspectChatRequestDto.getQuestion();

        Suspect suspect = this.suspectRepository.findSuspectById(Long.valueOf(suspectNumber));
        User user = this.userRepository.findUserById(userId);
        Story story = this.storyRepository.findStoryByDate(LocalDate.now());

        Chat chat = Chat.builder()
                .userId(userId)
                .suspectNumber(suspectNumber.longValue())
                .chatContent(suspectChatRequestDto.getQuestion())
                .dateTime(LocalDateTime.now())
                .build();

        this.chatRepository.save(chat);

        List<Chat> chats = this.chatRepository.findChatsByUserIdAndSuspectNumberOrderByIdAsc(userId, Long.valueOf(suspectNumber));
        List<String> chatList = new ArrayList<>();

        for (Chat item : chats) {
            chatList.add(item.getChatContent());
        }

        String prevChat = chatList.toString();

        Result result = this.resultRepository.findResultByDate(LocalDate.now());

        String todayStory = result.getAllStory();

        StringBuffer sb = new StringBuffer();

        if(suspect == null) throw new IllegalArgumentException("없는 용의자 혹은 용의자 번호입니다");
        else if (user == null) throw new IllegalArgumentException("없는 유저 번호 혹은 유저입니다");
        else if (story == null) throw new IllegalArgumentException("오늘의 스토리가 없습니다");



        String prompt = todayStory + "이게 추리 게임에 대한 전체적인 내용이야" + "그리고 현재 용의자 중 " + suspect.getSuspectName() + " 에 대해서 심문을 진행하고 있어"
               + question + "이 질문에 대해" + suspect.getSuspectName() + "의 입장에서 짧게 대답해줘" + prevChat + "이건 이전 대화 내역이야";

        Gson gson = new Gson();

        // JSON 객체 생성
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4o");
        requestBody.addProperty("stream" , true);

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        messages.add(message);

        requestBody.add("messages", messages);

        String jsonRequestBody = gson.toJson(requestBody);
        // Flux 의 동작방식에 대해 찾아보기

        return this.webClient.post()
                .bodyValue(jsonRequestBody)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchangeToFlux(response -> response.bodyToFlux(String.class))
                .doOnNext(data -> {
                    try {
                        if (data.equals("[DONE]")) {

                            Chat userChat = Chat.builder()
                                    .userId(userId)
                                    .suspectNumber(suspectNumber.longValue())
                                    .chatContent(sb.toString())
                                    .dateTime(LocalDateTime.now())
                                    .build();

                            // 메세지 저장과 전송 종료
                            this.chatRepository.save(userChat);
                        }
                        else{
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                            ChatStreamResponseDto streamDto = mapper.readValue(data,ChatStreamResponseDto.class);
                            ChatStreamResponseDto.Choice.Delta delta = streamDto.getChoices().get(0).getDelta();

                            if (delta!=null && delta.getContent()!=null){
                                sb.append(delta.getContent());
                            }
                        }
                    } catch (IOException e) {
                        throw new IllegalArgumentException();
                    }
                });

    }

    private String saveResponse(String response){
        Gson gson = new Gson();

        // JSON 문자열을 JsonObject로 변환
        JsonObject responseObject = gson.fromJson(response, JsonObject.class);

        // choices 배열을 가져옴
        JsonArray choicesArray = responseObject.getAsJsonArray("choices");

        // 첫 번째 요소의 delta 객체를 가져옴
        JsonObject deltaObject = choicesArray.get(0).getAsJsonObject().getAsJsonObject("delta");

        // content 필드를 추출
        String content = deltaObject.get("content").getAsString();

        // content 출력
        System.out.println(content);
        return response;
    }



}
