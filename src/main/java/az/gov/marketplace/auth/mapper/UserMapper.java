package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.RegisterRequest;
import az.gov.marketplace.auth.dto.UserResponse;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    User toEntity(RegisterRequest reg);

    UserResponse toResponse(User user);

}
