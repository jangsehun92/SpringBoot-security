package jsh.project.security.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import jsh.project.security.domain.Member;
import jsh.project.security.dto.MemberDto;

@Mapper
public interface MemberMapper{
    public int save(Member member);

    public Optional<MemberDto> findByEmail(String userEmail);
    
}