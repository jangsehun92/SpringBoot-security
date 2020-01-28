package jsh.project.security.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    public int save(String userEmail);
    public String findByEmail(String userEmail);
}