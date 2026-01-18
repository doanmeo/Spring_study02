package bai02.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import bai02.example.demo.dto.request.UserCreationRequest;
import bai02.example.demo.dto.request.UserUpdateRequest;
import bai02.example.demo.dto.response.UserResponse;
import bai02.example.demo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    // @Mapping(target = "id", ignore = true)
    // bỏ qua trường id khi mapping
    // @Mapping(source = "firstName", target = "lastName")
    // map firstName của request sang lastName của entity -> firstName = lastName
    UserResponse toUserResponse(User user);

    void updateUser(UserUpdateRequest request, @MappingTarget User user);
    // @MappingTarget: chỉ định đối tượng đích cần cập nhật

}
