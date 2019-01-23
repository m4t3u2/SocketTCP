package socket;

public class TCPServer {

    public static void main(String args[]) {
        TCPServerComando iniciaServidorComando = new TCPServerComando();
        TCPServerPacote iniciaServidorPacote = new TCPServerPacote();
        iniciaServidorComando.start();
        iniciaServidorPacote.start();
    }
}
