package com.carero.controller;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MailApiControllerTest {

    @Test
    public void randomUuid(){
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }

}