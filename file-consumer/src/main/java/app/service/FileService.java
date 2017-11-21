package app.service;

import entity.DVD;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Vlad on 18-Nov-17.
 */
@Service
public class FileService {

    public void createFile(DVD dvd) throws IOException {
        String path = dvd.getTitle() + "_" + dvd.getYear() + ".txt";
        FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(dvd.toString().getBytes());
        outputStream.close();
    }
}
