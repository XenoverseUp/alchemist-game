package domain;

import java.util.ArrayList;
import enums.Avatar;

public class Auth {
    public ArrayList<Player> players = new ArrayList<>(2);
    public int currentUser = 0;

    public int createUser(String name, Avatar avatar) {
        for (Player p : this.players) {
            if (p.name.equals(name))
                return 1;
        }

        Player player = new Player(this.players.size(), name, avatar);
        this.players.add(player);

        return 0;
    }

    public void toggleCurrentUser() {
        this.currentUser += 1;
        this.currentUser %= 2;
        System.out.println("It's " + players.get(currentUser).name + "'s turn.");
    }

}
