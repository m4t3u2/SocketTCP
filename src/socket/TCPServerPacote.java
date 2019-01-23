package socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServerPacote extends Thread {

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(6770);
            while (true) {
                Socket clSocket = server.accept();
                InputStream in = clSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(isr);
                String pacote = reader.readLine();
                File f1 = new File("/home/mateus/servidor/pacotesRecebidos/" + pacote);
                FileOutputStream out = new FileOutputStream(f1);
                System.out.println("\nRecebendo pacote de um cliente.");
                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
        } catch (IOException e) {
            System.out.println("TCPServerPacote: " + e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPServerPacote.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
