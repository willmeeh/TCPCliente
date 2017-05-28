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

    private PrintStream saida;
    private InputStreamReader entrada;

    private int status;

    private Socket socket = null;

    public Cliente(String host, int porta) {
        this.ip = host;
        this.porta = porta;
    }

    public int getStatus() {
        return this.status;
    }

    public void conectaServidor() throws IOException {
        Socket cliente;
        cliente = new Socket(this.ip, this.porta);

        this.status = Constantes.STATUS_CONNECTION_SUCCESSFUL;

        saida = new PrintStream(cliente.getOutputStream());

        System.out.println("O cliente se conectou ao servidor!fffff");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

    }

    public void enviarMensagem(String message) throws IOException {
        if (message != null) {
            //out.write(message);
            //out.flush();
            // inexplicavelmente, tive que duplicar a linha, se nao so funcionava com dois cliques para enviar
            saida.println(message);
            saida.println(message);

            saida.flush();

        }
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
