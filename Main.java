package conexão; // <-- ALTERE para o nome do seu pacote!

import view.MenuEscolaFrames;

public class Main {

    public static void main(String[] args) {

        // Garante que a interface abra na thread correta
        java.awt.EventQueue.invokeLater(() -> {
            MenuEscolaFrames tela = new MenuEscolaFrames();
            tela.setVisible(true);
        });
    }
}
