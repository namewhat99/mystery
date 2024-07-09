package com.example.docker.main;

import com.example.docker.dto.ImageDto;
import com.example.docker.dto.MainPageContentDto;
import com.example.docker.dto.MainPageStoryDto;
import com.example.docker.dto.ResultDto;
import com.example.docker.entity.Result;
import com.example.docker.entity.Story;
import com.example.docker.entity.Suspect;
import com.example.docker.repository.ResultRepository;
import com.example.docker.repository.StoryRepository;
import com.example.docker.repository.SuspectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MainService {

    private final StoryRepository storyRepository;
    private final ResultRepository resultRepository;
    private final SuspectRepository suspectRepository;
    public MainPageContentDto getMainPageContent(){

        Story story = this.storyRepository.findStoryByDate(LocalDate.now());

        if(story == null) throw new EntityNotFoundException("오늘의 story 가 없습니다");

        return MainPageContentDto.builder()
                .weather(story.getWeather())
                .time(story.getTime())
                .place(story.getPlace())
                .build();
    }

    public ImageDto getMainImage(){

        Story story = this.storyRepository.findStoryByDate(LocalDate.now());

        if(story == null) throw new EntityNotFoundException("오늘의 story 가 없습니다");

        return ImageDto.builder()
                .backgroundImageUrl(story.getMainBackGroundImage())
                .build();
    }

    public MainPageStoryDto getMainPageStory() {
        Story story = this.storyRepository.findStoryByDate(LocalDate.now());

        if (story == null) throw new EntityNotFoundException("오늘의 story 가 없습니다");

        return MainPageStoryDto.builder()
                .storyLine(story.getStoryLine())
                .build();

    }

    public ResultDto getResult(){

        Result result = this.resultRepository.findResultByDate(LocalDate.now());

        Suspect suspect = this.suspectRepository.findSuspectByDateAndSuspectName(LocalDate.now() , result.getCriminal());


        if (result == null) throw new EntityNotFoundException("오늘의 결과가 없습니다");
        else if (suspect == null) {
            throw new EntityNotFoundException("해당 이름의 범인이 없습니다");
        }


        return ResultDto.builder()
                .resultContent1(result.getResultContent1())
                .resultImage1(result.getResultImageUrl1())
                .resultContent2(result.getResultContent2())
                .resultImage2(result.getResultImageUrl2())
                .criminalSaying(result.getCriminalSaying())
                .caseBackground(result.getCaseBackground())
                .criminal(result.getCriminal())
                .criminalImage(suspect.getSuspectImage())
                .build();

    }
}
