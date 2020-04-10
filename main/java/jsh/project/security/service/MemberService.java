package jsh.project.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsh.project.security.dao.AuthMapper;
import jsh.project.security.dao.MemberMapper;
import jsh.project.security.domain.Role;
import jsh.project.security.dto.MemberDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService{
    private MemberMapper memberMapper;
    private AuthMapper authMapper;

    @Transactional
    public void joinUser(MemberDto memberDto){
        //회원가입시 Role부여하기(일반 회원 가입)
        memberDto.setRole(Role.MEMBER.getValue());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        memberMapper.save(memberDto.toDomainObject());
        authMapper.save(memberDto.getEmail());
    }

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        //Member객체에 직접 UserDtatils를 구현하기도함
        Optional<MemberDto> memberDtoWrapper = memberMapper.findByEmail(userEmail);
        MemberDto user = memberDtoWrapper.get();
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority(authMapper.findByEmail(userEmail)));

        return new User(user.getEmail(), user.getPassword(), authorities);

	}

    
}
