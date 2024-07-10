package com.example.docker.suspect;

import com.example.docker.dto.SuspectsInfoDto;
import com.example.docker.entity.Suspect;
import com.example.docker.repository.SuspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuspectService {

    private final SuspectRepository suspectRepository;

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


}
