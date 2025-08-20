package _2.zadanie.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
@Setter
@Getter
public class FileDto {
    private Long id;

    private String fileName;

    private String fileType;

    private LocalDateTime uploadedTime;

    private LocalDateTime changedTime;

    private Long size;
    // status 0 - SCANNING, 1 - READY
    private Integer status;

    private String comment;

    private Long userId;

    private String downloadUrl;

}
