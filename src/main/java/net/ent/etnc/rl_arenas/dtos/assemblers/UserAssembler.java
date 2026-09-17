package net.ent.etnc.rl_arenas.dtos.assemblers;

import net.ent.etnc.rl_arenas.dtos.UserRequestDto;
import net.ent.etnc.rl_arenas.dtos.UserResponseDto;
import net.ent.etnc.rl_arenas.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAssembler {

    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .pseudo(user.getPseudo())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }

    public List<UserResponseDto> toDtos(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }

    public User toEntity(UserRequestDto userDto) {
        User user = new User();
        if (userDto.getId() != null) {
            user.setId(userDto.getId());
        }
        user.setPseudo(userDto.getPseudo());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }

}