package com.example.docker.resource;

import com.example.docker.dto.StoryResourcePostDto;
import com.example.docker.dto.SuspectResourcePostDto;
import com.example.docker.entity.Story;
import com.example.docker.entity.Suspect;
import com.example.docker.repository.StoryRepository;
import com.example.docker.repository.SuspectRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StoryRepository storyRepository;
    private final SuspectRepository suspectRepository;

    public void uploadSuspectResource(SuspectResourcePostDto suspectResourcePostDto){

        Integer suspectCount = this.suspectRepository.countSuspectsByDate(LocalDate.now());

        if(suspectCount < 4){
            Suspect suspect = Suspect.builder()
                    .suspectName(suspectResourcePostDto.getSuspectName())
                    .suspectAge(suspectResourcePostDto.getSuspectAge())
                    .suspectGender(suspectResourcePostDto.getSuspectGender())
                    .suspectOccupation(suspectResourcePostDto.getSuspectOccupation())
                    .suspectTrait(suspectResourcePostDto.getSuspectTrait())
                    .suspectImage(suspectResourcePostDto.getSuspectImage())
                    .build();

            this.suspectRepository.save(suspect);

        }else{
            throw new IllegalArgumentException("오늘의 용의자 4명이 이미 추가되었습니다");
        }
    }

    public void uploadStoryResource(StoryResourcePostDto storyResourcePostDto){

        Boolean isStoryExists = this.storyRepository.existsStoryByDate(LocalDate.now());

        if(!isStoryExists){
            Story story = Story.builder()
                    .time(storyResourcePostDto.getTime())
                    .place(storyResourcePostDto.getPlace())
                    .weather(storyResourcePostDto.getWeather())
                    .storyLine(storyResourcePostDto.getStoryLine())
                    .allStory(storyResourcePostDto.getAllStory())
                    .mainBackGroundImage(storyResourcePostDto.getMainBackgroundImage())
                    .build();

            this.storyRepository.save(story);

        }else{
            throw new IllegalArgumentException("오늘의 스토리가 이미 존재합니다");
        }

    }
}
