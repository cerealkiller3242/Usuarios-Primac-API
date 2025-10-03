package com.example.usuariosprimacapi.Beneficiary.domain;

import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryRequestDto;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryResponseDto;
import com.example.usuariosprimacapi.Beneficiary.dto.BeneficiaryPatchDto;
import com.example.usuariosprimacapi.Beneficiary.infrastructure.BeneficiaryRepository;
import com.example.usuariosprimacapi.Client.domain.Client;
import com.example.usuariosprimacapi.Client.infrastructure.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeneficiaryService {
    private final BeneficiaryRepository beneficiaryRepository;
    private final ClientRepository clientRepository;

    public Page<BeneficiaryResponseDto> getAllBeneficiaries(Pageable pageable) {
        return beneficiaryRepository.findAllWithClientAndUser(pageable)
                .map(this::mapToResponseDto);
    }
    
    public Page<BeneficiaryResponseDto> searchBeneficiaries(String firstName, String lastName, String documentNumber, Long clientId, Pageable pageable) {
        return beneficiaryRepository.findBySearchCriteria(firstName, lastName, documentNumber, clientId, pageable)
                .map(this::mapToResponseDto);
    }

    // Método legacy para compatibilidad (no recomendado para 20k registros)
    public List<BeneficiaryResponseDto> getAllBeneficiaries() {
        return beneficiaryRepository.findAllWithClientAndUser().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public BeneficiaryResponseDto getBeneficiaryById(Long id) {
        Beneficiary beneficiary = beneficiaryRepository.findByIdWithClientAndUser(id)
                .orElseThrow(() -> new EntityNotFoundException("Beneficiary not found with id: " + id));
        return mapToResponseDto(beneficiary);
    }

    public BeneficiaryResponseDto createBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto) {
        Client client = clientRepository.findById(beneficiaryRequestDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + beneficiaryRequestDto.getClientId()));
        
        Beneficiary beneficiary = mapToEntity(beneficiaryRequestDto, client);
        beneficiary.setCreatedAt(LocalDateTime.now());
        beneficiary.setUpdatedAt(LocalDateTime.now());
        
        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
        return mapToResponseDto(savedBeneficiary);
    }

    public BeneficiaryResponseDto updateBeneficiary(Long id, BeneficiaryRequestDto beneficiaryRequestDto) {
        Beneficiary existingBeneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beneficiary not found with id: " + id));
        
        Client client = clientRepository.findById(beneficiaryRequestDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + beneficiaryRequestDto.getClientId()));

        // Update fields
        existingBeneficiary.setFirstName(beneficiaryRequestDto.getFirstName());
        existingBeneficiary.setLastName(beneficiaryRequestDto.getLastName());
        existingBeneficiary.setDocumentType(beneficiaryRequestDto.getDocumentType());
        existingBeneficiary.setDocumentNumber(beneficiaryRequestDto.getDocumentNumber());
        existingBeneficiary.setBirthDate(beneficiaryRequestDto.getBirthDate());
        existingBeneficiary.setRelationship(beneficiaryRequestDto.getRelationship());
        existingBeneficiary.setClient(client);
        existingBeneficiary.setUpdatedAt(LocalDateTime.now());

        Beneficiary updatedBeneficiary = beneficiaryRepository.save(existingBeneficiary);
        return mapToResponseDto(updatedBeneficiary);
    }

    public BeneficiaryResponseDto patchBeneficiary(Long id, BeneficiaryPatchDto beneficiaryPatchDto) {
        Beneficiary existingBeneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beneficiary not found with id: " + id));
        
        boolean updated = false;
        
        // Update fields only if they are provided
        if (beneficiaryPatchDto.getFirstName() != null) {
            existingBeneficiary.setFirstName(beneficiaryPatchDto.getFirstName());
            updated = true;
        }
        if (beneficiaryPatchDto.getLastName() != null) {
            existingBeneficiary.setLastName(beneficiaryPatchDto.getLastName());
            updated = true;
        }
        if (beneficiaryPatchDto.getDocumentType() != null) {
            existingBeneficiary.setDocumentType(beneficiaryPatchDto.getDocumentType());
            updated = true;
        }
        if (beneficiaryPatchDto.getDocumentNumber() != null) {
            existingBeneficiary.setDocumentNumber(beneficiaryPatchDto.getDocumentNumber());
            updated = true;
        }
        if (beneficiaryPatchDto.getBirthDate() != null) {
            existingBeneficiary.setBirthDate(beneficiaryPatchDto.getBirthDate());
            updated = true;
        }
        if (beneficiaryPatchDto.getRelationship() != null) {
            existingBeneficiary.setRelationship(beneficiaryPatchDto.getRelationship());
            updated = true;
        }
        if (beneficiaryPatchDto.getClientId() != null) {
            Client client = clientRepository.findById(beneficiaryPatchDto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + beneficiaryPatchDto.getClientId()));
            existingBeneficiary.setClient(client);
            updated = true;
        }
        
        if (updated) {
            existingBeneficiary.setUpdatedAt(LocalDateTime.now());
            existingBeneficiary = beneficiaryRepository.save(existingBeneficiary);
        }
        
        return mapToResponseDto(existingBeneficiary);
    }

    public void deleteBeneficiary(Long id) {
        if (!beneficiaryRepository.existsById(id)) {
            throw new EntityNotFoundException("Beneficiary not found with id: " + id);
        }
        beneficiaryRepository.deleteById(id);
    }

    private Beneficiary mapToEntity(BeneficiaryRequestDto beneficiaryRequestDto, Client client) {
        return Beneficiary.builder()
                .firstName(beneficiaryRequestDto.getFirstName())
                .lastName(beneficiaryRequestDto.getLastName())
                .documentType(beneficiaryRequestDto.getDocumentType())
                .documentNumber(beneficiaryRequestDto.getDocumentNumber())
                .birthDate(beneficiaryRequestDto.getBirthDate())
                .relationship(beneficiaryRequestDto.getRelationship())
                .client(client)
                .build();
    }

    private BeneficiaryResponseDto mapToResponseDto(Beneficiary beneficiary) {
        return BeneficiaryResponseDto.builder()
                .id(beneficiary.getId())
                .firstName(beneficiary.getFirstName())
                .lastName(beneficiary.getLastName())
                .documentType(beneficiary.getDocumentType())
                .documentNumber(beneficiary.getDocumentNumber())
                .birthDate(beneficiary.getBirthDate())
                .relationship(beneficiary.getRelationship())
                .createdAt(beneficiary.getCreatedAt())
                .updatedAt(beneficiary.getUpdatedAt())
                .clientId(beneficiary.getClient() != null ? beneficiary.getClient().getUserId() : null)
                .build();
    }
}