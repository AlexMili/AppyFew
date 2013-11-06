package adchance;

import org.littleshoot.proxy.*;

public class BasicAuthenticator implements ProxyAuthenticator {


    String u;
    String p;

    public BasicAuthenticator(String u,String p){
	this.u = u;
	this.p = p;
    }

    protected String getUsername() {
        return u;
    }

    protected String getPassword() {
        return p;
    }

    @Override
    public boolean authenticate(String userName, String password) {
        return getUsername().equals(userName) && getPassword().equals(password);
    }

}
