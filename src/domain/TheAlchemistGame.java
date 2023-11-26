package domain;
import java.util.Scanner;
import enums.Avatar;

public class TheAlchemistGame {
    Auth auth = new Auth();

    public void initialize() {
        int currentUser = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to KU Alchemist Game! In this game, we will have many adventureous journeys.");
        
        while (currentUser < 2) {
            System.out.printf("Pick a name for the %s player: ", currentUser == 0 ? "first" : "second");
            String username;
            
            username = scanner.nextLine();
            
            while (username == null || username == "") {
                System.out.printf("Seems like you haven't picked one, yet. Pick a name for the %s player: ", currentUser == 0 ? "first" : "second");
                username = scanner.nextLine();
            }
            
            System.out.println("Well, now that you have a wizard name, pick an avatar. Here are the available avatars and default avatar is `Galactic`:\n");

            for (Avatar a: Avatar.values()) {
                System.out.println(a.toString());
            }
            
            Avatar avatar = Avatar.Galactic;
            String avatarName = scanner.nextLine();

            for (Avatar a : Avatar.values()) {
                if (a.name().equals(avatarName)) {
                    avatar = a;
                    break;
                }
            }

            
            int result = auth.createUser(username, avatar);
       
            if (result == 0) {
                currentUser++;
                continue;
            } else if (result == 1) 
                System.out.printf("Looks like we already have an alchemist named %s. Let's try one more time.\n", username);

            
        }

        scanner.close();

        System.out.println("Great! All alchemists are ready to brew. Let's move to our alchemy board to start the adventure.");

        int index = 1;
        for (Player p : auth.players) {
            System.out.println("\n===============================================");
            System.out.printf("Alchemist %d:\n", index);
            System.out.printf("Name: %s\n", p.name);
            System.out.printf("Avatar: %s\n", p.avatar.name());
            index++;
        }
        
        System.out.println("\n===============================================");
    }
    
}
