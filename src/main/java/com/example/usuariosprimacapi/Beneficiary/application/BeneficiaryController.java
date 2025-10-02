package com.example.usuariosprimacapi.Beneficiary.application;

import com.example.usuariosprimacapi.Beneficiary.domain.BeneficiaryService;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryRequestDto;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {
    private final BeneficiaryService beneficiaryService;

    @GetMapping
    public ResponseEntity<List<BeneficiaryResponseDto>> getAllBeneficiaries() {
        return ResponseEntity.ok(beneficiaryService.getAllBeneficiaries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaryResponseDto> getBeneficiaryById(@PathVariable Long id) {
        return ResponseEntity.ok(beneficiaryService.getBeneficiaryById(id));
    }

    @PostMapping
    public ResponseEntity<BeneficiaryResponseDto> createBeneficiary(@Valid @RequestBody BeneficiaryRequestDto beneficiaryRequestDto) {
        return new ResponseEntity<>(beneficiaryService.createBeneficiary(beneficiaryRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficiaryResponseDto> updateBeneficiary(@PathVariable Long id, @Valid @RequestBody BeneficiaryRequestDto beneficiaryRequestDto) {
        return ResponseEntity.ok(beneficiaryService.updateBeneficiary(id, beneficiaryRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }
}