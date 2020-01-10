package jsh.project.security.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import jsh.project.security.domain.MemberEntity;

@Mapper
public interface MemberMapper{
    public int save(MemberEntity member);

    public Optional<MemberEntity> findByEmail(String userEmail);
    
}