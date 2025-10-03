package com.example.usuariosprimacapi.Client.application;

import com.example.usuariosprimacapi.Client.domain.ClientService;
import com.example.usuariosprimacapi.Client.dto.ClientRequestDto;
import com.example.usuariosprimacapi.Client.dto.ClientResponseDto;
import com.example.usuariosprimacapi.Client.dto.ClientPatchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientResponseDto>> getAllClients(
            @PageableDefault(size = 20, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(clientService.getAllClients(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ClientResponseDto>> searchClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String documentNumber,
            @PageableDefault(size = 20, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(clientService.searchClients(firstName, lastName, documentNumber, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return new ResponseEntity<>(clientService.createClient(clientRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.updateClient(id, clientRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDto> patchClient(@PathVariable Long id, @Valid @RequestBody ClientPatchDto clientPatchDto) {
        return ResponseEntity.ok(clientService.patchClient(id, clientPatchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
