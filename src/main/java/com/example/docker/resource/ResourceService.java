package com.example.docker.resource;

import com.example.docker.dto.StoryResourcePostDto;
import com.example.docker.entity.Story;
import com.example.docker.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StoryRepository storyRepository;

    public void uploadStoryResource(StoryResourcePostDto storyResourcePostDto){
        try{
            Story story = Story.builder()
                    .time(storyResourcePostDto.getTime())
                    .place(storyResourcePostDto.getPlace())
                    .weather(storyResourcePostDto.getWeather())
                    .storyLine(storyResourcePostDto.getStoryLine())
                    .allStory(storyResourcePostDto.getAllStory())
                    .mainBackGroundImage(storyResourcePostDto.getMainBackgroundImage())
                    .build();

            this.storyRepository.save(story);
        }catch (Exception e){
            System.out.println("e = " + e);
        }
    }
}
