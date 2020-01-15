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
        Optional<MemberDto> memberDtoWrapper = memberMapper.findMember(userEmail);
        MemberDto user = memberDtoWrapper.get();
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        //로그인한 유저의 Role을 가져와서 User 객체에 담아주기
		if (("admin@admin.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        
        return new User(user.getEmail(), user.getPassword(), authorities);

	}

    
}