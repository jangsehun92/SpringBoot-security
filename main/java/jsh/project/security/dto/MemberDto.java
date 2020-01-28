package jsh.project.security.dto;

import java.time.LocalDateTime;

import jsh.project.security.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto{
    private int id;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdDate;
    private LocalDateTime modefiedDate;


    public Member toDomainObject(){
        /*
        return Member.builder()
        .id(id)
        .email(email)
        .password(password)
        .build();
        */

        return new Member(id,email,password);
    }

    @Builder
    public MemberDto(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

}