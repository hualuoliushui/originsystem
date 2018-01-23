package com.scut.originsystem.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptUtilTest {

    @Test
    public void encrypt() {
        String str = "12345678";
        System.out.println(EncryptUtil.encrypt(str));

    }
}