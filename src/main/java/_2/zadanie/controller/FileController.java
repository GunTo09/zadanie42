package _2.zadanie.controller;

import _2.zadanie.dto.ApiResponse;
import _2.zadanie.dto.FileDto;
import _2.zadanie.model.FileData;
import _2.zadanie.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @Value("${server.url}")
    private String serverUrl;

    @PostMapping
    ResponseEntity<ApiResponse> addFile(@RequestParam @Valid FileData file, @RequestParam Long userId, @RequestParam String comment){
        fileService.addFile(file, userId, comment);
        return ResponseEntity.ok(ApiResponse.ok(file));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> deleteFileById(@PathVariable Long id){
        fileService.deleteFileById(id);
        return ResponseEntity.ok(ApiResponse.ok("File deleted"));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse> getFileData(@PathVariable Long id){
        Optional<FileData> file = fileService.getFileData(id);
        return ResponseEntity.ok(ApiResponse.ok(file));
    }

    @GetMapping("/read/{id}")
    ResponseEntity<ApiResponse> readFile(@PathVariable Long id) throws IOException {
        String file = fileService.readFile(id);
        return ResponseEntity.ok(ApiResponse.ok(file));
    }


    @GetMapping("/list")
    ResponseEntity<ApiResponse> getListOfNames(){
        List<String> listOfNames = fileService.getListOfNames();
        return ResponseEntity.ok(ApiResponse.ok(listOfNames));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException{
        FileData fileData = fileService.getFileData(id).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileData.getFileName())
                .contentType(MediaType.parseMediaType(fileData.getFileType()))
                .body(fileData.getContent());
    }

    @GetMapping("/download/link/{id}")
    public ResponseEntity<ApiResponse> downloadLink(@PathVariable Long id){
        String fileUrl = serverUrl + "/download/" + id;
        return ResponseEntity.ok(ApiResponse.ok(fileUrl));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getFiles(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam(required = false) List<String> types
    ) {
        List<FileDto> list = fileService.getFilteredList(name, dateFrom, dateTo, types);
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

}
