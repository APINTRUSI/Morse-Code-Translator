import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MorseCodeTranslatorGUI extends JFrame implements KeyListener{
    private JTextArea textImputArea, morseCodeArea;
    private MorseCodeController morseCodeController;

    public MorseCodeTranslatorGUI(){
        super("Morse code translator");

        setSize(540, 760); //setSize(new Dimension(540, 760));
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //changes the color of the background
        getContentPane().setBackground(Color.decode("#264653"));
        setLocationRelativeTo(null);//centers

        morseCodeController = new MorseCodeController();
        addGUIComponents();
    }


    void addGUIComponents(){
        //title

        JLabel titleLabel = new JLabel("Morse code Translator");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        //changes the font color of the text to white
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, 540, 100);

        //text input
        JLabel textImputLabel = new JLabel("Text:");
        textImputLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        textImputLabel.setForeground(Color.WHITE);
        textImputLabel.setBounds(20, 100, 200, 30);

        textImputArea = new JTextArea();
        textImputArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        // simulates padding of 10px in the text are
        textImputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // makes it so that words wrap to the next line after reaching the end of the test area
        textImputArea.setLineWrap(true);
        // makes it so that when the words do get wrap, the word does not split off
        textImputArea.setWrapStyleWord(true);
        //listening to keypresses whenever we are typing in this area
        textImputArea.addKeyListener(this);


        JScrollPane textImputScroll = new JScrollPane(textImputArea);
        textImputScroll.setBounds(20, 132, 484, 236);


        //mores code input

        JLabel morseCodeInputLabel = new JLabel("Morse Code:");
        morseCodeInputLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        morseCodeInputLabel.setForeground(Color.WHITE);
        morseCodeInputLabel.setBounds(20, 375, 200, 30);

        morseCodeArea = new JTextArea();
        morseCodeArea.setFont(new Font("Dialog", Font.PLAIN, 25));
        morseCodeArea.setEditable(false);
        morseCodeArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        morseCodeArea.setLineWrap(true);
        morseCodeArea.setWrapStyleWord(true);

        JScrollPane morseCodeScroll = new JScrollPane(morseCodeArea);
        morseCodeScroll.setBounds(20, 410, 484, 236);

        //play sound button
        JButton playSoundButton = new JButton("Play Sound");
        playSoundButton.setBounds(210, 665, 100, 30);
        playSoundButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //disable the play button (prevents the sound from getting interupted)
                playSoundButton.setEnabled(false);

                Thread playMorseCodeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //attempt to play the morse code sound
                        try {
                            String[] morseCodeMessage = morseCodeArea.getText().split(" ");
                            morseCodeController.playSound(morseCodeMessage);
                        }
                        catch (LineUnavailableException lineUnavailableException){
                            lineUnavailableException.printStackTrace();
                        }
                        catch (InterruptedException interruptedException){
                            interruptedException.printStackTrace();
                        }
                        finally {
                            //enable play sound button
                            playSoundButton.setEnabled(true);
                        }
                    }
                });
                playMorseCodeThread.start();
            }
        });



        add(titleLabel);
        add(textImputLabel);
        add(textImputScroll);
        add(morseCodeInputLabel);
        add(morseCodeScroll);
        add(playSoundButton);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() != KeyEvent.VK_SHIFT){
            String inputText = textImputArea.getText();

            morseCodeArea.setText(morseCodeController.translateToMorse(inputText));
        }
    }
}
