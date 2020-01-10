package jsh.project.security.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEntity{
    private int id;
    private String email;
    private String password;

    @Builder
    public MemberEntity(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }
}