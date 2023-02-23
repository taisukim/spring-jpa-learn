package com.jpabook.jpashop.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        try(Connection connection = dataSource.getConnection()){
//            log.info(connection.getMetaData().getURL());
//        }
    }
}
