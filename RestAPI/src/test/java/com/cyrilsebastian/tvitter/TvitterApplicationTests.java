package com.cyrilsebastian.tvitter;

import com.cyrilsebastian.tvitter.config.redis.RedisServerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RedisServerConfiguration.class)
class TvitterApplicationTests {

    @Test
    void contextLoads() {
    }

}
