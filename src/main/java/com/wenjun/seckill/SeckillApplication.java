package com.wenjun.seckill;

import com.wenjun.seckill.dao.UserDOMapper;
import com.wenjun.seckill.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.wenjun.seckill.dao")
@RestController
public class SeckillApplication {

    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home() {
        UserDO userDo = userDOMapper.selectByPrimaryKey(1);
        if (userDo == null) return "null";
        else return "hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }

}
