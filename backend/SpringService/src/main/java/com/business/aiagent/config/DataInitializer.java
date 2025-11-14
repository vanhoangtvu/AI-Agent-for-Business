package com.business.aiagent.config;

import com.business.aiagent.entity.Role;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.RoleRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Starting data initialization...");
        
        // Initialize roles first
        initializeRoles();
        
        // Initialize demo users
        initializeUsers();
        
        log.info("Data initialization completed!");
    }

    private void initializeRoles() {
        // Create ADMIN role if not exists
        if (roleRepository.findByName(Role.RoleName.ADMIN).isEmpty()) {
            Role adminRole = Role.builder()
                .name(Role.RoleName.ADMIN)
                .description("System Administrator - Full access")
                .permissions(getAllPermissions())
                .build();
            roleRepository.save(adminRole);
            log.info("Created ADMIN role with all permissions");
        }

        // Create BUSINESS role if not exists
        if (roleRepository.findByName(Role.RoleName.BUSINESS).isEmpty()) {
            Role businessRole = Role.builder()
                .name(Role.RoleName.BUSINESS)
                .description("Business Owner - Manage own products, orders, analytics")
                .permissions(getBusinessPermissions())
                .build();
            roleRepository.save(businessRole);
            log.info("Created BUSINESS role with business permissions");
        }

        // Create CUSTOMER role if not exists
        if (roleRepository.findByName(Role.RoleName.CUSTOMER).isEmpty()) {
            Role customerRole = Role.builder()
                .name(Role.RoleName.CUSTOMER)
                .description("Customer - Shop and view orders")
                .permissions(getCustomerPermissions())
                .build();
            roleRepository.save(customerRole);
            log.info("Created CUSTOMER role with customer permissions");
        }
    }

    private void initializeUsers() {
        // Create ADMIN user
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName(Role.RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            
            User admin = User.builder()
                .username("admin")
                .email("admin@aiagent.com")
                .password(passwordEncoder.encode("admin123"))
                .fullName("System Administrator")
                .roles(Set.of(adminRole))
                .build();
            userRepository.save(admin);
            log.info("Created ADMIN user (username: admin, password: admin123)");
        }

        // Create BUSINESS user
        if (!userRepository.existsByUsername("business")) {
            Role businessRole = roleRepository.findByName(Role.RoleName.BUSINESS)
                .orElseThrow(() -> new RuntimeException("BUSINESS role not found"));
            
            User business = User.builder()
                .username("business")
                .email("business@example.com")
                .password(passwordEncoder.encode("admin123"))
                .fullName("Business Owner Demo")
                .businessName("Demo Electronics Store")
                .businessType("Retail")
                .businessDescription("Electronic devices and accessories retail store")
                .businessAddress("123 Tech Street, Silicon Valley")
                .businessPhone("+1-555-0100")
                .businessEmail("contact@demoelectronics.com")
                .taxCode("TAX123456789")
                .isBusinessVerified(true)
                .roles(Set.of(businessRole))
                .build();
            userRepository.save(business);
            log.info("Created BUSINESS user (username: business, password: admin123)");
        }

        // Create CUSTOMER user
        if (!userRepository.existsByUsername("customer")) {
            Role customerRole = roleRepository.findByName(Role.RoleName.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));
            
            User customer = User.builder()
                .username("customer")
                .email("customer@example.com")
                .password(passwordEncoder.encode("admin123"))
                .fullName("John Customer")
                .roles(Set.of(customerRole))
                .build();
            userRepository.save(customer);
            log.info("Created CUSTOMER user (username: customer, password: admin123)");
        }
    }

    private Set<Role.Permission> getAllPermissions() {
        Set<Role.Permission> permissions = new HashSet<>();
        // Add all permissions for ADMIN
        permissions.add(Role.Permission.USER_READ);
        permissions.add(Role.Permission.USER_CREATE);
        permissions.add(Role.Permission.USER_UPDATE);
        permissions.add(Role.Permission.USER_DELETE);
        permissions.add(Role.Permission.PRODUCT_READ);
        permissions.add(Role.Permission.PRODUCT_CREATE);
        permissions.add(Role.Permission.PRODUCT_UPDATE);
        permissions.add(Role.Permission.PRODUCT_DELETE);
        permissions.add(Role.Permission.PRODUCT_MANAGE_ALL);
        permissions.add(Role.Permission.CATEGORY_READ);
        permissions.add(Role.Permission.CATEGORY_CREATE);
        permissions.add(Role.Permission.CATEGORY_UPDATE);
        permissions.add(Role.Permission.CATEGORY_DELETE);
        permissions.add(Role.Permission.ORDER_READ);
        permissions.add(Role.Permission.ORDER_CREATE);
        permissions.add(Role.Permission.ORDER_UPDATE);
        permissions.add(Role.Permission.ORDER_DELETE);
        permissions.add(Role.Permission.ORDER_MANAGE_ALL);
        permissions.add(Role.Permission.DOCUMENT_READ);
        permissions.add(Role.Permission.DOCUMENT_CREATE);
        permissions.add(Role.Permission.DOCUMENT_UPDATE);
        permissions.add(Role.Permission.DOCUMENT_DELETE);
        permissions.add(Role.Permission.DOCUMENT_MANAGE_ALL);
        permissions.add(Role.Permission.ANALYTICS_VIEW);
        permissions.add(Role.Permission.ANALYTICS_VIEW_ALL);
        permissions.add(Role.Permission.REPORT_GENERATE);
        permissions.add(Role.Permission.REPORT_EXPORT);
        permissions.add(Role.Permission.CHAT_USE);
        permissions.add(Role.Permission.CHAT_VIEW_HISTORY);
        permissions.add(Role.Permission.SYSTEM_CONFIG);
        permissions.add(Role.Permission.SYSTEM_LOGS);
        permissions.add(Role.Permission.ROLE_READ);
        permissions.add(Role.Permission.ROLE_MANAGE);
        return permissions;
    }

    private Set<Role.Permission> getBusinessPermissions() {
        Set<Role.Permission> permissions = new HashSet<>();
        permissions.add(Role.Permission.PRODUCT_READ);
        permissions.add(Role.Permission.PRODUCT_CREATE);
        permissions.add(Role.Permission.PRODUCT_UPDATE);
        permissions.add(Role.Permission.PRODUCT_DELETE);
        permissions.add(Role.Permission.ORDER_READ);
        permissions.add(Role.Permission.ORDER_UPDATE);
        permissions.add(Role.Permission.DOCUMENT_READ);
        permissions.add(Role.Permission.DOCUMENT_CREATE);
        permissions.add(Role.Permission.DOCUMENT_UPDATE);
        permissions.add(Role.Permission.DOCUMENT_DELETE);
        permissions.add(Role.Permission.ANALYTICS_VIEW);
        permissions.add(Role.Permission.REPORT_GENERATE);
        permissions.add(Role.Permission.REPORT_EXPORT);
        permissions.add(Role.Permission.CHAT_USE);
        permissions.add(Role.Permission.CHAT_VIEW_HISTORY);
        return permissions;
    }

    private Set<Role.Permission> getCustomerPermissions() {
        Set<Role.Permission> permissions = new HashSet<>();
        // Customer permissions
        permissions.add(Role.Permission.PRODUCT_READ);
        permissions.add(Role.Permission.CATEGORY_READ);
        permissions.add(Role.Permission.ORDER_READ);
        permissions.add(Role.Permission.ORDER_CREATE);
        permissions.add(Role.Permission.CHAT_USE);
        return permissions;
    }
}
