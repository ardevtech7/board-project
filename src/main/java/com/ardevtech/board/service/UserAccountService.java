package com.ardevtech.board.service;

import com.ardevtech.board.domain.UserAccount;
import com.ardevtech.board.dto.UserAccountDto;
import com.ardevtech.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    // entity 정보는 service 밖으로 나가지 않게 dto 로 반환
    @Transactional(readOnly = true)
    public Optional<UserAccountDto> searchUser(String username) {
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from);
    }

    public UserAccountDto saveUser(
            String username,
            String password,
            String email,
            String nickname,
            String memo
    ) {
        return UserAccountDto.from(
                // entity -> dto
                userAccountRepository.save(UserAccount.of(username, password, email, nickname, memo, username))
        );
    }
}
