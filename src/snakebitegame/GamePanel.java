package snakebitegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    //Snake Variables
    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private int lengthOfsnake = 1;
    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private int moves = 0;

    //Frog Variables
    private int[] xPos = new int[33];
    private int[] yPos =new int[22];
    private Random random = new Random();
    private int frogX,frogY;

    //Game Progress variables
    private int score = 0;
    private boolean gameOver=false;
    private boolean gameStart=true;


    private Timer timer;
    private int delay = 110;

    GamePanel(){
        setBackground(Color.DARK_GRAY);
        timer= new Timer(delay, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        newFrog();

    }



    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        //Title drawing
        ImageIcon snaketitle = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\snaketitle.png")));
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);


        //Importing the image files
        ImageIcon leftmouth = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\leftmouth.png")));
        ImageIcon rightmouth = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\rightmouth.png")));
        ImageIcon upmouth = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\upmouth.png")));
        ImageIcon downmouth = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\downmouth.png")));
        ImageIcon snakebody = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\snakebody.gif")));
        ImageIcon GameOver = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\GameOver.jpg")));
        ImageIcon GameStart = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\GameStart.jpg")));
        ImageIcon frog = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("Snake Images\\frog.png")));


        //Initial position of the snake
        if(moves ==0){
            snakexlength[0] = 100;
            snakeylength[0] = 100;
        }
        if(left){
            leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }if(right){
            rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }if(up){
            upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }if(down){
            downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        for(int i =1;i<lengthOfsnake;i++){
            snakebody.paintIcon(this, g, snakexlength[i], snakeylength[i]);
        }


        frog.paintIcon(this, g, frogX, frogY);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Minecraft", Font.BOLD, 14));
        g.drawString("S C O R E  :  " +score, 750,92);

        //Game Over Screen contents
        if(gameOver){
            GameOver.paintIcon(this, g, 26, 75);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Minecraft", Font.BOLD, 25));
            g.drawString("S C O R E  :  " +score, 365,265);
        }

        if(gameStart){
            GameStart.paintIcon(this, g, 25, 11);
        }


        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i =lengthOfsnake-1;i>0;i--){
            snakexlength[i] = snakexlength[i-1];
            snakeylength[i] = snakeylength[i-1];
        }

        //Condition for the animation of the snake
        if(left){
            snakexlength[0]=snakexlength[0] - 25;
        }
        if(right){
            snakexlength[0]=snakexlength[0] + 25;
        }
        if(up){
            snakeylength[0]=snakeylength[0] - 25;
        }
        if(down){
            snakeylength[0]=snakeylength[0] + 25;
        }

        //Condition for the snake to not leave the rectangle boundary
        if(snakexlength[0]>850)snakexlength[0] = 25;
        if(snakexlength[0]<25)snakexlength[0] = 850;
        if(snakeylength[0]>625)snakeylength[0] = 70;
        if(snakeylength[0]<70)snakeylength[0] = 625;

        //Eating
        eatFrog();
        eatItself();
        repaint();
    }




    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A && (!right)){
            left = true;
            right = false;
            up = false;
            down = false;
            moves ++;
        }
        if(e.getKeyCode() == KeyEvent.VK_D && (!left)){
            left = false;
            right = true;
            up = false;
            down = false;
            moves ++;
        }
        if(e.getKeyCode() == KeyEvent.VK_W && (!down)){
            left = false;
            right = false;
            up = true;
            down = false;
            moves ++;
        }
        if(e.getKeyCode() == KeyEvent.VK_S && (!up)){
            left = false;
            right = false;
            up = false;
            down = true;
            moves ++;
        }
        while(gameOver){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                timer.stop();
                restart();
            }
        }
        while(gameStart){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                gameStart=false;
                timer.start();
            }
        }
    }

    //Logic for setting the position of the frog
    private void newFrog() {
        int a = 25;
        for(int i = 0;i<xPos.length;i++){
            xPos[i] = a;
            a+=25;
        }
        a=75;
        for(int i = 0;i<yPos.length;i++){
            yPos[i] = a;
            a+=25;
        }
        frogX = xPos[random.nextInt(33)];
        frogY = yPos[random.nextInt(22)];
        for(int i = lengthOfsnake; i>=0; i--){
            if(snakexlength[i] == frogX && snakeylength[i] == frogY){
                newFrog();
            }
        }
    }

    //Logic after eating the frog
    private void eatFrog() {
        if(snakexlength[0] == frogX && snakeylength[0] == frogY){
            newFrog();
            lengthOfsnake ++;
            score ++;
            if(delay>10){
                delay-= 1;
            }
            timer.setDelay(delay);
        }
    }

    //Logic for GameOver
    private void eatItself(){
        for(int i = lengthOfsnake-1;i>0;i--){
            if(snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]){
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void restart(){
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfsnake = 1;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        delay=110;
        timer.setDelay(delay);
        repaint();
        newFrog();
    }


    //Unused methods//
    public void keyReleased(KeyEvent e){}public void keyTyped(KeyEvent e){}
    //Unused methods//
}


