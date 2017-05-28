/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.main;

import tcp.ui.ServerConnectionFrame;
import java.net.Socket;
/**
 *
 * @author will
 */
public class TCPCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerConnectionFrame mainFrame = new ServerConnectionFrame();
        //centraliza a janela
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
