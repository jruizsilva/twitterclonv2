package twitterclonv2.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import twitterclonv2.common.util.Role;
import twitterclonv2.domain.dto.user.request.CreateUserEntityRequest;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity createOneCustomer(CreateUserEntityRequest createUserEntityRequest) {
        UserEntity userEntity =
                UserEntity.builder()
                          .name(createUserEntityRequest.getName())
                          .username(createUserEntityRequest.getUsername())
                          .password(passwordEncoder.encode(createUserEntityRequest.getPassword()))
                          .role(Role.CUSTOMER)
                          .build();
        return userRepository.save(userEntity);
    }
}
