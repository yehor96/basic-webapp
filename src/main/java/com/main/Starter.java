package com.main;

public class Starter {

    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(3000);
        server.setWebAppPath("src/main/resources/webapp");
        server.start();
    }

}
