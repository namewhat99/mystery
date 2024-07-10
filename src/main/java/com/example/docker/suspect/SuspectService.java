package com.example.docker.suspect;

import com.example.docker.dto.ChatStreamResponseDto;
import com.example.docker.dto.SuspectChatDto;
import com.example.docker.dto.SuspectChatRequestDto;
import com.example.docker.dto.SuspectsInfoDto;
import com.example.docker.entity.Chat;
import com.example.docker.entity.Story;
import com.example.docker.entity.Suspect;
import com.example.docker.entity.User;
import com.example.docker.repository.ChatRepository;
import com.example.docker.repository.StoryRepository;
import com.example.docker.repository.SuspectRepository;
import com.example.docker.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
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

        SseEmitter sseEmitter = new SseEmitter();
        StringBuffer sb = new StringBuffer();

        if(suspect == null) throw new IllegalArgumentException("없는 용의자 혹은 용의자 번호입니다");
        else if (user == null) throw new IllegalArgumentException("없는 유저 번호 혹은 유저입니다");
        else if (story == null) throw new IllegalArgumentException("오늘의 스토리가 없습니다");

        String tempStory = "### 사건 배경:\n" +
                "- 장소: 촬영을 위해 준비 중인 방송국\n" +
                "- 시간: 낮\n" +
                "- 날씨: 발자국도 금방 사라질듯이 눈이 오는 날\n" +
                "\n" +
                "### 사건 상황:\n" +
                "장소: 촬영을 위해 준비 중인 방송국\n" +
                "시간: 낮\n" +
                "날씨: 발자국도 금방 사라질듯이 눈이 오는 날\n" +
                "\n" +
                "오늘, 인기 연예인의 생방송 촬영을 앞둔 방송국에서 비극적 사건이 발생했다. 연예인 김하영이 촬영 대기실에서 의식을 잃은 채 발견된 것이다. 목격자들에 따르면 김하영은 촬영 전 불안해 보였고, 매니저와 크게 다투는 장면이 포착되었다. 경찰은 김하영의 대기실에서 이상한 냄새가 나는 액체와 깨진 유리병을 증거로 확보했다. 방송국 내부는 마치 시간마저 얼어붙은 듯한 긴장감이 감돌고, 시작된 추리는 모두가 용의자일 수 있음을 시사하고 있다.\n" +
                "\n" +
                "### 피해자 정보:\n" +
                "- 이름: 김하영\n" +
                "- 나이: 27세\n" +
                "- 성별: 여성\n" +
                "- 직업: 인기 연예인 (가수 겸 배우)\n" +
                "\n" +
                "### 용의자 정보:\n" +
                "\n" +
                "- 이름: 박찬호\n" +
                "- 나이: 33세\n" +
                "- 성별: 남성\n" +
                "- 직업: 매니저\n" +
                "- 특이사항: \"김하영과 저는 5년 동안 같이 일해왔습니다. 우리는 서로 알만큼 알고, 이번 사건은 믿기지 않아요.\"\n" +
                "- 증거물품: 매니저 사무실에서 발견된 김하영의 의상\n" +
                "  증거물 설명: 김하영이 촬영 당일 입기로 되어있던 공연 의상으로, 매니저인 박찬호의 사무실에 있게 된 이유가 의문스러움.\n" +
                "\n" +
                "- 이름: 정수진\n" +
                "- 나이: 29세\n" +
                "- 성별: 여성\n" +
                "- 직업: 김하영의 스타일리스트\n" +
                "- 특이사항: \"하영씨와 일하면서 친해지려고 노력했지만, 항상 거리감이 느껴졌어요. 이번 촬영 준비도 예민하게 굴어서 좀 힘들었죠.\"\n" +
                "- 증거물품: 파손된 헤어 스프레이\n" +
                "  증거물 설명: 정수진이 평소 사용하던 것으로, 김하영 대기실에서 발견된 깨진 헤어 스프레이는 독특한 성분이 포함되어 있는데, 이는 김하영에게서 발견된 화학물질과 일치함.\n" +
                "\n" +
                "- 이름: 박준형\n" +
                "- 나이: 32세\n" +
                "- 성별: 남성\n" +
                "- 직업: 경호원\n" +
                "- 특이사항: \"그날 김하영씨 주변에 수상한 사람이 많았어. 내 임무는 그녀를 지키는 것이었고, 그 임무에 충실했을 뿐이야.\"\n" +
                "- 증거물품: 경호일지\n" +
                "  증거물 설명: 박준형이 생방송 당일 작성한 경호일지. 그가 마지막으로 김하영을 본 시간이 정확히 기록되어 있음.\n" +
                "\n" +
                "- 이름: 최유리\n" +
                "- 나이: 32세\n" +
                "- 성별: 여성\n" +
                "- 직업: 방송작가\n" +
                "- 특이사항: \"생방송 대본을 수정하느라 계속 바빴어요. 김하영 씨와는 별로 개인적인 대화는 없었습니다.\"\n" +
                "- 증거물품: 대본 노트\n" +
                "  증거물 설명: 방송 대본 수정본이 담긴 노트. 조그맣게 써진 개인적인 메모가 발견됨, \"하영...?\"\n" +
                "\n" +
                "### 범인 및 이유:\n" +
                "- 범인: 정수진\n" +
                "- 이유 1: 김하영의 대기실에서 발견된 깨진 헤어 스프레이가 정수진의 소유이며, 이는 김하영에게서 발견된 독특한 화학물질과 일치한다. 이는 정수진이 김하영에게 해를 가했다는 강력한 증거다.\n" +
                "- 이유 2: 정수진이 김하영과 일하면서 거리감을 느꼈고, 이번 촬영 준비 중에 예민하게 굴었다는 점에서 김하영에 대해 불만을 품을 여지가 있었다. 가까운 사이였던 만큼, 김하영의 생활과 행동 패턴을 잘 알고 있을 가능성이 크다.\n" +
                "- 사건의 전말: 김하영은 촬영 준비 중 예민하게 굴었고, 평소 그녀와의 거리감을 느꼈던 정수진은 이에 대한 불만이 쌓였다. 결국 정수진은 김하영에게 독특한 성분의 화학물질이 포함된 헤어 스프레이를 사용해 그녀에게 해를 입혔다.\n" +
                "- 범인의 한마디: \"모두 제 탓이에요. 그녀와의 거리감이 이렇게 큰 문제를 일으킬 줄은 몰랐어요.\"";

        String prompt = tempStory + "이게 추리 게임에 대한 배경이야" + "그리고 현재 용의자 중 " + suspect.getSuspectName() + " 에 대해서 심문을 진행하고 있어"
                + question + "이 질문에 대해" + suspect.getSuspectName() + "의 입장에서 짧게 대답해줘, 단 자신이 범인임을 밝히면 안돼 그리고 이 추리게임과 관련없을 것 같은 질문에는 답하는걸 거절해도 좋아";

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
                            sseEmitter.complete();
                        }
                        else{
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                            ChatStreamResponseDto streamDto = mapper.readValue(data,ChatStreamResponseDto.class);
                            ChatStreamResponseDto.Choice.Delta delta = streamDto.getChoices().get(0).getDelta();

                            if (delta!=null && delta.getContent()!=null){
                                sb.append(delta.getContent());
                                sseEmitter.send(delta.getContent());
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
