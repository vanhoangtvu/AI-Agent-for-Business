package com.aiagent.config;

import com.aiagent.model.entity.User;
import com.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Seeder - Khởi tạo tài khoản test
 * 
 * Tạo 3 tài khoản mẫu cho testing:
 * - ADMIN: admin / admin123
 * - BUSINESS: business / business123
 * - CUSTOMER: customer / customer123
 * 
 * @author Nguyễn Văn Hoàng
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            log.info("🌱 Starting Data Seeding...");

            // 1. Tạo ADMIN account
            if (!userRepository.existsByEmail("admin@aiagent.com")) {
                Set<String> adminRoles = new HashSet<>();
                adminRoles.add("ADMIN");

                User admin = User.builder()
                        .username("admin")
                        .email("admin@aiagent.com")
                        .password(passwordEncoder.encode("admin123"))
                        .fullName("Administrator")
                        .phoneNumber("0900000001")
                        .roles(adminRoles)
                        .active(true)
                        .emailVerified(true)
                        .build();

                userRepository.save(admin);
                log.info("✅ Created ADMIN account: admin@aiagent.com / admin123");
            } else {
                log.info("⏭️  ADMIN account already exists");
            }

            // 2. Tạo BUSINESS account
            if (!userRepository.existsByEmail("business@aiagent.com")) {
                Set<String> businessRoles = new HashSet<>();
                businessRoles.add("BUSINESS");

                User business = User.builder()
                        .username("business")
                        .email("business@aiagent.com")
                        .password(passwordEncoder.encode("business123"))
                        .fullName("Công ty TNHH ABC")
                        .phoneNumber("0900000002")
                        .roles(businessRoles)
                        .active(true)
                        .emailVerified(true)
                        .build();

                userRepository.save(business);
                log.info("✅ Created BUSINESS account: business@aiagent.com / business123");
            } else {
                log.info("⏭️  BUSINESS account already exists");
            }

            // 3. Tạo CUSTOMER account
            if (!userRepository.existsByEmail("customer@aiagent.com")) {
                Set<String> customerRoles = new HashSet<>();
                customerRoles.add("CUSTOMER");

                User customer = User.builder()
                        .username("customer")
                        .email("customer@aiagent.com")
                        .password(passwordEncoder.encode("customer123"))
                        .fullName("Khách Hàng Test")
                        .phoneNumber("0900000003")
                        .roles(customerRoles)
                        .active(true)
                        .emailVerified(true)
                        .build();

                userRepository.save(customer);
                log.info("✅ Created CUSTOMER account: customer@aiagent.com / customer123");
            } else {
                log.info("⏭️  CUSTOMER account already exists");
            }

            // 4. Tạo thêm tài khoản Sinh viên (ADMIN)
            if (!userRepository.existsByEmail("110122078@st.tvu.edu.vn")) {
                Set<String> studentRoles = new HashSet<>();
                studentRoles.add("ADMIN");

                User student = User.builder()
                        .username("hoangvan")
                        .email("110122078@st.tvu.edu.vn")
                        .password(passwordEncoder.encode("hoang123"))
                        .fullName("Nguyễn Văn Hoàng")
                        .phoneNumber("0123456789")
                        .roles(studentRoles)
                        .active(true)
                        .emailVerified(true)
                        .build();

                userRepository.save(student);
                log.info("✅ Created STUDENT ADMIN account: 110122078@st.tvu.edu.vn / hoang123");
            } else {
                log.info("⏭️  Student account already exists");
            }

            // 5. Tạo thêm 2 BUSINESS accounts
            createBusinessIfNotExists("business1@company.com", "business1", "Công ty XYZ", "0901111111");
            createBusinessIfNotExists("business2@company.com", "business2", "Công ty 123", "0902222222");

            // 6. Tạo thêm 3 CUSTOMER accounts
            createCustomerIfNotExists("customer1@gmail.com", "customer1", "Nguyễn Văn A", "0903333333");
            createCustomerIfNotExists("customer2@gmail.com", "customer2", "Trần Thị B", "0904444444");
            createCustomerIfNotExists("customer3@gmail.com", "customer3", "Lê Văn C", "0905555555");

            log.info("🎉 Data Seeding Completed!");
            log.info("========================================");
            log.info("📋 TEST ACCOUNTS:");
            log.info("========================================");
            log.info("👑 ADMIN:");
            log.info("   - admin@aiagent.com / admin123");
            log.info("   - 110122078@st.tvu.edu.vn / hoang123");
            log.info("🏢 BUSINESS:");
            log.info("   - business@aiagent.com / business123");
            log.info("   - business1@company.com / business123");
            log.info("   - business2@company.com / business123");
            log.info("👤 CUSTOMER:");
            log.info("   - customer@aiagent.com / customer123");
            log.info("   - customer1@gmail.com / customer123");
            log.info("   - customer2@gmail.com / customer123");
            log.info("   - customer3@gmail.com / customer123");
            log.info("========================================");
        };
    }

    private void createBusinessIfNotExists(String email, String username, String fullName, String phone) {
        if (!userRepository.existsByEmail(email)) {
            Set<String> roles = new HashSet<>();
            roles.add("BUSINESS");

            User user = User.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode("business123"))
                    .fullName(fullName)
                    .phoneNumber(phone)
                    .roles(roles)
                    .active(true)
                    .emailVerified(true)
                    .build();

            userRepository.save(user);
            log.info("✅ Created BUSINESS account: {} / business123", email);
        }
    }

    private void createCustomerIfNotExists(String email, String username, String fullName, String phone) {
        if (!userRepository.existsByEmail(email)) {
            Set<String> roles = new HashSet<>();
            roles.add("CUSTOMER");

            User user = User.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode("customer123"))
                    .fullName(fullName)
                    .phoneNumber(phone)
                    .roles(roles)
                    .active(true)
                    .emailVerified(true)
                    .build();

            userRepository.save(user);
            log.info("✅ Created CUSTOMER account: {} / customer123", email);
        }
    }

}

