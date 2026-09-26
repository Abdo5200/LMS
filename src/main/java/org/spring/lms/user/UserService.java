package org.spring.lms.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional
public class UserService {
    private final UserRepository users;
    private final UserMapper mapper;

    public UserService(UserRepository users, UserMapper mapper) {
        this.users = users;
        this.mapper = mapper;
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (users.findByEmailIgnoreCase(request.email().trim()).isPresent()) {
            throw new IllegalStateException("An account with this email already exists.");
        }
        UserAccount user = mapper.toEntity(request);
        user.setName(user.getName().trim());
        user.setEmail(user.getEmail().trim().toLowerCase(Locale.ROOT));
        return mapper.toResponse(users.save(user));
    }

}
