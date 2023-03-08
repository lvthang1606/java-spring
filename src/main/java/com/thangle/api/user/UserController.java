package com.thangle.api.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.thangle.domain.user.User;
import com.thangle.domain.user.UserService;
import static com.thangle.api.user.UserDTOMapper.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserDTO> findAll() {
        return toUserDTOs(userService.findAll());
    }

    @Operation(summary = "Find a specific user by id")
    @GetMapping("{id}")
    public UserDTO findUserById(final @PathVariable UUID id) {
        return toUserDTO(userService.findUserById(id));
    }

    @Operation(summary = "Create a specific user")
    @PostMapping
    public UserDTO createUser(final @RequestBody User user) {
        return toUserDTO(userService.createUser(user));
    }

    @Operation(summary = "Update a specific user")
    @PutMapping("{id}")
    public UserDTO updateUser(final @PathVariable UUID id, final @RequestBody UserDTO userDTO) {
        return toUserDTO(userService.updateUser(id, toUser(userDTO)));
    }

    @Operation(summary = "Delete a specific user")
    @DeleteMapping("{id}")
    public void deleteUser(final @PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
