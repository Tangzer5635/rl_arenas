package net.ent.etnc.rl_arenas.controllers;

import net.ent.etnc.rl_arenas.dtos.FinishMatchRequestDto;
import net.ent.etnc.rl_arenas.models.entities.Match;
import net.ent.etnc.rl_arenas.dtos.MatchRequestDto;
import net.ent.etnc.rl_arenas.dtos.MatchResponseDto;
import net.ent.etnc.rl_arenas.dtos.assemblers.MatchAssembler;
import net.ent.etnc.rl_arenas.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchAssembler matchAssembler;

    @Autowired
    public MatchController(
            MatchService matchService,
            MatchAssembler matchAssembler
    ) {
        this.matchService = matchService;
        this.matchAssembler = matchAssembler;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('JOUEUR')")
    public ResponseEntity<Page<MatchResponseDto>> getAll(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(
                matchService.findAll(pageable)
                        .map(matchAssembler::toDto)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('JOUEUR')")
    public ResponseEntity<MatchResponseDto> getById(
            @PathVariable Long id
    ) {
        return matchService.findById(id)
                .map(match -> ResponseEntity.ok(matchAssembler.toDto(match)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ARBITRE')")
    public ResponseEntity<MatchResponseDto> post(
            @RequestBody MatchRequestDto matchDto
    ) {
        return ResponseEntity.ok(
                matchAssembler.toDto(matchService.create(matchDto))
        );
    }

    @PatchMapping("/{id}/start")
    @PreAuthorize("hasRole('ARBITRE')")
    public ResponseEntity<MatchResponseDto> start(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(matchAssembler.toDto(matchService.start(id)));
    }

    @PatchMapping("/{id}/finish")
    @PreAuthorize("hasRole('ARBITRE')")
    public ResponseEntity<MatchResponseDto> finish(
            @PathVariable Long id,
            @RequestBody FinishMatchRequestDto finishDto
    ) {
        return ResponseEntity.ok(matchAssembler.toDto(matchService.finish(id, finishDto)));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ARBITRE')")
    public ResponseEntity<MatchResponseDto> cancel(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(matchAssembler.toDto(matchService.cancel(id)));
    }
}