package ui;

import ui.framework.VComponent;
import ui.util.WrapLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.TheAlchemistGame;
import enums.View;

public class VPotionBrewingArea extends VComponent {
    private ArrayList<String> selected = new ArrayList<>() {{
        add(null);
        add(null);
    }};

    private ArrayList<JButton> previewButtons = new ArrayList<>() {{
        add(new JButton());
        add(new JButton());
    }};

    public VPotionBrewingArea(TheAlchemistGame game) {
        super(game);
    }

    @Override
    protected void mounted() {
        selected = new ArrayList<>() {{
            add(null);
            add(null);
        }};
        this.updatePreviews();
    }

    @Override
    protected void render() {
        Image BClose = assetLoader.getCloseIcon();
        JButton close = new JButton(new ImageIcon(BClose));
        close.setBounds(
            windowDimension.getWidth() - BClose.getWidth(null) - 10, 
            10, 
            BClose.getWidth(null), 
            BClose.getHeight(null)
        );
        close.addActionListener(e -> router.to(View.Board));

        Image BTitle = assetLoader.getPageTitle();
        JLabel title = new JLabel(new ImageIcon(BTitle));
        title.setBounds(windowDimension.getWidth() / 2 - BTitle.getWidth(null) / 2 + 473 / 2, -16, BTitle.getWidth(null), BTitle.getHeight(null));

        JLabel titleText = new JLabel("Potion Brewing Area", SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds(title.getBounds());

        JPanel selectionPanel = createSelectionPanel();
        selectionPanel.setBounds(0,0, 473, windowDimension.getHeight());

        JLabel background = new JLabel(new ImageIcon(assetLoader.getBackground(View.PotionBrewingArea)));
        background.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());


        
        for (int i = 0; i < 2; i++) {
            previewButtons.get(i).setBounds(560, 103 + i * 150, 68, 68);
            previewButtons.get(i).setOpaque(false);
            previewButtons.get(i).setContentAreaFilled(false);
            previewButtons.get(i).setBorderPainted(false);
            previewButtons.get(i).setFocusable(false);
            final int index = i;

            previewButtons.get(index).addActionListener(e -> {
                setIngredient(index, null);
            });

            panel.add(previewButtons.get(i));
        }

        panel.add(titleText);
        panel.add(title);
        panel.add(close);
        panel.add(selectionPanel);
        panel.add(background);
    }


    private JPanel createSelectionPanel() {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setOpaque(false);

        JLabel info = new JLabel("<html>Pick 2 ingredients to make an experiment.<html>", SwingConstants.CENTER);
        info.setForeground(Color.white);
        info.setFont(new Font("Crimson Pro", Font.PLAIN, 18));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel cards = new JPanel(new WrapLayout(FlowLayout.CENTER));
        cards.setMaximumSize(new Dimension(9999, 600));
        cards.setOpaque(false);

        assetLoader.ingredientNames.forEach(name -> {
            Image cardImage = assetLoader.getIngredientCard(name, 0.45);
            JButton card = new JButton(new ImageIcon(cardImage));
            card.setOpaque(false);
            card.setContentAreaFilled(false);
            card.setBorderPainted(false);
            card.setFocusable(false);
            cards.add(card);


            card.addActionListener(e -> {
                if (selected.contains(name)) {
                    for (int i = 0; i < 2; i++)
                        if (selected.get(i) != null && selected.get(i).equals(name)) 
                            setIngredient(i, null);  
                } else {
                    if (selected.get(0) != null && selected.get(1) != null) return;
                    
                    for (int i = 0; i < 2; i++) {
                        if (selected.get(i) == null) {
                            setIngredient(i, name);
                            break;
                        }
                    }
                }
            });

        });
        
        selectionPanel.add(Box.createVerticalGlue());
        selectionPanel.add(info);
        selectionPanel.add(Box.createVerticalStrut(24));
        selectionPanel.add(cards);
        selectionPanel.add(Box.createVerticalGlue());

        return selectionPanel;

    }

    private void setIngredient(int index, String name) {
        selected.set(index, name);
        this.updatePreviews();
    }

    private void updatePreviews() {
         for (int i = 0; i < 2; i++) {
            previewButtons.get(i).setIcon(null);
            if (selected.get(i) != null) 
                previewButtons.get(i).setIcon(new ImageIcon(assetLoader.getIngredientIcon(selected.get(i))));
        }
    }

}
