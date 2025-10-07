package com.hocSpring.bai_01.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hocSpring.bai_01.dto.Request.UserCreationRequest;
import com.hocSpring.bai_01.dto.Request.UserUpdateRequest;
import com.hocSpring.bai_01.dto.Respond.UserRespond;
import com.hocSpring.bai_01.entity.User;

@Mapper(componentModel = "spring") // de spring tu dong quan ly bean cua mapper

public interface UserMapper {

    User toUser(UserCreationRequest request);// chuyen tu UserCreationRequest sang User
   
    // @Mapping(source ="firstName" ,target="lastName")// chuyen doi truong firstName cua request thanh lastName cua user
    void updateUser(UserUpdateRequest request,@MappingTarget User user);// cap nhat thong tin user tu request thay the cho viec set tung truong
    // @Mapping(target = "lastName", ignore = true) // bo qua truong lastName khi chuyen doi
    UserRespond toUserRespond(User user);// chuyen tu User sang UserRespond
}
