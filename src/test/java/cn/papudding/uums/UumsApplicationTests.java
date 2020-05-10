package cn.papudding.uums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootTest
class UumsApplicationTests {

    @Test
    void contextLoads() {
        String str = BCrypt.hashpw("123456",BCrypt.gensalt());
        System.out.println(str);


    }

}
