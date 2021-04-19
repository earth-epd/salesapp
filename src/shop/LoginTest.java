package shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest{

    @Test
    void reGis() {
        assertEquals("shop.Login",Login.class.getName());
    }

    @Test
    void loGin() {
        assertEquals("Login",Login.class.getSimpleName().toString());
    }
}