package tp3;

import java.io.*;
import java.net.*;

public class ClientSimple {
    public static void main(String[] args) {
        try {
        	
        	// Remplace ladresse de  localhost par l' adresse IP du serveur
        	InetAddress serverIP = InetAddress.getByName("192.168.1.16"); // ladresse ip wifi du serveur
        	int serverPort = 1234;
        	Socket socket = new Socket(serverIP, serverPort);

           // Socket socket = new Socket("localhost", 1234); // si le serveur est  sur le  même PC
        	
        	
            System.out.println("✅ Connecté au serveur !");
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            
            // recevoir message du serveur
            System.out.println("Serveur : " + input.readLine());
            
            // envoyer messages au serveur
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                System.out.print("To server > ");
                message = console.readLine();
                if (message.equalsIgnoreCase("exit")) break;
                output.println(message);
            }
            
            socket.close();
            System.out.println("👋 Déconnexion du serveur.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
