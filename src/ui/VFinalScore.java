package ui;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.Game;
import enums.Avatar;
import enums.View;
import ui.framework.VComponent;

public class VFinalScore extends VComponent {

    private JPanel controls = new JPanel(null);
    private JPanel winner = new JPanel(null);
    public VFinalScore(Game game) { super(game); }

    @Override
    public void mounted(){
        ArrayList<Integer> winnerIds = game.getRegister().calculateWinner();
        Map<String, String> playerNames = game.getRegister().getPlayerNames();
        Map<String, String> playerScores = game.getRegister().getPlayerScores(); 

        int winnerId = winnerIds.get(0);
        String winnerScore = playerScores.get(String.valueOf(winnerId));
        String winnerName = playerNames.get(String.valueOf(winnerId));
        Avatar winnerAvatar = game.getRegister().getPlayerAvatar(winnerId);

        BufferedImage BAvatarWinner = assetLoader.getAvatarImageBig(winnerAvatar);
        JLabel winnerAvatarImage = new JLabel(new ImageIcon(BAvatarWinner));
        winnerAvatarImage.setBounds(350, 157, BAvatarWinner.getWidth(), BAvatarWinner.getHeight());

        JLabel winnerNameLabel = new JLabel(winnerName + ": " + winnerScore, SwingConstants.CENTER);
        winnerNameLabel.setBounds(316, 395, 316, 70);
        winnerNameLabel.setFont(new Font("Crimson Pro", Font.BOLD, 18));
        winnerNameLabel.setForeground(assetLoader.getNameRibbonColor(1));

        winner.add(winnerNameLabel);
        winner.add(winnerAvatarImage);

        playerNames.forEach((i, name) -> {
            int id = Integer.parseInt(i);
            Avatar avatar = game.getRegister().getPlayerAvatar(id);
            String finalScore = playerScores.get(i);

            BufferedImage BAvatar = assetLoader.getAvatarImage(avatar);
            JLabel avatarImage = new JLabel(new ImageIcon(BAvatar));
            avatarImage.setBounds(90 + id * (BAvatar.getWidth() + 65), 56, BAvatar.getWidth(), BAvatar.getHeight());

            BufferedImage BRibbon = assetLoader.getNameRibbon(id);
            JLabel nameRibbon = new JLabel(new ImageIcon(BRibbon));
            nameRibbon.setBounds(72 + id * (BRibbon.getWidth() + 30), 169, BRibbon.getWidth(), BRibbon.getHeight());
            
            JLabel nameLabel = new JLabel(name + ": " + finalScore, SwingConstants.CENTER);
            nameLabel.setBounds(72 + id * (BRibbon.getWidth() + 30), 168, BRibbon.getWidth(), BRibbon.getHeight() - 6);
            nameLabel.setFont(new Font("Crimson Pro", Font.BOLD, 18));
            nameLabel.setForeground(assetLoader.getNameRibbonColor(id));

            if(winnerIds.contains(id)){
                BufferedImage BCrown = assetLoader.getCrown();
                JLabel crown = new JLabel(new ImageIcon(BCrown));
                crown.setBounds(105 + id * (BCrown.getWidth() + 95), 3, BCrown.getWidth(), BCrown.getHeight());
                controls.add(crown);
            }

            controls.add(nameLabel);
            controls.add(nameRibbon);
            controls.add(avatarImage);
        });

        if ((game.isOnline() && game.getOnlineRegister().isHost()) || (!game.isOnline())){
            BufferedImage BButton = assetLoader.getFinishButton(false);
            JButton finishButton = new JButton(new ImageIcon(BButton));
            finishButton.setContentAreaFilled(false);
            finishButton.setOpaque(false);
            finishButton.setBorderPainted(false);
            finishButton.setFocusable(false);
            finishButton.setBounds(1089, 44, BButton.getWidth(), BButton.getHeight());
            finishButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { 
                    //TODO
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    finishButton.setIcon(new ImageIcon(assetLoader.getFinishButton(true)));
                }
                    
                @Override
                public void mouseReleased(MouseEvent e) {
                    finishButton.setIcon(new ImageIcon(assetLoader.getFinishButton(false)));
                }
            });
            controls.add(finishButton);
        }
    }

    @Override
    protected void render() {
        BufferedImage BBackground = assetLoader.getBackground(View.FinalScore);
        JLabel background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, BBackground.getWidth(), BBackground.getHeight());

        controls.setBounds(0, 526, windowDimension.getWidth(), 241);
        controls.setOpaque(false);

        winner.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight()-241);
        winner.setOpaque(false);
        panel.add(winner);
        panel.add(controls);
        panel.add(background);
    }

    


    
}
