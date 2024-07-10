import javax.swing.*;

public class App {
    public static void main(String[] args) {
        //invoke later insures that the GUI is created and dupdated in a thread-safe maner
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MorseCodeTranslatorGUI().setVisible(true);

            }
        });
    }
}
