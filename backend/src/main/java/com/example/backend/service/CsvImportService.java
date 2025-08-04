package com.example.backend.service;

import com.example.backend.entity.Order;
import com.example.backend.entity.User;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.UserRepository;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class CsvImportService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CsvImportService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void importUsers(String csvPath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader("C:/Users/kumar/Downloads/archive/archive/users.csv"))) {
            String[] line;
            reader.readNext(); // Skip header line
            while ((line = reader.readNext()) != null) {
                User user = new User();
                user.setId(Integer.parseInt(line[0]));
                user.setFirstName(line[1]);
                user.setLastName(line[2]);
                user.setEmail(line[3]);
                user.setAge(Integer.parseInt(line[4]));
                user.setGender(line[5]);
                user.setState(line[6]);
                user.setStreetAddress(line[7]);
                user.setPostalCode(line[8]);
                user.setCity(line[9]);
                user.setCountry(line[10]);
                user.setLatitude(Double.parseDouble(line[11]));
                user.setLongitude(Double.parseDouble(line[12]));
                user.setTrafficSource(line[13]);
                user.setCreatedAt(parseDate(line[14]));

                userRepository.save(user);
            }
        }
    }

    @Transactional
    public void importOrders(String csvPath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader("C:/Users/kumar/Downloads/archive/archive/orders.csv"))) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                Order order = new Order();
                order.setOrderId(Integer.parseInt(line[0]));
                Integer userId = Integer.parseInt(line[1]);
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));
                order.setUser(user);

                order.setStatus(line[2]);
                order.setGender(line[3]);
                order.setCreatedAt(parseDate(line[4]));
                order.setReturnedAt(parseDate(line[5]));
                order.setShippedAt(parseDate(line[6]));
                order.setDeliveredAt(parseDate(line[7]));
                order.setNumOfItem(line[8] != null && !line[8].isEmpty() ? Integer.parseInt(line[8]) : null);

                orderRepository.save(order);
            }
        }
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        // Replace space with 'T' for valid ISO 8601 datetime format
        String isoDateStr = dateStr.replace(" ", "T");
        return OffsetDateTime.parse(isoDateStr).toLocalDateTime();
    }
}
