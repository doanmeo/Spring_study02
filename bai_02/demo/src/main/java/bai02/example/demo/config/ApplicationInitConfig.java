package bai02.example.demo.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import bai02.example.demo.entity.User;
import bai02.example.demo.enums.Role;
import bai02.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j // Tự động tạo logger cho lớp này    
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean // Đánh dấu phương thức này là một bean quản lý bởi Spring
    ApplicationRunner runner(UserRepository userRepository) { // ApplicationRunner: interface được sử dụng để thực thi mã sau khi ứng dụng Spring Boot đã khởi động hoàn tất
        return args -> {
          if (userRepository.findByUsername("admin").isEmpty()) {
            var roles = new HashSet<String>();
            roles.add(Role.ADMIN.name());
            User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123")) // Mật khẩu đã được mã hóa (ví dụ: "admin123")
                    .email("admin@example.com")
                    .roles(roles)
                    .build();
            userRepository.save(user);
            log.warn("Admin user created with username: admin and password: admin123");
          }
        };
    }

}
