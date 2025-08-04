package com.example.backend;

import com.example.backend.service.CsvImportService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final CsvImportService csvImportService;

    public DataLoader(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @PostConstruct
    public void loadData() {
        try {
            // Pass null or actual path strings are ignored in your service, you have hardcoded paths inside
            csvImportService.importUsers(null);
            csvImportService.importOrders(null);

            System.out.println("CSV data loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
