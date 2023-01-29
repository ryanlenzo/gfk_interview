package posmy.interview.boot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import posmy.interview.boot._enum.BookStatus;
import posmy.interview.boot._enum.Role;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="book")
public class Book{

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    @Column(unique=true)
    private String shelfLocation;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    private String createdBy;
    private Date createdDate;
    private String lastBorrowedBy;
    private Date lastBorrowedDate;
    private Date lastReturnedDate;
}
