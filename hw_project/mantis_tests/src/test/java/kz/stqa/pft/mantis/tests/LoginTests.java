package kz.stqa.pft.mantis.tests;

import kz.stqa.pft.mantis.appmanager.HttpSession;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

/**
 * Created by xeniya on 7/16/16.
 */
public class LoginTests extends TestBase {

    @Test
    public  void testLogin() throws IOException {
        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
