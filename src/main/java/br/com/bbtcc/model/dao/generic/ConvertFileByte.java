package br.com.bbtcc.model.dao.generic;

import java.io.File;
import java.nio.file.Files;

public class ConvertFileByte {


    public byte[] convertFileToByte(File file) {
        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }return fileBytes;
    }
}
