/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.socketConnection;

import java.io.InputStream;
import java.util.Scanner;
import tcp.ui.MainUiFrame;

/**
 *
 * @author will
 */
public class Recebedor implements Runnable {

    private InputStream servidor;

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    public void run() {
        // recebe msgs do servidor e imprime na tela
        Scanner s = new Scanner(this.servidor);
        System.out.println("s");
        while (s.hasNextLine()) {
            MainUiFrame.lblSaida.setText(s.nextLine());
        }
    }
}
