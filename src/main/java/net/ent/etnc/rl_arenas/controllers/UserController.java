package net.ent.etnc.rl_arenas.controllers;

import net.ent.etnc.rl_arenas.models.entities.User;
import net.ent.etnc.rl_arenas.dtos.UserRequestDto;
import net.ent.etnc.rl_arenas.dtos.UserResponseDto;
import net.ent.etnc.rl_arenas.dtos.assemblers.UserAssembler;
import net.ent.etnc.rl_arenas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @Autowired
    public UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('JOUEUR')")
    public ResponseEntity<Page<UserResponseDto>> getAll(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(userService.findAll(pageable)
                .map(userAssembler::toDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('JOUEUR')")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userAssembler.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> post(@RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(
                userAssembler.toDto(userService.create(userAssembler.toEntity(userDto))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> put(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        if (!userService.existsById(id)) return ResponseEntity.notFound().build();
        User user = userAssembler.toEntity(userRequestDto);
        user.setId(id);
        return ResponseEntity.ok(userAssembler.toDto(userService.update(user)));
    }
}