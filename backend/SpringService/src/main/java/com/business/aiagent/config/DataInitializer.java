package com.business.aiagent.config;

import com.business.aiagent.entity.Role;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.RoleRepository;
import com.business.aiagent.repository.UserRepository;
import com.business.aiagent.security.RolePermissionDefaults;
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
        createOrUpdateRole(
            Role.RoleName.ADMIN,
            "System Administrator - Full access",
            RolePermissionDefaults.getPermissionsForRole(Role.RoleName.ADMIN)
        );
        createOrUpdateRole(
            Role.RoleName.BUSINESS,
            "Business Owner - Manage own products, orders, analytics",
            RolePermissionDefaults.getPermissionsForRole(Role.RoleName.BUSINESS)
        );
        createOrUpdateRole(
            Role.RoleName.CUSTOMER,
            "Customer - Shop and view orders",
            RolePermissionDefaults.getPermissionsForRole(Role.RoleName.CUSTOMER)
        );
    }

    private void createOrUpdateRole(Role.RoleName roleName, String description, Set<Role.Permission> permissions) {
        Role role = roleRepository.findByName(roleName).orElse(null);
        if (role == null) {
            Role newRole = Role.builder()
                .name(roleName)
                .description(description)
                .permissions(new HashSet<>(permissions))
                .build();
            roleRepository.save(newRole);
            log.info("Created {} role with {} permissions", roleName, permissions.size());
            return;
        }

        boolean updated = false;
        if (role.getPermissions() == null) {
            role.setPermissions(new HashSet<>(permissions));
            updated = true;
        } else {
            Set<Role.Permission> currentPermissions = new HashSet<>(role.getPermissions());
            if (!currentPermissions.equals(permissions)) {
                role.getPermissions().clear();
                role.getPermissions().addAll(permissions);
                updated = true;
            }
        }
        if ((role.getDescription() == null && description != null) ||
            (role.getDescription() != null && !role.getDescription().equals(description))) {
            role.setDescription(description);
            updated = true;
        }

        if (updated) {
            roleRepository.save(role);
            log.info("Updated {} role with {} permissions", roleName, permissions.size());
        } else {
            log.info("{} role is already up-to-date", roleName);
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

}
