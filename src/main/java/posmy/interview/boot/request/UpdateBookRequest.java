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
public class UpdateBookRequest {
    private Integer id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String shelfLocation;
    private String status;
}
