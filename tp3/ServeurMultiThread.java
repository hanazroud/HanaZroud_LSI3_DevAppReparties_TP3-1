package tp3;

import java.io.*;
import java.net.*;

public class ServeurMultiThread {
    private static int clientCount = 0; // Compteur de clients

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("✅ Serveur démarré sur le port 1234...");
            
            while (true) {
                Socket socket = serverSocket.accept(); // attendre un client
                clientCount++;
                System.out.println("➡️ Client n°" + clientCount + " connecté depuis : " + socket.getRemoteSocketAddress());
                
                // créer un thread pour ce client
                Thread t = new Thread(new ClientHandler(socket, clientCount));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Classe pour gérer un client dans un thread séparé
class ClientHandler implements Runnable {
    private Socket socket;
    private int clientNumber;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Bienvenue ! Vous êtes le client n°" + clientNumber);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("📩 Message du client " + clientNumber + ": " + msg);
                out.println("Message reçu : " + msg);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("❌ Erreur avec le client " + clientNumber);
        }
    }
}
