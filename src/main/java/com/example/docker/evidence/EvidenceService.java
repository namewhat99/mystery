package com.example.docker.evidence;

import com.example.docker.dto.EvidenceInfoDto;
import com.example.docker.entity.Evidence;
import com.example.docker.repository.EvidenceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvidenceService {

    public final EvidenceRepository evidenceRepository;

    public List<EvidenceInfoDto> getEvidenceList(){

        List<Evidence> evidencesByDate = this.evidenceRepository.findEvidencesByDate(LocalDate.now());

        List<EvidenceInfoDto> evidenceInfoDtoList = new ArrayList<EvidenceInfoDto>();

        for (Evidence evidence : evidencesByDate) {
            EvidenceInfoDto evidenceInfoDto = new EvidenceInfoDto(evidence.getEvidenceName() , evidence.getEvidenceInfo(), evidence.getEvidenceImageUrl());
            evidenceInfoDtoList.add(evidenceInfoDto);
        }

        return evidenceInfoDtoList;
    }
}
