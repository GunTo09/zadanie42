package _2.zadanie.service;

import _2.zadanie.dto.FileDto;
import _2.zadanie.model.FileData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface FileService {

    void addFile(FileData file, Long userId, String comment);

    void deleteFileById(Long id);

    String readFile(Long id) throws IOException;

    Optional<FileData> getFileData(Long id);

    List<String> getListOfNames();

    CompletableFuture<Void> scanFile(Long id);

    List<FileDto> getFilteredList (String name, LocalDateTime dateFrom, LocalDateTime dateTo, List<String> types);

}
