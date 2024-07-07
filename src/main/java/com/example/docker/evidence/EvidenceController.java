package com.example.docker.evidence;


import com.example.docker.dto.EvidenceInfoDto;
import com.example.docker.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/evidences")
public class EvidenceController {

    @GetMapping
    @Operation(summary = "사건 현장" , description = "증거물품 리스트를 반환한다, evidenceNumber 는 아직 쓸지 안쓸지 미정상태")
    public ResponseDto<ArrayList<EvidenceInfoDto>> findEvidenceList(){
        return new ResponseDto<ArrayList<EvidenceInfoDto>>(200 , "Good" , null);
    }
}
