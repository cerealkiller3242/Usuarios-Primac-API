package com.example.usuariosprimacapi.Agent.domain;

import com.example.usuariosprimacapi.Agent.dto.AgentRequestDto;
import com.example.usuariosprimacapi.Agent.dto.AgentResponseDto;
import com.example.usuariosprimacapi.Agent.dto.AgentPatchDto;
import com.example.usuariosprimacapi.Agent.infrastructure.AgentRepository;
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
public class AgentService {
    private final AgentRepository agentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<AgentResponseDto> getAllAgents(Pageable pageable) {
        return agentRepository.findAllWithUser(pageable)
                .map(this::mapToResponseDto);
    }
    
    public Page<AgentResponseDto> searchAgents(String firstName, String lastName, String code, Pageable pageable) {
        return agentRepository.findBySearchCriteria(firstName, lastName, code, pageable)
                .map(this::mapToResponseDto);
    }

    // Método legacy para compatibilidad (no recomendado para 20k registros)
    public List<AgentResponseDto> getAllAgents() {
        return agentRepository.findAllWithUser().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public AgentResponseDto getAgentById(Long id) {
        Agent agent = agentRepository.findByIdWithUser(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        return mapToResponseDto(agent);
    }

    @Transactional
    public AgentResponseDto createAgent(AgentRequestDto agentRequestDto) {
        // Create and save User first
        User user = User.builder()
                .username(agentRequestDto.getUsername())
                .email(agentRequestDto.getEmail())
                .password(passwordEncoder.encode(agentRequestDto.getPassword()))
                .phone(agentRequestDto.getPhone())
                .street(agentRequestDto.getStreet())
                .city(agentRequestDto.getCity())
                .state(agentRequestDto.getState())
                .role(rol.AGENT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        User savedUser = userRepository.save(user);
        
        // Create Agent with reference to saved User
        Agent agent = Agent.builder()
                .user(savedUser)
                .code(agentRequestDto.getCode())
                .firstName(agentRequestDto.getFirstName())
                .lastName(agentRequestDto.getLastName())
                .isActive(agentRequestDto.getIsActive())
                .build();
                
        Agent savedAgent = agentRepository.save(agent);
        return mapToResponseDto(savedAgent);
    }

    @Transactional
    public AgentResponseDto updateAgent(Long id, AgentRequestDto agentRequestDto) {
        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));

        // Update User fields
        User user = existingAgent.getUser();
        user.setUsername(agentRequestDto.getUsername());
        user.setEmail(agentRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(agentRequestDto.getPassword()));
        user.setPhone(agentRequestDto.getPhone());
        user.setStreet(agentRequestDto.getStreet());
        user.setCity(agentRequestDto.getCity());
        user.setState(agentRequestDto.getState());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepository.save(user);

        // Update Agent fields
        existingAgent.setCode(agentRequestDto.getCode());
        existingAgent.setFirstName(agentRequestDto.getFirstName());
        existingAgent.setLastName(agentRequestDto.getLastName());
        existingAgent.setActive(agentRequestDto.getIsActive());

        Agent updatedAgent = agentRepository.save(existingAgent);
        return mapToResponseDto(updatedAgent);
    }

    @Transactional
    public AgentResponseDto patchAgent(Long id, AgentPatchDto agentPatchDto) {
        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));

        User user = existingAgent.getUser();
        boolean userUpdated = false;
        
        // Update User fields only if they are provided
        if (agentPatchDto.getUsername() != null) {
            user.setUsername(agentPatchDto.getUsername());
            userUpdated = true;
        }
        if (agentPatchDto.getEmail() != null) {
            user.setEmail(agentPatchDto.getEmail());
            userUpdated = true;
        }
        if (agentPatchDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(agentPatchDto.getPassword()));
            userUpdated = true;
        }
        if (agentPatchDto.getPhone() != null) {
            user.setPhone(agentPatchDto.getPhone());
            userUpdated = true;
        }
        if (agentPatchDto.getStreet() != null) {
            user.setStreet(agentPatchDto.getStreet());
            userUpdated = true;
        }
        if (agentPatchDto.getCity() != null) {
            user.setCity(agentPatchDto.getCity());
            userUpdated = true;
        }
        if (agentPatchDto.getState() != null) {
            user.setState(agentPatchDto.getState());
            userUpdated = true;
        }
        
        if (userUpdated) {
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
        
        boolean agentUpdated = false;
        
        // Update Agent fields only if they are provided
        if (agentPatchDto.getCode() != null) {
            existingAgent.setCode(agentPatchDto.getCode());
            agentUpdated = true;
        }
        if (agentPatchDto.getFirstName() != null) {
            existingAgent.setFirstName(agentPatchDto.getFirstName());
            agentUpdated = true;
        }
        if (agentPatchDto.getLastName() != null) {
            existingAgent.setLastName(agentPatchDto.getLastName());
            agentUpdated = true;
        }
        if (agentPatchDto.getIsActive() != null) {
            existingAgent.setActive(agentPatchDto.getIsActive());
            agentUpdated = true;
        }
        
        if (agentUpdated) {
            existingAgent = agentRepository.save(existingAgent);
        }
        
        return mapToResponseDto(existingAgent);
    }

    @Transactional
    public void deleteAgent(Long id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        
        User user = agent.getUser();
        agentRepository.delete(agent);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    private AgentResponseDto mapToResponseDto(Agent agent) {
        User user = agent.getUser();
        
        return AgentResponseDto.builder()
                .id(agent.getId())
                .username(user != null ? user.getUsername() : null)
                .email(user != null ? user.getEmail() : null)
                .role(user != null ? user.getRole() : null)
                .phone(user != null ? user.getPhone() : null)
                .createdAt(user != null ? user.getCreatedAt() : null)
                .updatedAt(user != null ? user.getUpdatedAt() : null)
                .street(user != null ? user.getStreet() : null)
                .city(user != null ? user.getCity() : null)
                .state(user != null ? user.getState() : null)
                .code(agent.getCode())
                .firstName(agent.getFirstName())
                .lastName(agent.getLastName())
                .isActive(agent.isActive())
                .build();
    }
}
