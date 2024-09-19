package com.finance.user.service;

import com.finance.exception.ErrorCode;
import com.finance.exception.NotFoundException;
import com.finance.user.domain.User;
import com.finance.user.domain.UserDetail;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByAccount(username)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));


        return UserDetail.builder()
                .account(user.getAccount())
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }
}
