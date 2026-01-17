package com.odontosuitepatients.exception;

public class DuplicateDocumentNumberException extends RuntimeException {
    public DuplicateDocumentNumberException(String document) {
        super("Document number already exists: " + document);
    }
}
