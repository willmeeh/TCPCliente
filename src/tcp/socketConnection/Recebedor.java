/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.socketConnection;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tcp.main.Constantes;
import tcp.ui.MainUiFrame;

/**
 *
 * @author will
 */
public class Recebedor implements Runnable {

    private InputStream entrada;
    private Scanner entradaListener;

    public Recebedor(InputStream entrada) {
        this.entrada = entrada;
    }

    public void run() {
        entradaListener = new Scanner(this.entrada);
        System.out.println("s");
        // O scanner fica a espera de mensagens do servidor
        while (entradaListener.hasNextLine()) {
            // Seta a resposta do servidor na tela
            MainUiFrame.lblSaida.setText(entradaListener.nextLine());
        }
        
        // instancia uma thread para verificar se a conexão continua ativa
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isConnected = true;
                
                while(isConnected) {
                    
                    if (!entradaListener.hasNext()) {
                        JOptionPane.showMessageDialog(null, "A conexão com o servidor foi encerrada!");
                        MainUiFrame.lblSaida.setText(Constantes.MSG_DEFAULT_END_CONNECTION);
                        isConnected = false;
                        Cliente.setStatus(Constantes.STATUS_CONNECTION_FAILED);
                    }
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
}
