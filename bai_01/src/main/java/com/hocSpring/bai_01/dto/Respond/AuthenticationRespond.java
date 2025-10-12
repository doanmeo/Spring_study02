package com.hocSpring.bai_01.dto.Respond;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)  
public class AuthenticationRespond {
    String token; // Thêm trường token nếu cần thiết
    boolean authenticated;

}
