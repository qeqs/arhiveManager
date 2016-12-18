package service;

import dao.HibernateUtil;
import entities.ArhiveEntity;
import entities.EntryEntity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Arhivator {

    private Arhivator(){

    }

    public static void createArhive(String path,String[] entries) throws IOException {

        Files.deleteIfExists(Paths.get(path));

        int length = 0;
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path))) {

            for (int i = 0; i < entries.length; i++) {

                zipOutputStream.putNextEntry(new ZipEntry(entries[i].substring(entries[i].lastIndexOf("\\")+1)));
                try (FileInputStream entryReadStream = new FileInputStream(entries[i])) {

                    length += entryReadStream.available();
                    byte[] buff = new byte[entryReadStream.available()];
                    entryReadStream.read(buff);
                    zipOutputStream.write(buff);
                }

                zipOutputStream.closeEntry();
            }
            ArhiveEntity arhiveEntity = new ArhiveEntity();
            arhiveEntity.setDate(new Date(new java.util.Date().getTime()));
            arhiveEntity.setName(path.substring(path.lastIndexOf("\\")+1));
            arhiveEntity.setPath(path);
            arhiveEntity.setSize(length);
            arhiveEntity.setId(arhiveEntity.hashCode());

            HibernateUtil.getArhivesDAO().insert(arhiveEntity);
            for (String str : entries) {

                EntryEntity entryEntity = new EntryEntity();
                entryEntity.setName(str.substring(str.lastIndexOf("\\")+1));
                entryEntity.setArhive(arhiveEntity);
                entryEntity.setId(entryEntity.hashCode());

                HibernateUtil.getEntryDAO().insert(entryEntity);

            }
        }
    }

    public static void deleteArhive(ArhiveEntity arhiveEntity) throws IOException {
        Files.deleteIfExists(Paths.get(arhiveEntity.getPath()));
        HibernateUtil.getArhivesDAO().delete(arhiveEntity);
    }
}
