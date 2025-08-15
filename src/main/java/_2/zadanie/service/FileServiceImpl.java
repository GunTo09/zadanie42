package _2.zadanie.service;


import _2.zadanie.model.FileData;
import _2.zadanie.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void addFile(FileData file, Long userId, String comment){
        FileData fileData = new FileData();
        fileData.setFileName(file.getFileName());
        fileData.setFileType(file.getFileType());
        fileData.setSize(file.getSize());
        fileData.setStatus(1);
        fileData.setUserId(userId);
        fileData.setComment(comment);
        fileData.setFile(file.getFile());
        fileRepository.save(file);
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

}
