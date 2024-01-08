package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.TheAlchemistGame;
import enums.Avatar;
import enums.BroadcastAction;
import enums.View;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import ui.framework.VComponent;

public class VLobby extends VComponent implements IBroadcastListener {
    public VLobby(TheAlchemistGame game) { 
        super(game);
    }
    private JPanel avatars = new JPanel(null);

    @Override
    protected void mounted() {
        updatePlayers();
        game.addBroadcastListener(this);
    }

    @Override
    protected void unmounted() {
        game.removeBroadcastListener(this);
    }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.Lobby);
        JLabel background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, BBackground.getWidth(), BBackground.getHeight());

        JLabel title = new JLabel("The KU Alchemist");
        title.setFont(new Font("Crimson Pro", Font.BOLD, 64));
        title.setForeground(Color.white);
        title.setBounds(78, 48, 512, 78);
        
        JLabel onlineLabel = new JLabel("Online");
        onlineLabel.setFont(new Font("Crimson Pro", Font.BOLD, 64));
        onlineLabel.setForeground(new Color(255, 207, 36));
        onlineLabel.setBounds(78, 120, 512, 78);
        
        JLabel waitingLabel = new JLabel("Waiting for other players...");
        waitingLabel.setFont(new Font("Crimson Pro", Font.BOLD, 40));
        waitingLabel.setForeground(new Color(245, 241, 235));
        waitingLabel.setBounds(78, 374, 512, 78);


        JLabel info = new JLabel("The game will start once host starts the session.");
        info.setFont(new Font("Crimson Pro", Font.PLAIN, 18));
        info.setForeground(new Color(244, 239, 235));
        info.setBounds(78, 416, 512, 78);




        avatars.setBounds(0, 526, windowDimension.getWidth(), 241);
        avatars.setOpaque(false);


        panel.add(title);
        panel.add(onlineLabel);
        panel.add(waitingLabel);
        panel.add(info);
        panel.add(avatars);
        panel.add(background);
    }

 

    @Override
    public void onBroadcast(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        if (action == BroadcastAction.PLAYER_CREATED) updatePlayers();
    }



    private void updatePlayers() {
        avatars.removeAll();
        avatars.setLayout(null);

        game.online.getPlayerNames().forEach((i, name) -> {
            int id = Integer.parseInt(i);
            Avatar avatar = game.online.getAvatar(id); 
            BufferedImage BAvatar = assetLoader.getAvatarImage(avatar);
            JLabel avatarImage = new JLabel(new ImageIcon(BAvatar));
            avatarImage.setBounds(90 + id * (BAvatar.getWidth() + 65), 56, BAvatar.getWidth(), BAvatar.getHeight());

            avatars.add(avatarImage);
        });

        avatars.revalidate();
        avatars.repaint();
    }
}
