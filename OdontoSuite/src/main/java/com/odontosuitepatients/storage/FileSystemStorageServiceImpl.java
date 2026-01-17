package com.odontosuitepatients.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileSystemStorageServiceImpl implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    @Override
    public String storePatientPhoto(final Long patientId, final MultipartFile file) {
        try {
            final Path root = Paths.get(storageLocation);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            final String filename = "patient_" + patientId + "_" + file.getOriginalFilename();
            final Path destination = root.resolve(filename);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Error storing file", e);
        }
    }

    @Override
    public Resource loadAsResource(final String filename) {
        try {
            final Path file = Paths.get(storageLocation).resolve(filename).normalize();
            final Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new RuntimeException("Could not read file: " + filename);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error loading file", e);
        }
    }

    @Override
    public void delete(final String filename) {
        try {
            final Path file = Paths.get(storageLocation).resolve(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file", e);
        }
    }

    @Override
    public String storeClinicalAttachment(
            final Long patientId,
            final Long entryId,
            final MultipartFile file) {

        try {
            final Path root = Paths.get(storageLocation, "patients", patientId.toString(), "history");
            Files.createDirectories(root);

            final String filename =
                    "entry_" + entryId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            final Path destination = root.resolve(filename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return Paths.get("patients", patientId.toString(), "history", filename).toString();

        } catch (IOException e) {
            throw new RuntimeException("Error storing clinical attachment", e);
        }
    }


}
