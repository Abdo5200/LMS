package org.spring.lms.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserAccount toEntity(CreateUserRequest request);

    UserResponse toResponse(UserAccount user);
}
