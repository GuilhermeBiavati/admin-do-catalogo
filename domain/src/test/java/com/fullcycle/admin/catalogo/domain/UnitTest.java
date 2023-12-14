package com.fullcycle.admin.catalogo.domain;

import org.junit.jupiter.api.Tag;

@Tag("unitTest")
public class UnitTest {


    protected void waitToUpdate() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
