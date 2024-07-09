package com.example.docker.resource;

import com.example.docker.dto.StoryResourcePostDto;
import com.example.docker.entity.Story;
import com.example.docker.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StoryRepository storyRepository;

    public void uploadStoryResource(StoryResourcePostDto storyResourcePostDto){

        try{
            Boolean isStoryExists = this.storyRepository.existsStoryByDate(LocalDate.now());

            if(isStoryExists){
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
                throw new IllegalArgumentException("Story Exists");
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
    }
}
