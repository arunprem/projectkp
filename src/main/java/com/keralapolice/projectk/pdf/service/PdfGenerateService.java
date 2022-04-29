package com.keralapolice.projectk.pdf.service;

import java.io.IOException;
import java.util.Map;

public interface PdfGenerateService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws IOException;
}
