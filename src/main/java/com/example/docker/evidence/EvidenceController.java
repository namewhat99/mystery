package com.example.docker.evidence;


import com.example.docker.dto.EvidenceInfoDto;
import com.example.docker.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/evidences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EvidenceController {

    private final EvidenceService evidenceService;
    @GetMapping
    @Operation(summary = "사건 현장" , description = "증거물품 리스트를 반환한다, evidenceNumber 는 아직 쓸지 안쓸지 미정상태")
    public ResponseDto<List<EvidenceInfoDto>> findEvidenceList(){
        List<EvidenceInfoDto> evidenceInfoDtoArrayList = this.evidenceService.getEvidenceList();
        return new ResponseDto<List<EvidenceInfoDto>>(200 , "Good" , evidenceInfoDtoArrayList);
    }
}
