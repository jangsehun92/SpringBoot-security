package jsh.project.security.config;

import java.lang.reflect.Member;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import jsh.project.security.dto.MemberDto;

@Configuration
@MapperScan(basePackages = "jsh.project.security.dao") // mapper interface 경로 지정
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

        sqlSessionFactory.setDataSource(dataSource);
        //sqlSessionFactory.setTypeAliasesPackage("jsh.project.security.dto"); //DTO가 위치해 있는 곳을 잡아준다.
        sqlSessionFactory.setTypeAliases(new Class[]{
            Member.class,
            MemberDto.class
        });
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/*.xml")); //실질적인 sql 쿼리가 있는 mapper.xml
        
        return sqlSessionFactory.getObject();
    }
    
    @Bean
    public SqlSessionTemplate sqlSession (SqlSessionFactory sqlSessionFactory) {
        
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}