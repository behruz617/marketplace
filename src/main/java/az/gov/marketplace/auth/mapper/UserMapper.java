package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.User;
import az.gov.marketplace.auth.dto.request.RegisterRequest;
import az.gov.marketplace.auth.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "lastLogin", ignore = true)
    User toEntity(RegisterRequest reg);

    UserResponse toResponse(User user);
}