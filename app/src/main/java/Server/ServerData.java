package Server;

import Model.AuthToken;
import Model.User;

public class ServerData {
    private static ServerData instance = null;
    private User user;
    private User newUser;
    private AuthToken token;
    private String serverPort;
    private String serverHost;
    private boolean loggedIn;
    private boolean toastThrown = false;

    private ServerData() {
        loggedIn = false;
    }

    public static ServerData getInstance() {
        if (instance == null) {
            instance = new ServerData();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser() {
        this.user = this.newUser;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isToastThrown() {
        return toastThrown;
    }

    public void setToastThrown(boolean toastThrown) {
        this.toastThrown = toastThrown;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}

