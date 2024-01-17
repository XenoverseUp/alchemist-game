package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.ArtifactCard;
import domain.Game;
import enums.View;
import error.NotEnoughActionsException;
import error.ServerSideException;
import ui.framework.VComponent;
import ui.util.WrapLayout;

public class VElixirofInsight extends VComponent {
    private JLabel titleText;
    private JLabel background;

    private JScrollPane scrollPane;
    private int scrollPosition = 0;

    private BufferedImage BHammer;
    private BufferedImage BArtifactCardTemplate;
    private BufferedImage BDiscardArtifactCard;
    List<Integer> selectedValues = new ArrayList<>();

    public VElixirofInsight(Game game) {
        super(game);
    }

    @Override
    protected void render() {

        BufferedImage BBackground = assetLoader.getBackground(View.Inventory);
        Image BClose = assetLoader.getClose();
        Image BTitle = assetLoader.getPageBanner();

        try {
            BHammer = ImageIO.read(new File("./src/resources/image/HUD/hammer.png"));
            BArtifactCardTemplate = ImageIO.read(new File("./src/resources/image/artifactCard.png"));
            BDiscardArtifactCard = ImageIO.read(new File("./src/resources/image/HUD/discardArtifact.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        JLabel title = new JLabel(new ImageIcon(BTitle));
        title.setBounds(windowDimension.getWidth() / 2 - BTitle.getWidth(null) / 2, -16, BTitle.getWidth(null),
                BTitle.getHeight(null));

        titleText = new JLabel("", SwingConstants.CENTER);
        titleText.setForeground(Color.white);
        titleText.setFont(new Font("Itim-Regular", Font.BOLD, 20));
        titleText.setBounds(title.getBounds());

        JButton close = new JButton(new ImageIcon(BClose));
        close.setBounds(10, 10, BClose.getWidth(null), BClose.getHeight(null));
        close.addActionListener(e -> router.to(View.Board));

        background = new JLabel(new ImageIcon(BBackground));
        background.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, windowDimension.getWidth(), windowDimension.getHeight());
        scrollPane.setPreferredSize(windowDimension.getSize());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton actionButton = new JButton("Perform Action");
        actionButton.addActionListener(e -> {
            if (selectedValues.size() == 3) {
                game.getRegister().swapAfterIndex(selectedValues.get(0), selectedValues.get(1), selectedValues.get(2));
                this.update();
            }
        });

        panel.add(titleText);
        panel.add(title);
        panel.add(close);
        panel.add(scrollPane);
        panel.add(background);
    }

    @Override
    protected void mounted() {
        this.update();
    }

    @Override
    protected void unmounted() {
        this.scrollPosition = 0;
        scrollPane.getVerticalScrollBar().setValue(this.scrollPosition);
    }

    private void update() {
        selectedValues = new ArrayList<>();

        this.scrollPosition = scrollPane.getVerticalScrollBar().getValue();
        scrollPane.setViewportView(null);

        this.titleText.setText(String.format("%s's Inventory", game.getRegister().getCurrentPlayerName()));

        JPanel cards = new JPanel(new WrapLayout(FlowLayout.CENTER, 24, 24));
        cards.setOpaque(false);

        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 20));
        marginTop.setOpaque(false);
        cards.add(marginTop);

        JLabel ingredientsTitle = new JLabel("Last Three Ingredient Cards", SwingConstants.CENTER);
        ingredientsTitle.setFont(new Font("Crimson Pro", Font.BOLD, 36));
        ingredientsTitle.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
        ingredientsTitle.setForeground(Color.WHITE);
        cards.add(ingredientsTitle);

        game.getRegister()
                .getIngredients()
                .stream()
                .map(name -> this.generateIngredientCard(name))
                .forEach(cards::add);

        JPanel marginBottom = new JPanel();
        marginBottom.setPreferredSize(new Dimension(windowDimension.getWidth() - 100, 100));
        marginBottom.setOpaque(false);
        cards.add(marginBottom);

        scrollPane.setViewportView(cards);
        scrollPane.getVerticalScrollBar().setValue(this.scrollPosition);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private JPanel generateIngredientCard(String name) {
        BufferedImage cardBuffer = assetLoader.getIngredientCard(name);

        JPanel card = new JPanel(null);
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(cardBuffer.getWidth(), cardBuffer.getHeight()));

        JLabel bg = new JLabel(new ImageIcon(cardBuffer));
        bg.setBounds(0, 0, cardBuffer.getWidth(), cardBuffer.getHeight());

        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.setBounds(cardBuffer.getWidth() / 2 - BHammer.getWidth() / 4,
                cardBuffer.getHeight() - BHammer.getHeight() / 2 - 36, BHammer.getWidth() / 2, BHammer.getHeight() / 2);
        comboBox.setOpaque(false);
        comboBox.setFocusable(false);
        comboBox.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item
                int selectedValue = (Integer) comboBox.getSelectedItem();
                System.out.println("Selected Option: " + selectedValue);
                selectedValues.add(selectedValue);
            }
        });

        comboBox.addItem(1);
        comboBox.addItem(2);
        comboBox.addItem(3);
        card.add(comboBox);
        card.add(bg);

        return card;
    }

}
