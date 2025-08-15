package _2.zadanie.repository;

import _2.zadanie.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileData, Long> {
}
