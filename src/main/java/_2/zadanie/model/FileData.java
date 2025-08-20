package _2.zadanie.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Sorry, empty")
    private String fileName;

    @NotEmpty(message = "Sorry, empty")
    private String fileType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime uploadedTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime changedTime;

    @NotEmpty(message = "Sorry, empty")
    private Long size;
    // status 0 - SCANNING, 1 - READY
    private Integer status;

    private String comment;

    @NotEmpty(message = "Sorry, empty")
    private Long userId;

    @NotEmpty(message = "Sorry, empty")
    private File file;

    @Lob
    private byte[] content;
}
