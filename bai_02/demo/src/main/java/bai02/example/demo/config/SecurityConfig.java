package bai02.example.demo.config;

import java.lang.reflect.Method;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Bật cấu hình bảo mật web của Spring Security
@EnableMethodSecurity // Bật bảo mật cấp phương thức, cho phép sử dụng các chú thích bảo mật trên các phương thức
public class SecurityConfig {
        private static final String[] PUBLIC_ENDPOINTS = {
                        "/auth/log-in",
                        "/auth/token",
                        "/auth/introspect",
                        "/users",
                        "/users/"
        };
        @Value("${jwt.signerKey}")
        protected String SECRET_KEY;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF để dễ dàng kiểm thử API
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(PUBLIC_ENDPOINTS).permitAll() // Cho phép truy cập
                                                                                               // không cần xác thực đến
                                                                                               // các endpoint công khai
                                                .requestMatchers(HttpMethod.POST, "/auth/token").permitAll() // Cho phép
                                                // .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN") // Chỉ
                                                                                                                       // cho
                                                                                                                       // phép
                                                                                                                       // người
                                                                                                                       // dùng
                                                                                                                       // có
                                                                                                                       // vai
                                                                                                                       // trò
                                                                                                                       // ADMIN
                                                                                                                       // truy
                                                                                                                       // cập
                                                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các yêu cầu
                                                                              // khác
                                // .anyRequest().permitAll()
                                )
                                .httpBasic(Customizer.withDefaults()); // Sử dụng xác thực HTTP Basic

                httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwtConfiger -> jwtConfiger.decoder(jwtDecoder()).
                                jwtAuthenticationConverter(jwtAuthenticationConverter()))); // Cấu hình máy chủ tài nguyên
                                                                                         // OAuth2 để sử dụng JWT với bộ
                                                                                         // giải mã tùy chỉnh để dùng
                                                                                         // token authenticate endpoint
                                httpSecurity.csrf(AbstractHttpConfigurer::disable); // Vô hiệu hóa CSRF để dễ dàng kiểm thử API
                return httpSecurity.build();
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
               JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Tiền tố cho các quyền
                // grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Tên của claim chứa các quyền trong JWT

                JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
                jwtConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

                return jwtConverter;
        }

        @Bean
        JwtDecoder jwtDecoder() {
                SecretKeySpec secretKey = new SecretKeySpec(
                                SECRET_KEY.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                                "HS512"); // secretkeyspec : tạo khóa bí mật từ chuỗi ký tự

                return org.springframework.security.oauth2.jwt.NimbusJwtDecoder
                                .withSecretKey(secretKey)
                                .macAlgorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS512)
                                .build(); // Trả về một đối tượng JwtDecoder sử dụng khóa bí mật và thuật toán HS512 để
                                          // giải mã JWT
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
        }

}