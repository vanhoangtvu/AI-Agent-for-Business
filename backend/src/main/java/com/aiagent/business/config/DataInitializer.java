package com.aiagent.business.config;

import com.aiagent.business.model.Business;
import com.aiagent.business.model.Customer;
import com.aiagent.business.model.User;
import com.aiagent.business.repository.BusinessRepository;
import com.aiagent.business.repository.CustomerRepository;
import com.aiagent.business.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Data Initializer - Tạo tài khoản test khi khởi động
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("=== Starting Data Initialization ===");
        
        // Kiểm tra xem đã có tài khoản chưa
        if (userRepository.count() > 0) {
            log.info("Database already has users. Skipping initialization.");
            return;
        }
        
        try {
            createTestAccounts();
            log.info("=== Data Initialization Completed Successfully ===");
        } catch (Exception e) {
            log.error("Error during data initialization", e);
        }
    }

    private void createTestAccounts() {
        // 1. ADMIN Account
        User admin = new User();
        admin.setEmail("hv@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("HV - Admin");
        admin.setRole(User.UserRole.ADMIN);
        admin.setIsActive(true);
        admin.setIsVerified(true);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        userRepository.save(admin);
        log.info("✅ Created ADMIN account: hv@gmail.com / admin123");

        // 2. BUSINESS Account
        User businessOwner = new User();
        businessOwner.setEmail("hv.business@gmail.com");
        businessOwner.setPassword(passwordEncoder.encode("business123"));
        businessOwner.setFullName("HV - Business Owner");
        businessOwner.setRole(User.UserRole.BUSINESS);
        businessOwner.setIsActive(true);
        businessOwner.setIsVerified(true);
        businessOwner.setCreatedAt(LocalDateTime.now());
        businessOwner.setUpdatedAt(LocalDateTime.now());
        businessOwner = userRepository.save(businessOwner);
        
        // Tạo Business entity cho BUSINESS user
        Business business = new Business();
        business.setOwnerId(businessOwner.getId());
        business.setBusinessName("Công ty Test HV");
        business.setSlug("cong-ty-test-hv");
        business.setPhone("0901234567");
        business.setEmail("hv.business@gmail.com");
        business.setAddress("123 Test Street");
        business.setCity("Ho Chi Minh");
        business.setType(Business.BusinessType.RETAIL);
        business.setStatus(Business.BusinessStatus.ACTIVE);
        business.setSubscriptionPlan(Business.SubscriptionPlan.FREE);
        business.setCreatedAt(LocalDateTime.now());
        business.setUpdatedAt(LocalDateTime.now());
        businessRepository.save(business);
        log.info("✅ Created BUSINESS account: hv.business@gmail.com / business123");

        // 3. CUSTOMER Account
        User customerUser = new User();
        customerUser.setEmail("hv.customer@gmail.com");
        customerUser.setPassword(passwordEncoder.encode("customer123"));
        customerUser.setFullName("HV - Customer");
        customerUser.setRole(User.UserRole.CUSTOMER);
        customerUser.setIsActive(true);
        customerUser.setIsVerified(true);
        customerUser.setCreatedAt(LocalDateTime.now());
        customerUser.setUpdatedAt(LocalDateTime.now());
        customerUser = userRepository.save(customerUser);
        
        // Tạo Customer entity cho CUSTOMER user
        Customer customer = new Customer();
        customer.setUserId(customerUser.getId());
        customer.setBusinessId(business.getId()); // Link to business đã tạo
        customer.setFullName("HV - Customer");
        customer.setEmail("hv.customer@gmail.com");
        customer.setPhone("0912345678");
        customer.setSegment(Customer.CustomerSegment.NEW);
        customer.setSource(Customer.CustomerSource.WEBSITE);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        log.info("✅ Created CUSTOMER account: hv.customer@gmail.com / customer123");

        // Log summary
        log.info("");
        log.info("╔════════════════════════════════════════════════════════════╗");
        log.info("║           TEST ACCOUNTS CREATED SUCCESSFULLY               ║");
        log.info("╚════════════════════════════════════════════════════════════╝");
        log.info("");
        log.info("1. ADMIN:");
        log.info("   Email: hv@gmail.com");
        log.info("   Pass:  admin123");
        log.info("");
        log.info("2. BUSINESS OWNER:");
        log.info("   Email: hv.business@gmail.com");
        log.info("   Pass:  business123");
        log.info("");
        log.info("3. CUSTOMER:");
        log.info("   Email: hv.customer@gmail.com");
        log.info("   Pass:  customer123");
        log.info("");
        log.info("════════════════════════════════════════════════════════════");
    }
}

