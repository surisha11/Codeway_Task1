import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import pack.MyPanel;
import org.jdesktop.swingx.prompt.PromptSupport;

class TakeAGuess implements ActionListener {

    JFrame frame;
    JPanel cardPane, home, gamePane, resultPane;
    CardLayout card;
    JButton start, check, homeButton;
    JTextField enterGuess, resultField;
    Image homeBackgroundImage, gameBackgroundImage, homeImage, scaledHomeImage, resultBackgroundImage;
    Icon homeIcon;
    Font enterGuessStyle, resultStyle;
    int randomNumber, userGuess, attempt = 1, roundOption, roundNumber = 1, result = 0;
    String instruction;
    String[] option = {"Ok"}, nextRoundOption = {"Continue", "Cancel"}, tryAgainOption = {"Try again"};

    public TakeAGuess(){

        super();
        frame = new JFrame("Take A Guess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Take A Guess");

        cardPane = new JPanel();
        card = new CardLayout();
        cardPane.setLayout(card);

        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        try {
            homeBackgroundImage = ImageIO.read(new File("media/are you ready!!!.png"));
            gameBackgroundImage = ImageIO.read(new File("media/number game.png"));
            homeImage = ImageIO.read(new File("media/home.jpg"));
            scaledHomeImage = homeImage.getScaledInstance(50, 40, Image.SCALE_DEFAULT);
            resultBackgroundImage = ImageIO.read(new File("media/ResultPage.png"));
        }
        catch(IOException ioe) { 
            ioe.printStackTrace();
        }

        home = new MyPanel(homeBackgroundImage);
        home.setLayout(null);
        start = new JButton("Start");
        start.setBounds(1000, 450, 200, 50);
        start.addActionListener(this);
        home.add(start);

        gamePane = new MyPanel(gameBackgroundImage);
        gamePane.setLayout(null);
        homeIcon = new ImageIcon(scaledHomeImage);
        homeButton = new JButton("Home", homeIcon);
        homeButton.setBounds(0, 0, 50, 40);
        homeButton.addActionListener(this);
        gamePane.add(homeButton);
        enterGuess = new JTextField(3);
        enterGuess.setBounds(500, 350, 220, 100);
        enterGuessStyle = new Font("Serif", Font.BOLD, 80);
        enterGuess.setFont(enterGuessStyle);
        PromptSupport.setPrompt("Guess", enterGuess);
        PromptSupport.setForeground(Color.LIGHT_GRAY, enterGuess);
        gamePane.add(enterGuess);
        check = new JButton("Check");
        check.setBounds(800, 375, 150, 50);
        check.addActionListener(this);
        gamePane.add(check);

        resultPane = new MyPanel(resultBackgroundImage);
        resultPane.setLayout(null);
        homeButton = new JButton("Home", homeIcon);
        homeButton.setBounds(0, 0, 50, 40);
        homeButton.addActionListener(this);
        resultPane.add(homeButton);
        resultField = new JTextField();
        resultField.setText(Integer.toString(result));
        resultStyle = new Font("Serif", Font.BOLD, 200);
        resultField.setFont(resultStyle);
        resultField.setBackground(Color.LIGHT_GRAY);
        resultField.setBounds(520, 410, 300, 200);
        resultField.setEditable(false);
        resultPane.add(resultField);
        
        cardPane.add(home, "home");
        cardPane.add(gamePane, "game");
        cardPane.add(resultPane, "result");
        
        frame.add(cardPane);

        frame.setVisible(true);

    }

    

    public void actionPerformed(ActionEvent ae){

        if(ae.getActionCommand() == "Start") {
            card.show(cardPane, "game");
            instruction = new String("The number you have to guess is between 0 to 100 and including 0 and 100.\n Enter your guess in the Guess box, once you entered your guess click the check button.\nYou got 5 attempts in one round.\n\t if your guess is wrong you can use another attempt until 5th attempt.\n\t if you guess wrong in your 5th attempt, you lost.\nYou can play total of 7 rounds.\n\t To move to next round you have to clear previous round.\n If you felt like quitting press the home icon in left corner.");
            JOptionPane.showOptionDialog(cardPane, instruction, "INSTRUCTION", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
            randomNumber = (int)(Math.random() * 100);
            enterGuess.setText("");
            attempt = 1;
            roundNumber = 1;
            result = 0;
        }

        else if(ae.getActionCommand() == "Check") {
            if(roundNumber < 7) {
                try {
                    userGuess = Integer.valueOf(enterGuess.getText());
                    if(userGuess < 100 || userGuess > 0) {
                        if(randomNumber == userGuess) {
                            result = (int)(result + 100/attempt + 10 * roundNumber);
                            JOptionPane.showOptionDialog(cardPane, "Your Guess is Correct", "Congratulations!!!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
                            roundOption = JOptionPane.showOptionDialog(cardPane, "Would you like to continue to next Round.", "Next Round Option!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, nextRoundOption, nextRoundOption[0]);
                            if(roundOption == 1) {
                                resultField.setText(Integer.toString(result));
                                card.show(cardPane, "result");
                            } 
                            else {
                                attempt = 1;
                                randomNumber = (int)(Math.random() * 100);
                                JOptionPane.showOptionDialog(cardPane, "Welcome to Round " + ++roundNumber + ". All the best!!!", "ROUND 2!!!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
                            }
                        }
                        else {
                            if(randomNumber > userGuess) {
                                JOptionPane.showOptionDialog(cardPane, "Your Guess is Less than the Number", "Less than number!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                            }
                            else {
                                if(randomNumber < userGuess) {
                                    JOptionPane.showOptionDialog(cardPane, "Your Guess is Greater than the Number", "Greater than number!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
                                }
                            }
                            if(attempt == 5 ) {
                                JOptionPane.showOptionDialog(cardPane, "You have used all your attempts.\n Best luck next time.", "Lost!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
                                resultField.setText(Integer.toString(result));
                                card.show(cardPane, "result");
                            }
                            else {
                                JOptionPane.showOptionDialog(cardPane, "You got 5 attenmpts.\n Attempt number " + ++attempt + " would you like to give another shot.", "TRY AGAIN!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, tryAgainOption, tryAgainOption[0]);
                            }
                        }
                    }
                    else {
                        validInput();
                    }
                }
                catch(NumberFormatException nfe) {
                    validInput();
                }    
                enterGuess.setText("");
                enterGuess.requestFocus();
            }
            else {
                resultField.setText(Integer.toString(result));
                card.show(cardPane, "result");
            } 
        }

        else if(ae.getActionCommand() == "Home") {
            card.show(cardPane, "home");
        }
    }

    public void validInput() {
        JOptionPane.showOptionDialog(cardPane, "Your Guess is must be between 0 to 100.\n Please enter valid number.", "Enter valid number!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, option, option[0]);
        if(attempt == 5 ) {
            JOptionPane.showOptionDialog(cardPane, "You have used all your attempts.\n Best luck next time.", "Lost!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
            resultField.setText(Integer.toString(result));
            card.show(cardPane, "result");
        }
        else {
            JOptionPane.showOptionDialog(cardPane, "You got 5 attenmpts.\n Attempt number " + ++attempt + " would you like to give another shot.", "TRY AGAIN!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, tryAgainOption, tryAgainOption[0]);
        }
    }

    public static void main(String args[]){
        new TakeAGuess();
    }
}