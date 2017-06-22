/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.socketConnection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import tcp.main.Constantes;
import tcp.ui.MainUiFrame;

/**
 *
 * @author will
 */
public class Cliente {

    private String ip;
    private int porta;

    public static int status;

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        Cliente.status = status;
    }

    private PrintStream saida;

    public Cliente(String host, int porta) {
        this.ip = host;
        this.porta = porta;
    }

    /**
     * A partir do ip e da porta informados pelo usuário, instancia uma nova
     * conexão via socket.
     *
     * Esta funcao retorna se o servidor conseguiu ou nao se conectar
     *
     * @return status do servidor
     * @throws IOException
     */
    public int conectaServidor() throws IOException {
        Socket cliente;
        cliente = new Socket(this.ip, this.porta);

        int status = Constantes.STATUS_CONNECTION_FAILED;
        if (cliente.isConnected()) {
            status = Constantes.STATUS_CONNECTION_SUCCESSFUL;
            saida = new PrintStream(cliente.getOutputStream());
        } else {
            status = Constantes.STATUS_CONNECTION_FAILED;
        }

        // Instancia a thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        setStatus(status);

        return status;
    }

    /**
     * Metodo que envia a mensagem recebida por parametro para o servidor
     *
     * @param message
     * @throws IOException
     */
    public void enviarMensagem(String message) throws IOException {
        if (message != null) {
            if (getStatus() == Constantes.STATUS_CONNECTION_SUCCESSFUL) {
                // inexplicavelmente, tive que duplicar a linha, se nao so funcionava com dois cliques para enviar
                saida.println(message);
                saida.println(message);
                saida.flush();
                
                MainUiFrame.lblSaida.setText(Constantes.MSG_DEFAULT_WAIT_ANSWER);
            } else {
                MainUiFrame.lblSaida.setText(Constantes.MSG_DEFAULT_END_CONNECTION);
            }
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
