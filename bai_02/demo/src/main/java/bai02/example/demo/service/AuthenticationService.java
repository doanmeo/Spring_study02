package bai02.example.demo.service;

import java.text.ParseException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

import bai02.example.demo.dto.request.AuthenticationRequest;
import bai02.example.demo.dto.request.IntrospectRequest;
import bai02.example.demo.dto.response.AuthenticationResponse;
import bai02.example.demo.dto.response.IntrorespectResponse;
import bai02.example.demo.exception.AppException;
import bai02.example.demo.exception.ErrorCode;
import bai02.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;// laays danh sach user tu UserRepository
    @NonFinal
    protected static final String SECRET_KEY = "ef03bad871c220f440e6975a1427d0aa6b3b1bfbcd9341016505cdda3dbee331";

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.User_Not_Existed));
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isAuthenticated = encoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            throw new AppException(ErrorCode.Invalid_Credentials);
        }
        String token = generateJwtToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(isAuthenticated)
                .build();
    }

    private String generateJwtToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512); // Sử dụng thuật toán HS512 để mã hóa JWT

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username) // đặt chủ đề của token là tên người dùng
                .issuer("your-app-name")// đặt nhà phát hành của token là tên ứng dụng
                .issueTime(new Date()) // đặt thời gian phát hành của token là thời gian hiện tại
                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // đặt thời gian hết hạn của token là 1
                                                                                 // giờ sau thời gian hiện tại
                .claim("role", "USER") // claim: thêm thông tin vai trò người dùng vào token
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());// chuyển đổi claimsSet thành định dạng JSON và tạo
                                                                // payload cho token

        JWSObject jwsObject = new JWSObject(header, payload);

        // ký token (signature) - trong ví dụ này, chúng ta sẽ bỏ qua bước ký token để
        // đơn giản hóa
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY));
            return jwsObject.serialize(); // trả về token đã được ký dưới dạng chuỗi
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return null;
    }

    public IntrorespectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes()); // tạo bộ xác minh token với khóa bí mật
            SignedJWT signedJWT = SignedJWT.parse(token);// phân tích chuỗi token thành đối tượng SignedJWT
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier); // xác minh token
            return IntrorespectResponse.builder()
                    .valid(verified && expirationTime.after(new Date())) // kiểm tra token có hợp lệ và chưa hết hạn
                    .build();
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
        return IntrorespectResponse.builder()
                .valid(false)
                .build();

    };
}
