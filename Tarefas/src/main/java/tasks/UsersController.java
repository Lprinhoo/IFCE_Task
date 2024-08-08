package Task;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("users")
public class UsersController {

    private final JdbcTemplate database;

    public UsersController(JdbcTemplate database) {
        this.database = database;
    }

    @PostMapping
    public CreateUserOutput post(@RequestBody CreateUserInput in) {

        final var user_id = database.update("INSERT INTO users(name, email, password) VALUES (?,?,?)", in.name(), in.email(), SHA256.execute(in.password()));

        return new CreateUserOutput(
                user_id,
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
            Integer user_id,
            String name,
            String email,
            String password,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
    }

    @GetMapping("{user_id}")
    public GetUserByIdOutput getById(@PathVariable Long user_id) {

        String password = "123456";
        String cryptoPassword = SHA256.execute(password);
        return new GetUserByIdOutput(
                user_id,
                "John Doe",
                "john@example.com",
                cryptoPassword,
                LocalDateTime.of(2024, 7, 17, 0, 0, 0),
                LocalDateTime.of(2024, 7, 17, 0, 0, 0)
        );
    }

    public record GetUserByIdOutput(
            Long user_id,
            String name,
            String email,
            String password,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
    }

    @PutMapping("{user_id}")
    public UpdateUserOutput update(@PathVariable Long user_id, @RequestBody UpdateUserInput in) {
        return new UpdateUserOutput(
                user_id,
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
            Long user_id,
            String name,
            String email,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ) {
    }

    @DeleteMapping("{user_id}")
    public UsersController.DeleteUserOutput delete(@PathVariable Long user_id) {

        return new UsersController.DeleteUserOutput(
                "User deleted successfully"
        );

    }

    public record DeleteUserOutput(
            String message
    ) {
    }

}
