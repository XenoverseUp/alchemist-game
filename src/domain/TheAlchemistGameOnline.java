package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Avatar;
import enums.DeductionToken;
import enums.GamePhase;
import enums.Potion;
import error.NotEnoughActionsException;
import error.ServerSideException;
import error.WrongGameRoundException;
import interfaces.IGameRegister;
import net.Client;

public class TheAlchemistGameOnline implements IGameRegister {
    private Client client;
    private Board staticBoard = new Board(null);

    public TheAlchemistGameOnline(Client client) {
        this.client = client;
    }

    public int getId() {
        if (client != null)
            return client.getId();
        return 0;
    }

    public boolean isHost() {
        return getId() == 0;
    }

    @Override
    public int createUser(String name, Avatar avatar) {
        return client.createUser(client.getId(), name, avatar);
    }

    @Override
    public Map<String, String> getPlayerNames() {
        return client.getPlayerNames();
    }

    @Override
    public Map<String, String> getPlayerScores() {
        return client.getPlayerScores();
    }

    @Override
    public Avatar getPlayerAvatar(int id) {
        return client.getAvatar(id);
    }

    @Override
    public int initializeGame() {
        try {
            client.startGame();
        } catch (ServerSideException e) {
            return 1;
        }

        return 0;
    }

    public void finishGame() {
        client.finishGame();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<ArtifactCard> getArtifactCardDeck() {
        return (ArrayList<ArtifactCard>) this.staticBoard.artifactCardDeck.getArtifactCardDeck().clone();
    }

    @Override
    public String getCurrentPlayerName() {
        return client.getCurrentUser(true).get("name");
    }

    public Map<String, String> getCurrentUser(boolean cached) {
        return client.getCurrentUser(cached);
    }

    public Map<String, String> getCurrentUser() {
        return client.getCurrentUser();
    }

    @Override
    public GamePhase getPhase() {
        return client.getPhase(true);
    }

    @Override
    public void toggleCurrentUser() {
        try {
            client.toggleCurrentUser();
        } catch (ServerSideException e) {
            e.printStackTrace();
        }
        revalidateCache();
    }

    public void revalidateCache() {
        client.getCache().revalidateAll();
    }

    @Override
    public void forageIngredient() throws NotEnoughActionsException {
        client.forageIngredient();
        revalidateCache();
    }

    @Override
    public int drawMysteryCard() throws NotEnoughActionsException {
        int result = client.drawMysteryCard();
        revalidateCache();
        return result;
    }

    @Override
    public int buyArtifact(String name) throws NotEnoughActionsException {
        int result = client.buyArtifact(name);
        revalidateCache();
        return result;
    }

    @Override
    public List<String> getCurrentPlayerArtifacts() {
        return client.getCurrentPlayerArtifacts();
    }

    @Override
    public List<String> getCurrentPlayerIngredients() {
        return client.getCurrentPlayerIngredients();
    }

    @Override
    public void transmuteIngredient(String name) throws NotEnoughActionsException {
        client.transmuteIngredient(name);
        revalidateCache();
    }

    @Override
    public void discardArtifact(String name) throws NotEnoughActionsException {
        client.discardArtifact(name);
        revalidateCache();
    }

    public boolean isYourTurn() {
        return Integer.parseInt(this.getCurrentUser(true).get("id")) == client.getId();
    }

    @Override
    public int getCurrentPlayerActions() {
        return Integer.parseInt(this.getCurrentUser(true).get("left-actions"));
    }

    @Override
    public int getCurrentPlayerGold() {
        return Integer.parseInt(this.getCurrentUser(true).get("gold"));
    }

    @Override
    public int getCurrentPlayerReputation() {
        return Integer.parseInt(this.getCurrentUser(true).get("reputation"));
    }

    @Override
    public int getCurrentPlayerSickness() {
        return Integer.parseInt(this.getCurrentUser(true).get("sickness"));
    }

    @Override
    public Avatar getCurrentPlayerAvatar() {
        return Avatar.valueOf(this.getCurrentUser(true).get("avatar"));
    }

    @Override
    public int[][] getDeductionTable() {
        return client.getCurrentPlayerDeduction();
    }

    @Override
    public Potion makeExperiment(String ingredientName1, String ingredientName2, String testOn)
            throws WrongGameRoundException, NotEnoughActionsException, Exception {
        return client.makeExperiment(ingredientName1, ingredientName2, testOn);
    }

    public void toggleDeductionTable(String name, int tableIndex) {
        client.toggleDeductionTable(name, tableIndex);
    }

    @Override
    public HashMap<String[], DeductionToken> getDeductionTokens() {
        return client.getDeductionTokens();
    }

    @Override

    public void setCard(int i) {

        try {
            client.setCard(i);
        } catch (ServerSideException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void setMarker(int i) {

        try {
            client.setMarker(i);
        } catch (ServerSideException e) {

            e.printStackTrace();
        }

    }

    @Override
    public int publishTheory() {

        try {
            return client.publishTheory();
        } catch (ServerSideException e) {

            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int debunkTheory() {

        try {
            return client.debunkTheory();
        } catch (ServerSideException e) {

            e.printStackTrace();
        }
        return 0;

    }

    @Override
    public int getMarkerID(int CardId) {

        try {
            return client.getMarkerId(CardId);
        } catch (ServerSideException e) {

            e.printStackTrace();
        }
        return (Integer) null;

    }

    @Override
    public void activateArtifact(String name) throws ServerSideException {
        client.activateArtifact(name);
    }

    @Override
    public void removeArtifactCardAfterUsing(String name) throws ServerSideException {
        client.removeArtifactCardAfterUsing(name);
    }

    @Override
    public void paralyseEveryone() throws ServerSideException {
        client.paralyseEveryone();
    }

    @Override
    public boolean hasArtifactCard(String name) throws ServerSideException {
        return client.hasArtifactCard(name);
    }

    @Override
    public void swapAfterIndex(int first, int second, int third) {
        client.swapAfterIndex(first, second, third);
    }

    @Override
    public List<String> getIngredients() {
        return client.getIngredients();
    }

    public ArrayList<Integer> calculateWinner() {
        return client.calculateWinner();
    }

    @Override
    public void restart() {
        client.restart();
    }

}
