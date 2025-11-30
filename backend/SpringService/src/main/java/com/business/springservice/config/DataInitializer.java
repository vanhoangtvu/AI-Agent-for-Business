package com.business.springservice.config;

import com.business.springservice.entity.Category;
import com.business.springservice.entity.Product;
import com.business.springservice.entity.User;
import com.business.springservice.enums.Role;
import com.business.springservice.enums.Status;
import com.business.springservice.repository.CategoryRepository;
import com.business.springservice.repository.ProductRepository;
import com.business.springservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Initializing default users...");
            
            // Admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@ai.com");
            admin.setPassword(passwordEncoder.encode("hoang123"));
            admin.setRole(Role.ADMIN);
            admin.setAddress("Admin Office");
            admin.setPhoneNumber("0900000001");
            userRepository.save(admin);
            
            // Business user
            User business = new User();
            business.setUsername("business");
            business.setEmail("business@ai.com");
            business.setPassword(passwordEncoder.encode("hoang123"));
            business.setRole(Role.BUSINESS);
            business.setAddress("Business Center");
            business.setPhoneNumber("0900000002");
            userRepository.save(business);
            
            // Customer user
            User customer = new User();
            customer.setUsername("customer");
            customer.setEmail("customer@ai.com");
            customer.setPassword(passwordEncoder.encode("hoang123"));
            customer.setRole(Role.CUSTOMER);
            customer.setAddress("Customer Address");
            customer.setPhoneNumber("0900000003");
            userRepository.save(customer);
            
            log.info("Default users created successfully!");
            log.info("Admin - username: admin, email: admin@ai.com, password: hoang123");
            log.info("Business - username: business, email: business@ai.com, password: hoang123");
            log.info("Customer - username: customer, email: customer@ai.com, password: hoang123");
            
            // Initialize categories and products
            initializeCategoriesAndProducts(business);
        } else {
            log.info("Users already exist, skipping initialization.");
        }
    }
    
    private void initializeCategoriesAndProducts(User seller) {
        if (categoryRepository.count() == 0) {
            log.info("Initializing default categories and products...");
            
            // Category 1: Điện thoại
            Category phoneCategory = new Category();
            phoneCategory.setName("Điện thoại");
            phoneCategory.setDescription("Điện thoại thông minh các loại");
            phoneCategory.setImageUrl("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9");
            phoneCategory.setStatus(Status.ACTIVE);
            phoneCategory = categoryRepository.save(phoneCategory);
            
            // Products for Điện thoại
            Product iphone15 = new Product();
            iphone15.setName("iPhone 15 Pro Max");
            iphone15.setDescription("iPhone 15 Pro Max 256GB - Thiết kế Titan, chip A17 Pro mạnh mẽ");
            iphone15.setPrice(new BigDecimal("29990000"));
            iphone15.setQuantity(50);
            iphone15.setImageUrls("[\"https://images.unsplash.com/photo-1696446702798-8c0b5f7da85c\",\"https://images.unsplash.com/photo-1695048133142-1a20484d2569\"]");
            iphone15.setCategory(phoneCategory);
            iphone15.setSeller(seller);
            iphone15.setStatus(Status.ACTIVE);
            productRepository.save(iphone15);
            
            Product samsung = new Product();
            samsung.setName("Samsung Galaxy S24 Ultra");
            samsung.setDescription("Galaxy S24 Ultra 512GB - Màn hình Dynamic AMOLED 2X, S Pen tích hợp");
            samsung.setPrice(new BigDecimal("27990000"));
            samsung.setQuantity(30);
            samsung.setImageUrls("[\"https://images.unsplash.com/photo-1610945415295-d9bbf067e59c\",\"https://images.unsplash.com/photo-1598327105666-5b89351aff97\"]");
            samsung.setCategory(phoneCategory);
            samsung.setSeller(seller);
            samsung.setStatus(Status.ACTIVE);
            productRepository.save(samsung);
            
            // Category 2: Laptop
            Category laptopCategory = new Category();
            laptopCategory.setName("Laptop");
            laptopCategory.setDescription("Laptop cho công việc và giải trí");
            laptopCategory.setImageUrl("https://images.unsplash.com/photo-1496181133206-80ce9b88a853");
            laptopCategory.setStatus(Status.ACTIVE);
            laptopCategory = categoryRepository.save(laptopCategory);
            
            // Products for Laptop
            Product macbook = new Product();
            macbook.setName("MacBook Pro 14 inch M3");
            macbook.setDescription("MacBook Pro 14 inch M3 Pro 18GB 512GB - Hiệu năng vượt trội");
            macbook.setPrice(new BigDecimal("52990000"));
            macbook.setQuantity(20);
            macbook.setImageUrls("[\"https://images.unsplash.com/photo-1517336714731-489689fd1ca8\",\"https://images.unsplash.com/photo-1611186871348-b1ce696e52c9\"]");
            macbook.setCategory(laptopCategory);
            macbook.setSeller(seller);
            macbook.setStatus(Status.ACTIVE);
            productRepository.save(macbook);
            
            Product dell = new Product();
            dell.setName("Dell XPS 15");
            dell.setDescription("Dell XPS 15 - Intel Core i7, 16GB RAM, 512GB SSD, RTX 4050");
            dell.setPrice(new BigDecimal("35990000"));
            dell.setQuantity(15);
            dell.setImageUrls("[\"https://images.unsplash.com/photo-1593642632823-8f785ba67e45\",\"https://images.unsplash.com/photo-1588872657578-7efd1f1555ed\"]");
            dell.setCategory(laptopCategory);
            dell.setSeller(seller);
            dell.setStatus(Status.ACTIVE);
            productRepository.save(dell);
            
            // Category 3: Tai nghe
            Category headphoneCategory = new Category();
            headphoneCategory.setName("Tai nghe");
            headphoneCategory.setDescription("Tai nghe chất lượng cao");
            headphoneCategory.setImageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e");
            headphoneCategory.setStatus(Status.ACTIVE);
            headphoneCategory = categoryRepository.save(headphoneCategory);
            
            // Products for Tai nghe
            Product airpods = new Product();
            airpods.setName("AirPods Pro 2");
            airpods.setDescription("AirPods Pro 2 - Chống ồn chủ động, âm thanh không gian");
            airpods.setPrice(new BigDecimal("6490000"));
            airpods.setQuantity(100);
            airpods.setImageUrls("[\"https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7\",\"https://images.unsplash.com/photo-1606220945770-b5b6c2c55bf1\"]");
            airpods.setCategory(headphoneCategory);
            airpods.setSeller(seller);
            airpods.setStatus(Status.ACTIVE);
            productRepository.save(airpods);
            
            Product sony = new Product();
            sony.setName("Sony WH-1000XM5");
            sony.setDescription("Sony WH-1000XM5 - Tai nghe chống ồn hàng đầu, pin 30 giờ");
            sony.setPrice(new BigDecimal("8990000"));
            sony.setQuantity(40);
            sony.setImageUrls("[\"https://images.unsplash.com/photo-1545127398-14699f92334b\",\"https://images.unsplash.com/photo-1484704849700-f032a568e944\"]");
            sony.setCategory(headphoneCategory);
            sony.setSeller(seller);
            sony.setStatus(Status.ACTIVE);
            productRepository.save(sony);
            
            // Category 4: Đồng hồ thông minh (INACTIVE - để test)
            Category smartwatchCategory = new Category();
            smartwatchCategory.setName("Đồng hồ thông minh");
            smartwatchCategory.setDescription("Smartwatch theo dõi sức khỏe");
            smartwatchCategory.setImageUrl("https://images.unsplash.com/photo-1579586337278-3befd40fd17a");
            smartwatchCategory.setStatus(Status.INACTIVE);
            smartwatchCategory = categoryRepository.save(smartwatchCategory);
            
            // Product for Đồng hồ (sẽ không hiển thị trong shop vì category INACTIVE)
            Product appleWatch = new Product();
            appleWatch.setName("Apple Watch Series 9");
            appleWatch.setDescription("Apple Watch Series 9 - GPS, 45mm, viền nhôm");
            appleWatch.setPrice(new BigDecimal("10990000"));
            appleWatch.setQuantity(25);
            appleWatch.setImageUrls("[\"https://images.unsplash.com/photo-1434493789847-2f02dc6ca35d\"]");
            appleWatch.setCategory(smartwatchCategory);
            appleWatch.setSeller(seller);
            appleWatch.setStatus(Status.ACTIVE);
            productRepository.save(appleWatch);
            
            log.info("Default categories and products created successfully!");
            log.info("- 3 ACTIVE categories with 6 ACTIVE products");
            log.info("- 1 INACTIVE category with 1 product (for testing)");
        } else {
            log.info("Categories already exist, skipping initialization.");
        }
    }
}
