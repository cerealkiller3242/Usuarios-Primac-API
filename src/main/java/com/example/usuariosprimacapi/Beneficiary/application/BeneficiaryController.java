package com.example.usuariosprimacapi.Beneficiary.application;

import com.example.usuariosprimacapi.Beneficiary.domain.BeneficiaryService;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryRequestDto;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryResponseDto;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryPatchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {
    private final BeneficiaryService beneficiaryService;

    @GetMapping
    public ResponseEntity<Page<BeneficiaryResponseDto>> getAllBeneficiaries(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(beneficiaryService.getAllBeneficiaries(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BeneficiaryResponseDto>> searchBeneficiaries(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) Long clientId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(beneficiaryService.searchBeneficiaries(firstName, lastName, documentNumber, clientId, pageable));
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

    @PatchMapping("/{id}")
    public ResponseEntity<BeneficiaryResponseDto> patchBeneficiary(@PathVariable Long id, @Valid @RequestBody BeneficiaryPatchDto beneficiaryPatchDto) {
        return ResponseEntity.ok(beneficiaryService.patchBeneficiary(id, beneficiaryPatchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
        return ResponseEntity.noContent().build();
    }
}