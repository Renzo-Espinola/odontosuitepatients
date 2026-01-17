package com.odontosuitepatients.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /** Guarda la foto y devuelve la URL o path accesible */
    String storePatientPhoto(Long patientId, MultipartFile file);

    /** Recupera el archivo */
    Resource loadAsResource(String filename);

    /** Borra si hace falta */
    void delete(String filename);

    String storeClinicalAttachment(Long patientId, Long entryId, MultipartFile file);
}
