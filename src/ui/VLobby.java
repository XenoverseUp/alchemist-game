package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.Game;
import enums.Avatar;
import enums.BroadcastAction;
import enums.View;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;
import ui.framework.VComponent;

public class VLobby extends VComponent implements IBroadcastListener {
    public VLobby(Game game) { 
        super(game);
    }
    private JPanel controls = new JPanel(null);
    private JLabel waitingLabel;

    @Override
    protected void mounted() {
        update();
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
        
        waitingLabel = new JLabel("Waiting for other players...");
        waitingLabel.setFont(new Font("Crimson Pro", Font.BOLD, 40));
        waitingLabel.setForeground(new Color(245, 241, 235));
        waitingLabel.setBounds(78, 374, 1024, 78);


        JLabel info = new JLabel("The game will start once host starts the session.");
        info.setFont(new Font("Crimson Pro", Font.PLAIN, 18));
        info.setForeground(new Color(244, 239, 235));
        info.setBounds(78, 416, 512, 78);


        controls.setBounds(0, 526, windowDimension.getWidth(), 241);
        controls.setOpaque(false);


        panel.add(title);
        panel.add(onlineLabel);
        panel.add(waitingLabel);
        panel.add(info);
        panel.add(controls);
        panel.add(background);
    }

    @Override
    protected void listenBroadcast() {
        if (game.isOnline()) game.addBroadcastListener(this);
    }


    @Override
    public void onBroadcast(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        if (router.getCurrentView() != View.Lobby) return;
        if (action == BroadcastAction.PLAYER_CREATED) update();
        if (action == BroadcastAction.ROOM_IS_FULL) waitingLabel.setText("Waiting for host to start the game...");
        if (action == BroadcastAction.GAME_STARTED) router.to(View.Board);
    }

    private void update() {
        controls.removeAll();
        controls.setLayout(null);

        Map<String, String> playerNames = game.getRegister().getPlayerNames();

        playerNames.forEach((i, name) -> {
            int id = Integer.parseInt(i);
            Avatar avatar = game.getRegister().getPlayerAvatar(id); 

            BufferedImage BAvatar = assetLoader.getAvatarImage(avatar);
            JLabel avatarImage = new JLabel(new ImageIcon(BAvatar));
            avatarImage.setBounds(90 + id * (BAvatar.getWidth() + 65), 56, BAvatar.getWidth(), BAvatar.getHeight());

            
            BufferedImage BRibbon = assetLoader.getNameRibbon(id);
            JLabel nameRibbon = new JLabel(new ImageIcon(BRibbon));
            nameRibbon.setBounds(72 + id * (BRibbon.getWidth() + 30), 169, BRibbon.getWidth(), BRibbon.getHeight());
            
            JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
            nameLabel.setBounds(72 + id * (BRibbon.getWidth() + 30), 168, BRibbon.getWidth(), BRibbon.getHeight() - 6);
            nameLabel.setFont(new Font("Crimson Pro", Font.BOLD, 18));
            nameLabel.setForeground(assetLoader.getNameRibbonColor(id));

            controls.add(nameLabel);
            controls.add(nameRibbon);
            controls.add(avatarImage);
        });

        if (game.isOnline() && game.getOnlineRegister().isHost() && playerNames.keySet().size() > 1) {
            BufferedImage BButton = assetLoader.getStartButton(false);
            
            JButton startButton = new JButton(new ImageIcon(BButton));
            startButton.setContentAreaFilled(false);
            startButton.setOpaque(false);
            startButton.setBorderPainted(false);
            startButton.setFocusable(false);
            startButton.setBounds(1220, 60, BButton.getWidth(), BButton.getHeight());

            startButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { 
                    int result = game.getRegister().initializeGame();
                    
                    if (result == 1) 
                        modal.info("Server is on fire!", "Quick, bring some water over there. (We have no idea what happened just now. Maybe wait and try again.)");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    startButton.setIcon(new ImageIcon(assetLoader.getStartButton(true)));
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    startButton.setIcon(new ImageIcon(assetLoader.getStartButton(false)));
                }
            });

            controls.add(startButton);
        }


        controls.revalidate();
        controls.repaint();
    }
}
