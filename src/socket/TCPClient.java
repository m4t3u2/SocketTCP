package socket;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    public static void main(String args[]) throws Exception {

    }

    public static void enviaMensagem(String msg) {
        Socket socket = null;
        try {
            int serverPort = 6789;
            socket = new Socket("127.0.0.1", serverPort);
            DataInputStream dI = new DataInputStream(socket.getInputStream());
            DataOutputStream dO = new DataOutputStream(socket.getOutputStream());
            dO.writeUTF(msg);
            String dados = dI.readUTF();
            System.out.println("\nResposta: \n" + dados);

        } catch (SocketException e) {
            System.out.println("TCPCliente Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("TCPCliente IO: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void passaCaminho() throws Exception {
        System.out.println("Especifique o caminho local do pacote: ");
        Scanner caminho = new Scanner(System.in);
        String caminhoOrigemPacote = caminho.next();
        enviaPacote(caminhoOrigemPacote);
    }

    public static void enviaPacote(String caminhoRecebido) throws Exception {
        String caminhoPacote = caminhoRecebido;
        File pacote = new File(caminhoPacote);
        FileInputStream in = new FileInputStream(pacote);
        Socket socket = new Socket("127.0.0.1", 6770);
        OutputStream out = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);
        BufferedWriter writer = new BufferedWriter(osw);
        writer.write(pacote.getName() + "\n");
        writer.flush();
        System.out.print("Aguarde até que seja enviado!");
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        System.out.println("\nEnviado.");
    }
}
