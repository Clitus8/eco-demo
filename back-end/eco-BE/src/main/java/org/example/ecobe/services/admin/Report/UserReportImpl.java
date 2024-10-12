package org.example.ecobe.services.admin.Report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.ecobe.model.User;
import org.example.ecobe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserReportImpl {

    @Autowired
    private UserRepository userRepository;

    public String generateUserReport() {
        try {
            // Fetch user data
            List<User> users = userRepository.findAll();

            // Load the .jrxml template
            InputStream reportStream = new ClassPathResource("Jasper/JasperReport.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Create a JRBeanCollectionDataSource from the user list
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);

            // Parameters (if needed)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Quang");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Output the report as a PDF
            Path outputPath = Paths.get(System.getProperty("java.io.tmpdir"), "JasperReport.pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath.toString());

            return outputPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating report: " + e.getMessage();
        }
    }
}
