package domain;

import enums.Avatar;

public class TheAlchemistGame {
    public Auth auth;

    public TheAlchemistGame() {
    	auth = new Auth();
    }
    
    public void createUser(String userName, Avatar a) {
    	auth.createUser(userName, a);
    }

    public void initialize() {
        
    }
    
    
}
