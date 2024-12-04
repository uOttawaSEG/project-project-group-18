package com.example.seg_project_d11;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private UserValidator userValidator;

    public void setUp(){
        userValidator = new UserValidator();
    }

    @Test
    public void testPasswordWithoutNumbers(){
        assertFalse(userValidator.validatePassword("password"));
    }

    @Test
    public void testPasswordWithoutLetters(){
        assertFalse(userValidator.validatePassword("123"));
    }

    @Test
    public void testPasswordWithBoth(){
        assertTrue(userValidator.validatePassword("password123"));
    }

    @Test
    public void testEmailWithoutAt(){
        assertFalse(userValidator.validateEmail("hello.com"));
    }

    public void testEmailWithoutDot(){
        assertFalse(userValidator.validateEmail("hello@gmail"));
    }

    public void testEmailWithBoth(){
        assertTrue(userValidator.validateEmail("hello@gmail.com"));
    }

}