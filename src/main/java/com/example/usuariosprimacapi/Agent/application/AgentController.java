package com.example.usuariosprimacapi.Agent.application;

import com.example.usuariosprimacapi.Agent.domain.AgentService;
import com.example.usuariosprimacapi.Agent.dto.AgentRequestDto;
import com.example.usuariosprimacapi.Agent.dto.AgentResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @GetMapping
    public ResponseEntity<List<AgentResponseDto>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentResponseDto> getAgentById(@PathVariable Long id) {
        return ResponseEntity.ok(agentService.getAgentById(id));
    }

    @PostMapping
    public ResponseEntity<AgentResponseDto> createAgent(@Valid @RequestBody AgentRequestDto agentRequestDto) {
        return new ResponseEntity<>(agentService.createAgent(agentRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentResponseDto> updateAgent(@PathVariable Long id, @Valid @RequestBody AgentRequestDto agentRequestDto) {
        return ResponseEntity.ok(agentService.updateAgent(id, agentRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}
