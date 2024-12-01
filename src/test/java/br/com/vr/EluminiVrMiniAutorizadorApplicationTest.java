package br.com.vr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EluminiVrMiniAutorizadorApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> EluminiVrMiniAutorizadorApplication.main(new String[]{}));
    }
}
