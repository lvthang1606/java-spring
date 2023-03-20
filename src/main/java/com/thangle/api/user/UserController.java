package com.thangle.api.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.thangle.domain.user.UserService;
import static com.thangle.api.user.UserResponseDTOMapper.*;
import static com.thangle.api.user.UserUpdateDTOMapper.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return toUserResponseDTOs(userService.findAll());
    }

    @Operation(summary = "Find a specific user by id")
    @GetMapping("{id}")
    public UserResponseDTO findById(final @PathVariable UUID id) {
        return toUserResponseDTO(userService.findById(id));
    }

    @Operation(summary = "Create a specific user")
    @PostMapping
    public UserResponseDTO create(final @RequestBody UserUpdateDTO userUpdateDTO) {
        return toUserResponseDTO(userService.create(toUser(userUpdateDTO)));
    }

    @Operation(summary = "Update a specific user")
    @PutMapping("{id}")
    public UserResponseDTO update(final @PathVariable UUID id, final @RequestBody UserUpdateDTO userUpdateDTO) {
        return toUserResponseDTO(userService.update(id, toUser(userUpdateDTO)));
    }

    @Operation(summary = "Delete a specific user")
    @DeleteMapping("{id}")
    public void delete(final @PathVariable UUID id) {
        userService.delete(id);
    }
}
