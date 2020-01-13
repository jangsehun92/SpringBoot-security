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

import jsh.project.security.dao.MemberMapper;
import jsh.project.security.domain.Role;
import jsh.project.security.dto.MemberDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService{
    private MemberMapper memberMapper;

    @Transactional
    public int joinUser(MemberDto memberDto){
        //회원가입시 Role부여하기
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberMapper.save(memberDto.toDomainObject());
    }

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        //login 함수를 만들던지, LoginRequestDto를 통해 로그인을 하던지 둘중하나로 바꾸기
        System.out.println(userEmail);
        Optional<MemberDto> memberDtoWrapper = memberMapper.login(userEmail);
        MemberDto user = memberDtoWrapper.get();
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        //로그인한 유저의 Role을 확인하여 권한을 부여하자 
		if (("admin@admin.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        
        return new User(user.getEmail(), user.getPassword(), authorities);

	}

    
}