package ui;

import ui.framework.VComponent;
import ui.util.WrapLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.Game;
import enums.BroadcastAction;
import enums.View;
import error.NotEnoughActionsException;
import error.WrongGameRoundException;
import interfaces.IBroadcastListener;
import interfaces.IDynamicTypeValue;

public class VPotionBrewingArea extends VComponent {
    private ArrayList<String> selectedIngredients = new ArrayList<String>() {
        {
            add(null);
            add(null);
        }
    };

    private ArrayList<JButton> previewButtons = new ArrayList<JButton>() {
        {
            add(new JButton());
            add(new JButton());
        }
    };

    private JLabel checkMark = new JLabel(new ImageIcon(assetLoader.getCheckMark()));
    private JPanel selectionPanel = new JPanel();

    private int testingMethod = -1;

    public VPotionBrewingArea(Game game) {
        super(game);
    }

    @Override
    protected void render() {
        Image BClose = assetLoader.getClose();
        JButton close = new JButton(new ImageIcon(BClose));
        close.setBounds(
                windowDimension.getWidth() - BClose.getWidth(null) - 10,
                10,
                BClose.getWidth(null),
                BClose.getHeight(null));
        close.addActionListener(e -> router.to(View.Board));

        Image BTitle = assetLoader.getPageBanner();
        JLabel title = new JLabel(new ImageIcon(BTitle));
        title.setBounds(windowDimension.getWidth() / 2 - BTitle.getWidth(null) / 2 + 473 / 2, -16,
                BTitle.getWidth(null), BTitle.getHeight(null));

        JLabel titleText = new JLabel("Potion Brewing Area", SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds(title.getBounds());

        selectionPanel.setBounds(0, 0, 473, windowDimension.getHeight());

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

        for (int i = 0; i < 3; i++) {
            final int index = i;

            JButton radioButton = new JButton();
            radioButton.setBounds(627 + i * 228, 400, 188, 250);
            radioButton.setOpaque(false);
            radioButton.setContentAreaFilled(false);
            radioButton.setBorderPainted(false);
            radioButton.setFocusable(false);
            radioButton.addActionListener(e -> {
                if (index == testingMethod)
                    return;

                testingMethod = index;
                updatePreviews();
            });
            panel.add(radioButton);
        }

        JButton submitButton = new JButton("Brew Potion");
        submitButton.setBounds(864, 700, 180, 36);
        submitButton.addActionListener(e -> this.submit());

        panel.add(submitButton);
        panel.add(checkMark);
        panel.add(titleText);
        panel.add(title);
        panel.add(close);
        panel.add(selectionPanel);
        panel.add(background);
    }

    @Override
    protected void mounted() {
        selectionPanel.removeAll();
        createSelectionPanel();
        selectedIngredients = new ArrayList<String>() {
            {
                add(null);
                add(null);
            }
        };
        testingMethod = -1;
        this.updatePreviews();
    }

    private void createSelectionPanel() {
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setOpaque(false);

        JLabel info = new JLabel(
                String.format("<html><div style='text-align: center'>%s</div><html>",
                        game.getRegister().getCurrentPlayerIngredients().isEmpty()
                                // String.format("<html><div style='text-align: center'>%s</div><html>",
                                // game.getLocalRegister().getCurrentPlayer().inventory.isEmpty()
                                ? "Forage some ingredients to make experiments on and improve your insights."
                                : "Pick 2 ingredients to make an experiment."),
                SwingConstants.CENTER);

        info.setForeground(Color.white);
        info.setFont(new Font("Crimson Pro", Font.PLAIN, 18));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setMaximumSize(new Dimension(360, 999));

        JPanel cards = new JPanel(new WrapLayout(FlowLayout.CENTER));
        cards.setMaximumSize(new Dimension(9999, 600));
        cards.setOpaque(false);

        assetLoader.ingredientNames.forEach(name -> {
            if (!game.getRegister().getCurrentPlayerIngredients().contains(name))
                return;
            // !game.getLocalRegister().getCurrentPlayer().inventory.hasIngredient(name))
            // return;

            Image cardImage = assetLoader.getIngredientCard(name, 0.45);

            JButton card = new JButton(new ImageIcon(cardImage));
            card.setOpaque(false);
            card.setContentAreaFilled(false);
            card.setBorderPainted(false);
            card.setFocusable(false);
            card.addActionListener(e -> {
                if (selectedIngredients.contains(name)) {
                    for (int i = 0; i < 2; i++)
                        if (selectedIngredients.get(i) != null && selectedIngredients.get(i).equals(name))
                            setIngredient(i, null);
                } else {
                    if (selectedIngredients.get(0) != null && selectedIngredients.get(1) != null)
                        return;

                    for (int i = 0; i < 2; i++) {
                        if (selectedIngredients.get(i) == null) {
                            setIngredient(i, name);
                            break;
                        }
                    }
                }
            });

            cards.add(card);

        });

        selectionPanel.add(Box.createVerticalGlue());
        selectionPanel.add(info);
        if (!game.getRegister().getCurrentPlayerIngredients().isEmpty())
            selectionPanel.add(Box.createVerticalStrut(24));
        // !game.getLocalRegister().getCurrentPlayer().inventory.isEmpty())
        // selectionPanel.add(Box.createVerticalStrut(24));

        selectionPanel.add(cards);
        selectionPanel.add(Box.createVerticalGlue());
    }

    private void setIngredient(int index, String name) {
        selectedIngredients.set(index, name);
        this.updatePreviews();
    }

    private void updatePreviews() {
        for (int i = 0; i < 2; i++) {
            previewButtons.get(i).setIcon(null);
            if (selectedIngredients.get(i) != null)
                previewButtons.get(i).setIcon(new ImageIcon(assetLoader.getIngredientIcon(selectedIngredients.get(i))));
        }

        checkMark.setBounds(712 + testingMethod * 227, 590, 35, 35);
        if (testingMethod == -1)
            checkMark.setVisible(false);
        else
            checkMark.setVisible(true);
    }

    private int validate() {
        for (String s : selectedIngredients) {
            if (s == null) {
                modal.info("Pony up and give!",
                        "Pick 2 ingredients from your inventory to make experiment and create mystical potions.");
                return 1;
            }
        }

        if (testingMethod == -1) {
            modal.info("Who will be the victim? Decide.",
                    "Picking a test method would be really useful to see the inner structure of the potion.");
            return 1;
        }

        return 0;
    }

    private void submit() {
        if (validate() == 1)
            return;

        String testOn = testingMethod == 0 ? "student" : testingMethod == 1 ? "self" : "sell";
        try {
            game.getRegister().makeExperiment(selectedIngredients.get(0), selectedIngredients.get(1), testOn);
            router.to(View.DeductionBoard);
        } catch (WrongGameRoundException e) {
            modal.info("Not Available in First Round", "Selling a potion is not available in First Round");
        } catch (NotEnoughActionsException e) {
            modal.info("No Actions Left", "For this round you don't have any actions left! Wait till next round!");
        } catch (Exception e) {
            System.out.println(e);
            if (e.getMessage().equals("enough-gold-student"))
                modal.info("You little poor!",
                        "To test a potion on a student you have to have at least one gold. You can still test it on yourself, tho.");
            else if (e.getMessage().equals("enough-gold-sell"))
                modal.info("You little poor!",
                        "To sell a potion you have to have at least two golds. But don't forget, you can still have a positive trade.");
        }
    }
}
