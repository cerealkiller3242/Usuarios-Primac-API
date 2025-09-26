package com.example.usuariosprimacapi.Client.domain;

import com.example.usuariosprimacapi.Client.dto.ClientRequestDto;
import com.example.usuariosprimacapi.Client.dto.ClientResponseDto;
import com.example.usuariosprimacapi.Client.infrastructure.ClientRepository;
import com.example.usuariosprimacapi.User.domain.User;
import com.example.usuariosprimacapi.User.domain.rol;
import com.example.usuariosprimacapi.User.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public List<ClientResponseDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        return mapToResponseDto(client);
    }

    @Transactional
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        // Create and save User first
        User user = new User();
        user.setUsername(clientRequestDto.getUsername());
        user.setEmail(clientRequestDto.getEmail());
        user.setPassword(clientRequestDto.getPassword());
        user.setPhone(clientRequestDto.getPhone());
        user.setStreet(clientRequestDto.getStreet());
        user.setCity(clientRequestDto.getCity());
        user.setState(clientRequestDto.getState());
        user.setRole(rol.USER); // Always set role to USER for clients
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        
        // Create Client with reference to saved User
        Client client = new Client();
        client.setUser(savedUser);
        client.setFirstName(clientRequestDto.getFirstName());
        client.setLastName(clientRequestDto.getLastName());
        client.setDocumentType(clientRequestDto.getDocumentType());
        client.setDocumentNumber(clientRequestDto.getDocumentNumber());
        client.setBirthDate(clientRequestDto.getBirthDate());
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        
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
        user.setPassword(clientRequestDto.getPassword());
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
        
        // Note: documentNumber and birthDate are not updatable according to entity definition
        
        Client updatedClient = clientRepository.save(existingClient);
        return mapToResponseDto(updatedClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        
        // Delete the client first
        clientRepository.deleteById(id);
        
        // Then delete the associated user
        userRepository.delete(client.getUser());
    }

    private ClientResponseDto mapToResponseDto(Client client) {
        User user = client.getUser();
        
        return ClientResponseDto.builder()
                .id(client.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
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