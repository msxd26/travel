package pe.jsaire.springtravel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.springtravel.services.abstract_service.IUserService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;


    @Operation(summary = "Enabled or Disabled User")
    @GetMapping("/enabled-or-disabled")
    public ResponseEntity<Map<String, Boolean>> enabledOrDisabled(@RequestParam String username) {
        return ResponseEntity.ok(this.userService.enabled(username));
    }


    @Operation(summary = "Add role ")
    @PatchMapping("/add-role")
    public ResponseEntity<Map<String, List<String>>> addRole(@RequestParam String username,
                                                             @RequestParam String role) {
        return ResponseEntity.ok(this.userService.addRole(username, role));
    }

    @Operation(summary = "Add role ")
    @PatchMapping("/remove-role")
    public ResponseEntity<Map<String, List<String>>> removeRole(@RequestParam String username,
                                                                @RequestParam String role) {
        return ResponseEntity.ok(this.userService.removeRole(username, role));
    }
}
