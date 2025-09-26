package com.example.usuariosprimacapi.Agent.domain;

import com.example.usuariosprimacapi.Agent.dto.AgentRequestDto;
import com.example.usuariosprimacapi.Agent.dto.AgentResponseDto;
import com.example.usuariosprimacapi.Agent.infrastructure.AgentRepository;
import com.example.usuariosprimacapi.User.domain.rol;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;

    public List<AgentResponseDto> getAllAgents() {
        return agentRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public AgentResponseDto getAgentById(Long id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));
        return mapToResponseDto(agent);
    }

    public AgentResponseDto createAgent(AgentRequestDto agentRequestDto) {
        Agent agent = mapToEntity(agentRequestDto);
        agent.setCreatedAt(LocalDateTime.now());
        agent.setUpdatedAt(LocalDateTime.now());
        agent.setRole(rol.AGENT);
        Agent savedAgent = agentRepository.save(agent);
        return mapToResponseDto(savedAgent);
    }

    public AgentResponseDto updateAgent(Long id, AgentRequestDto agentRequestDto) {
        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + id));

        // Update User fields
        existingAgent.setUsername(agentRequestDto.getUsername());
        existingAgent.setEmail(agentRequestDto.getEmail());
        existingAgent.setPassword(agentRequestDto.getPassword());
        existingAgent.setPhone(agentRequestDto.getPhone());
        existingAgent.setStreet(agentRequestDto.getStreet());
        existingAgent.setCity(agentRequestDto.getCity());
        existingAgent.setState(agentRequestDto.getState());

        // Update Agent fields
        existingAgent.setCode(agentRequestDto.getCode());
        existingAgent.setFirstName(agentRequestDto.getFirstName());
        existingAgent.setLastName(agentRequestDto.getLastName());
        existingAgent.setActive(agentRequestDto.getIsActive());

        existingAgent.setUpdatedAt(LocalDateTime.now());

        Agent updatedAgent = agentRepository.save(existingAgent);
        return mapToResponseDto(updatedAgent);
    }

    public void deleteAgent(Long id) {
        if (!agentRepository.existsById(id)) {
            throw new EntityNotFoundException("Agent not found with id: " + id);
        }
        agentRepository.deleteById(id);
    }

    private Agent mapToEntity(AgentRequestDto agentRequestDto) {
        Agent agent = new Agent();

        // Set User properties
        agent.setUsername(agentRequestDto.getUsername());
        agent.setEmail(agentRequestDto.getEmail());
        agent.setPassword(agentRequestDto.getPassword());
        agent.setPhone(agentRequestDto.getPhone());
        agent.setStreet(agentRequestDto.getStreet());
        agent.setCity(agentRequestDto.getCity());
        agent.setState(agentRequestDto.getState());
        agent.setRole(rol.AGENT);

        // Set Agent properties
        agent.setCode(agentRequestDto.getCode());
        agent.setFirstName(agentRequestDto.getFirstName());
        agent.setLastName(agentRequestDto.getLastName());
        agent.setActive(agentRequestDto.getIsActive());

        return agent;
    }

    private AgentResponseDto mapToResponseDto(Agent agent) {
        return AgentResponseDto.builder()
                .id(agent.getId())
                .username(agent.getUsername())
                .email(agent.getEmail())
                .role(agent.getRole())
                .phone(agent.getPhone())
                .createdAt(agent.getCreatedAt())
                .updatedAt(agent.getUpdatedAt())
                .street(agent.getStreet())
                .city(agent.getCity())
                .state(agent.getState())
                .code(agent.getCode())
                .firstName(agent.getFirstName())
                .lastName(agent.getLastName())
                .isActive(agent.isActive())
                .build();
    }
}
