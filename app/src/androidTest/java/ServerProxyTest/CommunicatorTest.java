package ServerProxyTest;

import android.util.Log;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import Model.AuthToken;
import Model.User;
import Server.Communicator;
import Server.ServerData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * This tests getting data from the web service.
 */
public class CommunicatorTest {

    private ServerData serverData;

    @Test
    public void testLogin() {
        try {
            Communicator communicator = new Communicator();
            JSONObject jsonObject = communicator.sendLoginRequest();
            assertNotNull(jsonObject.getString("userName"));
            assertNotNull(jsonObject.getString("authToken"));
            assertNotNull(jsonObject.getString("personID"));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testRegister() {
        try {
            this.serverData.setNewUser(new User("testUser2",
                    "aoeu",
                    "test2@gmail.com",
                    "test2",
                    "user2",
                    "m", null));
            Communicator communicator = new Communicator();
            JSONObject jsonObject = communicator.sendRegisterRequest();
            assertEquals(jsonObject.getString("userName"), "testUser2");
            assertNotNull(jsonObject.getString("authToken"));
            assertNotNull(jsonObject.getString("personID"));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetEvents() {
        try {
            Communicator communicator = new Communicator();
            communicator.requestFillUser();
            JSONObject j = communicator.getEvents();
            assertNotNull(j);
            Log.d("testGetEvents", j.toString());

            /// test event filtering
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetPeople() {
        try {
            Communicator communicator = new Communicator();
            communicator.requestFillUser();
            JSONObject j = communicator.getPeople();
            Log.d("testGetPeople", j.toString());
            assertNotNull(j);

            // test family relationships
            //
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Before
    public void prepServerData() {
        this.serverData = ServerData.getInstance();
        this.serverData.setServerHost("10.0.2.2");
        this.serverData.setServerPort("8080");
        Communicator communicator = new Communicator();
        communicator.clearDB();
        this.serverData.setNewUser(new User("testUser",
                "1234",
                "test@gmail.com",
               "test",
              "user",
               "f", null));
        try {
            AuthToken token = new AuthToken();
            JSONObject result = communicator.sendRegisterRequest();
            token.setAuthorization(result.getString("authToken"));
            token.setUserName(result.getString("userName"));
            token.setPersonID(result.getString("personID"));
            this.serverData.setToken(token);
            this.serverData.setUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
