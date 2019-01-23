package socket;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        int op;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Trabalhar como:\n");
        System.out.println("1 - Servidor\n");
        System.out.println("2 - Cliente\n");
        System.out.println("3 - Sair\n");
        System.out.print("Opção: ");
        op = entrada.nextInt();

        switch (op) {
            case 1:
                menuServidor(args);
                break;

            case 2:
                menuCliente();
                break;

            case 3:
                break;

        }
    }

    public static void menuCliente() throws Exception {
        int opc;
        Scanner entrada = new Scanner(System.in);
        do {
            System.out.println("\n\nEscolha uma opção:\n");
            System.out.println("1 - Listas Disponíveis no Servidor\n");
            System.out.println("2 - Enviar Pacote para o Servidor\n");
            System.out.println("3 - Quebrar Senha\n");
            System.out.println("4 - Sair\n");

            System.out.print("Escolha: ");
            opc = entrada.nextInt();

            switch (opc) {
                case 1:
                    TCPClient.enviaMensagem("listas");
                    break;

                case 2:
                    TCPClient.passaCaminho();
                    break;

                case 3:
                    quebrarSenha();
                    break;

                case 4:
                    break;

            }
        } while (opc != 4);
    }

    public static void menuServidor(String[] args) {
        System.out.println("\nServidor Iniciado.");
        System.out.println("Aguardando comando dos clientes...\n");
        TCPServer.main(args);
    }

    public static void quebrarSenha() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\nDigite o nome da Lista: ");
        String nomeLista = entrada.nextLine();
        System.out.println("Digite o nome do Pacote enviado: ");
        String nomePacote = entrada.nextLine();
        String aux = "#" + nomeLista + "#" + nomePacote + "#";
        TCPClient.enviaMensagem("inicia" + aux);
    }

}
