package domain;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import error.HostDoesNotExistsException;
import interfaces.IBroadcastListener;
import interfaces.IGameRegister;
import net.Client;
import net.Server;

public class Game {
    private IGameRegister register = new TheAlchemistGame();
    private ApplicationType type = ApplicationType.Local; 
    private Client client = null;
    private ServerSocket serverSocket = null;
    private Server server = null;

    private enum ApplicationType {
        Local,
        Online
    }    

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public IGameRegister getRegister() {
        return register;
    }

    public TheAlchemistGameOnline getOnlineRegister() {
        return ((TheAlchemistGameOnline)this.register);
    }
   
    public TheAlchemistGame getLocalRegister() {
        return ((TheAlchemistGame)this.register);
    }

    public boolean isOnline() {
        return type == ApplicationType.Online;
    }

    public int createServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            server = new Server(serverSocket);
            server.start();
            this.connectToServer(port);
        } catch (BindException e) {
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    public int connectToServer(int port) {
        try {
            this.client = new Client(port);
            this.client.listen();
            this.setType(ApplicationType.Online);
            this.register = new TheAlchemistGameOnline(client);
        } catch (HostDoesNotExistsException e) {
            return 1;
        }

        return 0;
    }

    public int connectToServer(String host, int port) {
        try {
            this.client = new Client(host, port);
            this.client.listen();
            this.setType(ApplicationType.Online);
            this.register = new TheAlchemistGameOnline(client);
        } catch (HostDoesNotExistsException e) {
            return 1;
        }

        return 0;
    }

    public void dispose() {
        try {
            serverSocket.close();
            server.closeServer();
            client.shutdown();
            setType(ApplicationType.Local);
            register = new TheAlchemistGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addBroadcastListener(IBroadcastListener component) {
        if (isOnline() && this.client != null)
            client.addBroadcastListener(component);
    }
}
