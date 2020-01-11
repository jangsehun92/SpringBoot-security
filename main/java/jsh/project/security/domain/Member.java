package jsh.project.security.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Member{
    private int id;
    private String email;
    private String password;

    public Member(){

    }

    public Member(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }
}