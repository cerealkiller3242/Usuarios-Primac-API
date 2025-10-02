package com.example.usuariosprimacapi.Beneficiary.domain;

import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryRequestDto;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryResponseDto;
import com.example.usuariosprimacapi.Beneficiary.infrastructure.BeneficiaryRepository;
import com.example.usuariosprimacapi.User.domain.User;
import com.example.usuariosprimacapi.User.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {
    private final BeneficiaryRepository beneficiaryRepository;
    private final UserRepository userRepository;

    public List<BeneficiaryResponseDto> getAllBeneficiaries() {
        return beneficiaryRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public BeneficiaryResponseDto getBeneficiaryById(Long id) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beneficiary not found with id: " + id));
        return mapToResponseDto(beneficiary);
    }

    public BeneficiaryResponseDto createBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto) {
        User user = userRepository.findById(beneficiaryRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + beneficiaryRequestDto.getUserId()));
        
        Beneficiary beneficiary = mapToEntity(beneficiaryRequestDto, user);
        beneficiary.setCreatedAt(LocalDateTime.now());
        beneficiary.setUpdatedAt(LocalDateTime.now());
        
        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
        return mapToResponseDto(savedBeneficiary);
    }

    public BeneficiaryResponseDto updateBeneficiary(Long id, BeneficiaryRequestDto beneficiaryRequestDto) {
        Beneficiary existingBeneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beneficiary not found with id: " + id));
        
        User user = userRepository.findById(beneficiaryRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + beneficiaryRequestDto.getUserId()));

        // Update fields
        existingBeneficiary.setFirst_name(beneficiaryRequestDto.getFirst_name());
        existingBeneficiary.setLast_name(beneficiaryRequestDto.getLast_name());
        existingBeneficiary.setDocument_type(beneficiaryRequestDto.getDocument_type());
        existingBeneficiary.setDocument_number(beneficiaryRequestDto.getDocument_number());
        existingBeneficiary.setBirth_date(beneficiaryRequestDto.getBirth_date());
        existingBeneficiary.setRelationship(beneficiaryRequestDto.getRelationship());
        existingBeneficiary.setUser(user);
        existingBeneficiary.setUpdatedAt(LocalDateTime.now());

        Beneficiary updatedBeneficiary = beneficiaryRepository.save(existingBeneficiary);
        return mapToResponseDto(updatedBeneficiary);
    }

    public void deleteBeneficiary(Long id) {
        if (!beneficiaryRepository.existsById(id)) {
            throw new EntityNotFoundException("Beneficiary not found with id: " + id);
        }
        beneficiaryRepository.deleteById(id);
    }

    private Beneficiary mapToEntity(BeneficiaryRequestDto beneficiaryRequestDto, User user) {
        return Beneficiary.builder()
                .first_name(beneficiaryRequestDto.getFirst_name())
                .last_name(beneficiaryRequestDto.getLast_name())
                .document_type(beneficiaryRequestDto.getDocument_type())
                .document_number(beneficiaryRequestDto.getDocument_number())
                .birth_date(beneficiaryRequestDto.getBirth_date())
                .relationship(beneficiaryRequestDto.getRelationship())
                .user(user)
                .build();
    }

    private BeneficiaryResponseDto mapToResponseDto(Beneficiary beneficiary) {
        return BeneficiaryResponseDto.builder()
                .id(beneficiary.getId())
                .first_name(beneficiary.getFirst_name())
                .last_name(beneficiary.getLast_name())
                .document_type(beneficiary.getDocument_type())
                .document_number(beneficiary.getDocument_number())
                .birth_date(beneficiary.getBirth_date())
                .relationship(beneficiary.getRelationship())
                .createdAt(beneficiary.getCreatedAt())
                .updatedAt(beneficiary.getUpdatedAt())
                .userId(beneficiary.getUser() != null ? beneficiary.getUser().getId() : null)
                .build();
    }
}