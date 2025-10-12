package com.hocSpring.bai_01.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hocSpring.bai_01.dto.Request.AuthenticationRequest;
import com.hocSpring.bai_01.dto.Request.IntroSpectRequest;
import com.hocSpring.bai_01.dto.Respond.AuthenticationRespond;
import com.hocSpring.bai_01.dto.Respond.IntroSpectRespond;
import com.hocSpring.bai_01.exception.AppException;
import com.hocSpring.bai_01.exception.ErrorCode;
import com.hocSpring.bai_01.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
   UserRepository userRepository; // Sử dụng UserRepository để truy cập dữ liệu người dùng
   @NonFinal // khong inject vao constructor
   @Value("${jwt.signerKey}")
   protected String SIGNED_KEY = "7sf94b4BdC76Nu^kqTni$nwv#a%m1gx4r9/#/n9dvy#="; // Khóa bí mật để ký token (nên lưu trữ
                                                                                 // an toàn)

   public IntroSpectRespond introspect(IntroSpectRequest request) {// Phương thức xác thực người dùng
      var token = request.getToken();

      try {
         JWSVerifier verifier = new MACVerifier(SIGNED_KEY.getBytes()); // Tạo bộ xác minh với khóa bí mật
         try {
            SignedJWT signedJWT = SignedJWT.parse(token); // Phân tích chuỗi token thành đối tượng SignedJWT
            var verifiered = signedJWT.verify(verifier); // Xác minh chữ ký của token
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // Lấy thời gian hết hạn từ claims

            return verifiered && expirationTime.after(new Date()) ? IntroSpectRespond.builder().valid(true).build()
                  : IntroSpectRespond.builder().valid(false).build();
         } catch (ParseException e) {
            e.printStackTrace();
         }

      } catch (JOSEException e) {
         e.printStackTrace();
      }
      return IntroSpectRespond.builder().valid(false).build();
   }

   public AuthenticationRespond authenticate(AuthenticationRequest request) {// Phương thức xác thực người dùng
      var user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
      boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
      if (!isAuthenticated) {
         throw new AppException(ErrorCode.UN_AUTHENTICATED);
      }
      // Nếu xác thực thành công, có thể trả về token hoặc thông tin người dùng
      var token = generateToken(request.getUsername());
      return AuthenticationRespond.builder().token(token).authenticated(true).build();

   }

   private String generateToken(String username) {
      JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);// Sử dụng thuật toán HS512
      // cac data trong body goi la claims
      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(username) // Ai la nguoi so huu token
            .issuer("Devteia.com") // Ai phát hành token
            .issueTime(new Date()) // Thời gian phát hành token
            .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli())) // Thời gian hết hạn token
                                                                                             // (1 ngày)
            .claim("customClaim", "CustomValue") // Thêm các thông tin tùy chỉnh nếu cần thiết
            .build();
      Payload payload = new Payload(claimsSet.toJSONObject()); // Chuyển đổi claimsSet thành Payload để tạo JWSObject

      JWSObject jwsObject = new JWSObject(header, payload);
      // Ký token (signing) - trong thực tế, bạn nên sử dụng một khóa bí mật mạnh mẽ
      // len web generate ramdom.org de tao chuoi ngau nhien 32 bytes
      try {
         jwsObject.sign(new MACSigner(SIGNED_KEY.getBytes()));
         return jwsObject.serialize(); // Trả về token đã ký dưới dạng chuỗi
      } catch (KeyLengthException e) {
         e.printStackTrace();
      } catch (JOSEException e) {
         e.printStackTrace();
      }
      return "token"; // Trả về token đã ký dưới dạng chuỗi
   }

}
