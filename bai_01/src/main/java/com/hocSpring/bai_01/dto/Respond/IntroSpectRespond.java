package com.hocSpring.bai_01.dto.Respond;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
// @NoArgsConstructor
// @AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IntroSpectRespond {
    boolean valid;
    

}
