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
                    .allStory(resultResourcePostDto.getAllStory())
                    .build();

            this.resultRepository.save(result);
        }else{
            throw new IllegalArgumentException("오늘의 결과가 이미 추가되었습니다");
        }
    }

    public void uploadSuspectResource(SuspectResourcePostDto suspectResourcePostDto){

        Integer suspectCount = this.suspectRepository.countSuspectsByDate(LocalDate.now());
        Integer evidenceCount = this.evidenceRepository.countEvidencesByDate(LocalDate.now());

        if(suspectCount < 4 || evidenceCount < 4){
            Suspect suspect = Suspect.builder()
                    .suspectName(suspectResourcePostDto.getSuspectName())
                    .suspectAge(suspectResourcePostDto.getSuspectAge())
                    .suspectGender(suspectResourcePostDto.getSuspectGender())
                    .suspectOccupation(suspectResourcePostDto.getSuspectOccupation())
                    .suspectTrait(suspectResourcePostDto.getSuspectTrait())
                    .suspectImage(suspectResourcePostDto.getSuspectImage())
                    .suspectSpeciality(suspectResourcePostDto.getSuspectSpeciality())
                    .build();

            Evidence evidence = Evidence.builder()
                    .evidenceName(suspectResourcePostDto.getEvidenceName())
                    .evidenceImageUrl(suspectResourcePostDto.getEvidenceImage())
                    .evidenceInfo(suspectResourcePostDto.getEvidenceInfo())
                    .build();

            this.suspectRepository.save(suspect);
            this.evidenceRepository.save(evidence);

        }else{
            throw new IllegalArgumentException("오늘의 용의자 , 증거 4개가 이미 추가되었습니다");
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
                    .victimName(storyResourcePostDto.getVictimName())
                    .victimGender(storyResourcePostDto.getVictimGender())
                    .victimAge(storyResourcePostDto.getVictimAge())
                    .victimOccupation(storyResourcePostDto.getVictimOccupation())
                    .mainBackGroundImage(storyResourcePostDto.getMainBackgroundImage())
                    .build();

            this.storyRepository.save(story);

        }else{
            throw new IllegalArgumentException("오늘의 스토리가 이미 존재합니다");
        }

    }
}
