/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.socketConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcp.main.Constantes;

/**
 *
 * @author will
 */
public class Cliente {

    private String ip;
    private int porta;
    
    PrintWriter saida;
    InputStreamReader entrada;

    private int status;

    private Socket socket = null;

    public Cliente(String host, int porta) {
        this.ip = host;
        this.porta = porta;
    }

    public void conectarServidor() {
        if (socket == null) {
            try {
                socket = new Socket(this.ip, this.porta);
                this.status = Constantes.STATUS_CONNECTION_SUCCESSFUL;

                saida = new PrintWriter(socket.getOutputStream());
                entrada = new InputStreamReader(socket.getInputStream());

            } catch (IOException ex) {
                this.status = Constantes.STATUS_CONNECTION_FAILED;
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public int getStatus() {
        return this.status;
    }

    public void executa() throws UnknownHostException, IOException {
        Socket cliente = new Socket(this.ip, this.porta);
        System.out.println("O cliente se conectou ao servidor!");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        // lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }

        saida.close();
        teclado.close();
        cliente.close();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

   

}
