package com.example.demo.shared.config;

import com.example.demo.shared.service.SeedDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final SeedDataService seedDataService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Khởi động quá trình seed dữ liệu...");
        
        try {
            seedDataService.seedData();
        } catch (Exception e) {
            log.error("Lỗi khi seed dữ liệu: {}", e.getMessage(), e);
        }
    }
}
