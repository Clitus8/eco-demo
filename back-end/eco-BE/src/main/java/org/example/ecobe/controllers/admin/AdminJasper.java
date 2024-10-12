package org.example.ecobe.controllers.admin;

import org.example.ecobe.services.admin.Report.UserReport;
import org.example.ecobe.services.admin.Report.UserReportImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("api/admin")
public class AdminJasper {

    @Autowired
    private UserReportImpl userReport;

    @GetMapping("/report")
    public ResponseEntity<FileSystemResource> downloadUserReport() {
        String filePath = userReport.generateUserReport();
        File reportFile = new File(filePath);

        if (!reportFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set headers for file download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=JasperReport.pdf");

        // Return the file as a response
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new FileSystemResource(reportFile));
    }
}
