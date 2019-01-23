package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static socket.TCPServerComando.comandoRecebido;

public class TCPServerComando extends Thread {

    @Override
    public void run() {
        ServerSocket serverTCP = null;
        try {
            serverTCP = new ServerSocket(6789);
            while (true) {
                Socket socket = serverTCP.accept();
                Connection c = new Connection(socket);
            }

        } catch (SocketException e) {
            System.out.println("TCPServerComando Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("TCPServerComando IO: " + e.getMessage());
        } finally {
            if (serverTCP != null) {
                try {
                    serverTCP.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPServerComando.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public static String comandoRecebido(String recebida) {
        if (recebida.contains("listas")) {
            recebida = "ls /home/mateus/servidor/listasExistentes";
        }
        if (recebida.contains("inicia")) {
            String[] array = recebida.split("#");
            recebida = "aircrack-ng -w /home/mateus/servidor/listasExistentes/" + array[1] + " /home/mateus/servidor/pacotesRecebidos/" + array[2];
        }
        return recebida;
    }

    public byte[] executarComando(String comando) throws IOException {
        Process p = Runtime.getRuntime().exec(comando);
        java.util.Scanner s = new java.util.Scanner(p.getInputStream());
        int i = 0;
        String resp = new String();

        while (s.hasNextLine()) {
            resp = resp + (++i) + ": ";
            resp = resp + s.nextLine() + "\n";
        }
        System.out.println("\nEnviado resposta para um cliente.\n");
        int r = resp.getBytes().length;
        byte[] m = new byte[r];
        m = resp.getBytes();

        return m;
    }
}

class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket socket;

    public Connection(Socket aSocket) {
        try {
            socket = aSocket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("TCP Connection: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String data = in.readUTF();
            String temp = new String(data);
            TCPServerComando server = new TCPServerComando();
            temp = comandoRecebido(temp);
            byte[] novoBuffer = new byte[1000];
            novoBuffer = server.executarComando(temp);
            String aux = new String(novoBuffer);
            out.writeUTF(aux);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
