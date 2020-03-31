package com.chann.crystalshineproject.data;

public class Token {

    public static String token;

    public static class MyToken{
        public static String getToken() {

            return token; }

        public static void setToken(String token) { MyToken.token = token; }

        public static String token ;
        public MyToken(){
        }
    }

}
