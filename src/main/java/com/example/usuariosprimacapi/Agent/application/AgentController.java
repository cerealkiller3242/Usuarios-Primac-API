package com.example.usuariosprimacapi.Agent.application;

import com.example.usuariosprimacapi.Agent.domain.AgentService;
import com.example.usuariosprimacapi.Agent.dto.AgentRequestDto;
import com.example.usuariosprimacapi.Agent.dto.AgentResponseDto;
import com.example.usuariosprimacapi.Agent.dto.AgentPatchDto;
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
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @GetMapping
    public ResponseEntity<Page<AgentResponseDto>> getAllAgents(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(agentService.getAllAgents(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AgentResponseDto>> searchAgents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String code,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(agentService.searchAgents(firstName, lastName, code, pageable));
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

    @PatchMapping("/{id}")
    public ResponseEntity<AgentResponseDto> patchAgent(@PathVariable Long id, @Valid @RequestBody AgentPatchDto agentPatchDto) {
        return ResponseEntity.ok(agentService.patchAgent(id, agentPatchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}
