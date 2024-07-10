package com.example.docker.dto;

import com.example.docker.entity.Evidence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvidenceInfoDto {
    private String evidenceName;
    private String evidenceInfo;
    private String evidenceImageUrl;

}
