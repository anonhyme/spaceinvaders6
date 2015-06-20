package org.spaceinvaders.client;

import com.google.gwt.junit.client.GWTTestCase;

public class SandboxGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "org.spaceinvaders.Notus";
    }

    public void testSandbox() {
        assertTrue(true);
    }
}
