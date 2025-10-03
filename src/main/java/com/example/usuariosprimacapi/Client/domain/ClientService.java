package com.example.usuariosprimacapi.Client.domain;

import com.example.usuariosprimacapi.Client.dto.ClientRequestDto;
import com.example.usuariosprimacapi.Client.dto.ClientResponseDto;
import com.example.usuariosprimacapi.Client.dto.ClientPatchDto;
import com.example.usuariosprimacapi.Client.infrastructure.ClientRepository;
import com.example.usuariosprimacapi.User.domain.User;
import com.example.usuariosprimacapi.User.domain.rol;
import com.example.usuariosprimacapi.User.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<ClientResponseDto> getAllClients(Pageable pageable) {
        return clientRepository.findAllWithUser(pageable)
                .map(this::mapToResponseDto);
    }
    
    public Page<ClientResponseDto> searchClients(String firstName, String lastName, String documentNumber, Pageable pageable) {
        return clientRepository.findBySearchCriteria(firstName, lastName, documentNumber, pageable)
                .map(this::mapToResponseDto);
    }

    // Método legacy para compatibilidad (no recomendado para 20k registros)
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAllWithUser().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findByIdWithUser(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        return mapToResponseDto(client);
    }

    @Transactional
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        // Create and save User first
        User user = User.builder()
                .username(clientRequestDto.getUsername())
                .email(clientRequestDto.getEmail())
                .password(passwordEncoder.encode(clientRequestDto.getPassword()))
                .phone(clientRequestDto.getPhone())
                .street(clientRequestDto.getStreet())
                .city(clientRequestDto.getCity())
                .state(clientRequestDto.getState())
                .role(rol.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        User savedUser = userRepository.save(user);
        
        // Create Client with reference to saved User
        Client client = Client.builder()
                .user(savedUser)
                .firstName(clientRequestDto.getFirstName())
                .lastName(clientRequestDto.getLastName())
                .documentType(clientRequestDto.getDocumentType())
                .documentNumber(clientRequestDto.getDocumentNumber())
                .birthDate(clientRequestDto.getBirthDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Client savedClient = clientRepository.save(client);
        return mapToResponseDto(savedClient);
    }

    @Transactional
    public ClientResponseDto updateClient(Long id, ClientRequestDto clientRequestDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        
        // Update User fields
        User user = existingClient.getUser();
        user.setUsername(clientRequestDto.getUsername());
        user.setEmail(clientRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(clientRequestDto.getPassword()));
        user.setPhone(clientRequestDto.getPhone());
        user.setStreet(clientRequestDto.getStreet());
        user.setCity(clientRequestDto.getCity());
        user.setState(clientRequestDto.getState());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        
        // Update Client fields
        existingClient.setFirstName(clientRequestDto.getFirstName());
        existingClient.setLastName(clientRequestDto.getLastName());
        existingClient.setDocumentType(clientRequestDto.getDocumentType());
        existingClient.setUpdatedAt(LocalDateTime.now());
        
        Client updatedClient = clientRepository.save(existingClient);
        return mapToResponseDto(updatedClient);
    }

    @Transactional
    public ClientResponseDto patchClient(Long id, ClientPatchDto clientPatchDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        
        User user = existingClient.getUser();
        boolean userUpdated = false;
        
        // Update User fields only if they are provided
        if (clientPatchDto.getUsername() != null) {
            user.setUsername(clientPatchDto.getUsername());
            userUpdated = true;
        }
        if (clientPatchDto.getEmail() != null) {
            user.setEmail(clientPatchDto.getEmail());
            userUpdated = true;
        }
        if (clientPatchDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(clientPatchDto.getPassword()));
            userUpdated = true;
        }
        if (clientPatchDto.getPhone() != null) {
            user.setPhone(clientPatchDto.getPhone());
            userUpdated = true;
        }
        if (clientPatchDto.getStreet() != null) {
            user.setStreet(clientPatchDto.getStreet());
            userUpdated = true;
        }
        if (clientPatchDto.getCity() != null) {
            user.setCity(clientPatchDto.getCity());
            userUpdated = true;
        }
        if (clientPatchDto.getState() != null) {
            user.setState(clientPatchDto.getState());
            userUpdated = true;
        }
        
        if (userUpdated) {
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
        
        boolean clientUpdated = false;
        
        // Update Client fields only if they are provided
        if (clientPatchDto.getFirstName() != null) {
            existingClient.setFirstName(clientPatchDto.getFirstName());
            clientUpdated = true;
        }
        if (clientPatchDto.getLastName() != null) {
            existingClient.setLastName(clientPatchDto.getLastName());
            clientUpdated = true;
        }
        if (clientPatchDto.getDocumentType() != null) {
            existingClient.setDocumentType(clientPatchDto.getDocumentType());
            clientUpdated = true;
        }
        if (clientPatchDto.getDocumentNumber() != null) {
            existingClient.setDocumentNumber(clientPatchDto.getDocumentNumber());
            clientUpdated = true;
        }
        if (clientPatchDto.getBirthDate() != null) {
            existingClient.setBirthDate(clientPatchDto.getBirthDate());
            clientUpdated = true;
        }
        
        if (clientUpdated || userUpdated) {
            existingClient.setUpdatedAt(LocalDateTime.now());
            existingClient = clientRepository.save(existingClient);
        }
        
        return mapToResponseDto(existingClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        
        User user = client.getUser();
        clientRepository.delete(client);
        userRepository.delete(user);
    }

    private ClientResponseDto mapToResponseDto(Client client) {
        User user = client.getUser();
        
        return ClientResponseDto.builder()
                .id(client.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .street(user.getStreet())
                .city(user.getCity())
                .state(user.getState())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .documentType(client.getDocumentType())
                .documentNumber(client.getDocumentNumber())
                .birthDate(client.getBirthDate())
                .build();
    }
}