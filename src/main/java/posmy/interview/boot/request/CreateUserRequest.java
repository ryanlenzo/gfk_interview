package posmy.interview.boot.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String role;

}
