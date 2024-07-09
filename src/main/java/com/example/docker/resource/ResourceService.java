package com.example.docker.resource;

import com.example.docker.dto.EvidenceResourcePostDto;
import com.example.docker.dto.ResultResourcePostDto;
import com.example.docker.dto.StoryResourcePostDto;
import com.example.docker.dto.SuspectResourcePostDto;
import com.example.docker.entity.Evidence;
import com.example.docker.entity.Result;
import com.example.docker.entity.Story;
import com.example.docker.entity.Suspect;
import com.example.docker.repository.EvidenceRepository;
import com.example.docker.repository.ResultRepository;
import com.example.docker.repository.StoryRepository;
import com.example.docker.repository.SuspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final StoryRepository storyRepository;
    private final SuspectRepository suspectRepository;
    private final EvidenceRepository evidenceRepository;
    private final ResultRepository resultRepository;

    public void uploadResultResource(ResultResourcePostDto resultResourcePostDto){

        Boolean isResultExists = this.resultRepository.existsResultByDate(LocalDate.now());

        if(!isResultExists){
            Result result = Result.builder()
                    .resultContent1(resultResourcePostDto.getResultContent1())
                    .resultImageUrl1(resultResourcePostDto.getResultImage1())
                    .resultContent2(resultResourcePostDto.getResultContent2())
                    .resultImageUrl2(resultResourcePostDto.getResultImage2())
                    .caseBackground(resultResourcePostDto.getCaseBackground())
                    .criminal(resultResourcePostDto.getCriminal())
                    .criminalSaying(resultResourcePostDto.getCriminalSaying())
                    .build();

            this.resultRepository.save(result);
        }else{
            throw new IllegalArgumentException("오늘의 결과가 이미 추가되었습니다");
        }
    }

    public void uploadEvidenceResource(EvidenceResourcePostDto evidenceResourcePostDto){

        Integer evidenceCount = this.evidenceRepository.countEvidencesByDate(LocalDate.now());

        if(evidenceCount < 4){
            Evidence evidence = Evidence.builder()
                    .evidenceName(evidenceResourcePostDto.getEvidenceName())
                    .evidenceImageUrl(evidenceResourcePostDto.getEvidenceImage())
                    .evidenceInfo(evidenceResourcePostDto.getEvidenceInfo())
                    .build();

            this.evidenceRepository.save(evidence);
        }else{
            throw new IllegalArgumentException("오늘의 증거 4개가 이미 추가되었습니다");
        }
    }

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
