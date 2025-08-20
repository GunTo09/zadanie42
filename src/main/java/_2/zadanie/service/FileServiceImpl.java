package _2.zadanie.service;


import _2.zadanie.dto.FileDto;
import _2.zadanie.model.FileData;
import _2.zadanie.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;


@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    @Value("${server.url}")
    private String serverUrl;

    @Override
    public void addFile(FileData file, Long userId, String comment){
        FileData fileData = new FileData();
        fileData.setFileName(file.getFileName());
        fileData.setFileType(file.getFileType());
        fileData.setSize(file.getSize());
        fileData.setStatus(0);
        fileData.setUserId(userId);
        fileData.setComment(comment);
        fileData.setFile(file.getFile());
        fileData.setContent(file.getContent());
        fileRepository.save(fileData);

        scanFile(fileData.getId());
    }

    @Override
    public void deleteFileById(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public String readFile(Long id) throws IOException {
        File file = new File(fileRepository.findById(id).get().getFile().toURI());
        FileInputStream input = new FileInputStream(file);
        int i;
        StringBuilder out = new StringBuilder();
        while ((i= input.read()) != -1){
            out.append((char) i);
        }
        return out.toString();
    }

    public Optional<FileData> getFileData(Long id){
        return  fileRepository.findById(id);
    }

    public List<String> getListOfNames(){
        return fileRepository.findAll().stream().map(FileData::getFileName).toList();
    }

    public CompletableFuture<Void> scanFile(Long id){
        return CompletableFuture.runAsync(() -> {
            try {

                FileData file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("Файл не найден"));

                Thread.sleep(15000);

                file.setStatus(1);
                fileRepository.save(file);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        });
    }

    public List<FileDto> getFilteredList(String name, LocalDateTime dateFrom, LocalDateTime dateTo, List<String> types){
        List<FileData> list = fileRepository.findAll();

        return list.stream()
                .filter(file -> name == null || file.getFileName().equalsIgnoreCase(name))
                .filter(file -> dateFrom == null || !file.getUploadedTime().isBefore(dateFrom))
                .filter(file -> dateTo == null || !file.getChangedTime().isAfter(dateTo))
                .filter(file -> types == null || types.isEmpty() || types.contains(file.getFileType()))
                .map(file -> {
                    FileDto dto = new FileDto();
                    dto.setId(file.getId());
                    dto.setFileName(file.getFileName());
                    dto.setFileType(file.getFileType());
                    dto.setSize(file.getSize());
                    dto.setStatus(file.getStatus());
                    dto.setUserId(file.getUserId());
                    dto.setUploadedTime(file.getUploadedTime());
                    dto.setChangedTime(file.getChangedTime());
                    dto.setComment(file.getComment());
                    dto.setDownloadUrl(serverUrl + "/files/download/" + file.getId());
                    return dto;
                }).toList();
    }

}
