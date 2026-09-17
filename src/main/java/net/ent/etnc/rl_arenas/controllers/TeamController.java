package net.ent.etnc.rl_arenas.controllers;

import net.ent.etnc.rl_arenas.dtos.TeamRequestDto;
import net.ent.etnc.rl_arenas.dtos.TeamResponseDto;
import net.ent.etnc.rl_arenas.dtos.TeamUpdateRequestDto;
import net.ent.etnc.rl_arenas.dtos.assemblers.TeamAssembler;
import net.ent.etnc.rl_arenas.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamAssembler teamAssembler;

    @Autowired
    public TeamController(
            TeamService teamService,
            TeamAssembler teamAssembler) {

        this.teamService = teamService;
        this.teamAssembler = teamAssembler;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('JOUEUR')")
    public ResponseEntity<Page<TeamResponseDto>> getAll(
            @PageableDefault(size = 20) Pageable pageable) {

        return ResponseEntity.ok(
                teamService.findAll(pageable).map(teamAssembler::toDto)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('JOUEUR')")
    public ResponseEntity<TeamResponseDto> getById(
            @PathVariable Long id) {

        return teamService.findById(id)
                .map(team -> ResponseEntity.ok(teamAssembler.toDto(team)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('CAPITAINE')")
    public ResponseEntity<TeamResponseDto> post(
            @RequestBody TeamRequestDto teamDto) {

        return ResponseEntity.ok(teamAssembler.toDto(teamService.create(teamDto)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CAPITAINE')")
    public ResponseEntity<TeamResponseDto> put(@PathVariable Long id, @RequestBody TeamUpdateRequestDto teamDto) {
        return ResponseEntity.ok(teamAssembler.toDto(teamService.update(id, teamDto)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CAPITAINE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}