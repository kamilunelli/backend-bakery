package controller;

import view.InicioView;
import view.JanelaPrincipalView;

public class PrincipalController {
    
    //main para iniciar as views
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new InicioView().setVisible(true);
        });
    }
    
    
}
