package tasks;

import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.lang.System.in;

@RestController
@RequestMapping("users")
public class UserController {


    @PostMapping
    public CreateUserOutput post(@RequestBody CreateUserInput in) {
        return new CreateUserOutput(
                1L,
                in.name(),
                in.email(),
                SHA256.execute(in.password()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public record CreateUserInput(
            String name,
            String email,
            String password
    ) {
    }

    public record CreateUserOutput(
            Long id,
            String name,
            String email,
            String password,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
    }

    @GetMapping("{id}")
    public GetUserByIdOutput getById(@PathVariable Long id) {

        String password = "123456";
        String cryptoPassword = SHA256.execute(password);
        return new GetUserByIdOutput(
                id,
                "John Doe",
                "john@example.com",
                cryptoPassword,
                LocalDateTime.of(2024, 7, 17, 0, 0, 0),
                LocalDateTime.of(2024, 7, 17, 0, 0, 0)
        );
    }

    public record GetUserByIdOutput(
            Long id,
            String name,
            String email,
            String password,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
    }

    @PutMapping("{id}")
    public UpdateUserOutput update(@PathVariable Long id, @RequestBody UpdateUserInput in) {
        return new UpdateUserOutput(
                id,
                in.name(),
                in.email(),
                LocalDateTime.of(2024, 7, 17, 0, 0, 0),
                LocalDateTime.of(2024, 7, 17, 0, 0, 0)
        );
    }

    public record UpdateUserInput(
            String name,
            String email
    ) {
    }

    public record UpdateUserOutput(
            Long id,
            String name,
            String email,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
    }

    @DeleteMapping("{id}")
    public UserController.DeleteUserOutput delete(@PathVariable Long id) {

        return new UserController.DeleteUserOutput(
                "User deleted successfully"
        );

    }

    public record DeleteUserOutput(
            String message
    ) {
    }

}
