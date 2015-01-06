
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by xiny on 2015/1/5.
 */
public class Main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Window aWindow = new Window();
            }
        });
    }

}
class  Window{
    int boxSize = 50;
    int level = 0;
    int max_level = 2;
    //input the puzzle below
    int[][][] inputBorders = {{{0,0},{0,1},{0,2},{0,3},{0,4},{1,0},{1,4},{2,0},{2,2},{2,4},{3,0},{3,4},{4,0},{4,1},{4,2},{4,4},{4,5},{4,6},{5,2},{5,6},{6,2},{6,4},{6,6},{7,2},{7,6},{8,2},{8,3},{8,4},{8,5},{8,6}},
            {{0,2},{0,3},{0,4},{1,2},{1,4},{2,0},{2,1},{2,2},{2,4},{3,0},{3,4},{4,0},{4,2},{4,4},{4,5},{4,6},{5,0},{5,6},{6,0},{6,1},{6,2},{6,4},{6,6},{7,2},{7,6},{8,2},{8,3},{8,4},{8,5},{8,6}},
            {{1,0},{2,0},{3,0},{4,0},{5,0},{1,1},{5,1},{6,1},{7,1},{0,2},{1,2},{3,2},{7,2},{0,3},{7,3},{0,4},{6,4},{7,4},{0,5},{1,5},{2,5},{4,5},{6,5},{2,6},{6,6},{2,7},{3,7},{4,7},{5,7},{6,7}}};
    int []inputBorder_Number = {30,30,30};
    int[][][] inputBoxs = {{{3,3,0}},{{5,3,0},{3,2,0}},{{4,2,0},{2,3,0},{3,4,0},{4,4,0}}};
    //The Third number indicates whether the box is in aim area (default=0)
    int []inputBox_Number = {1,2,4};
    int[][][] inputAims = {{{1,3}},{{1,3},{2,3}},{{2,3},{3,3},{5,3},{5,5}}};
    int[][] inputLocation = {{2,1},{2,3},{3,1}};

    int border_Number,box_Number;
    int[][] boxs = new int[20][3];
    int[][] borders = new int[100][2];
    int[][] aims = new int[20][2];
    int[] nowLocation;
    Board mainBoard;
    JFrame frame;
    BufferedImage player;
    BufferedImage border;
    BufferedImage box;
    BufferedImage finishedBox;
    BufferedImage aim;
    public Window() {
        box_Number=inputBox_Number[level];
        border_Number=inputBorder_Number[level];
        for(int i=0;i<box_Number;i++) {
            System.arraycopy(inputBoxs[level][i], 0, boxs[i], 0, 3);
        }
        for(int i=0;i<box_Number;i++) {
            System.arraycopy(inputAims[level][i], 0, aims[i], 0, 2);
        }
        for(int i=0;i<border_Number;i++) {
            System.arraycopy(inputBorders[level][i], 0, borders[i], 0, 2);
        }
        nowLocation = inputLocation[level].clone();
        for(int i=0;i<box_Number;i++){
            boxs[i][2]=0;
            for(int j=0;j<box_Number;j++){
                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                    boxs[i][2]=1;
                    break;
                }
            }
        }
        try {
            player = ImageIO.read(new File("player.png"));
            border = ImageIO.read(new File("border.png"));
            box = ImageIO.read(new File("box.png"));
            finishedBox = ImageIO.read(new File("finishedBox.png"));
            aim = ImageIO.read(new File("aim.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Hello Swing");
        mainBoard = new Board();
        frame.add(mainBoard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        frame.setBounds(env.getCenterPoint().x - 300, env.getCenterPoint().y - 250, 600, 500);
        Image img = new ImageIcon("icon.gif").getImage();
        frame.setIconImage(img);
        frame.setTitle("PushBox");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        KeyHandler key = new KeyHandler();
        frame.addKeyListener(key);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    class Board extends JComponent {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            JButton restart = new JButton("Restart");
            restart.setBounds(450,20,100,50);
            SetRestart aRestart = new SetRestart();
            restart.addActionListener(aRestart);
            this.add(restart);
            for(int i=0;i<border_Number;i++){
                g2.drawImage(border,borders[i][0]*boxSize,borders[i][1]*boxSize,boxSize,boxSize,null);
            }
            for(int i=0;i<box_Number;i++){
                g2.drawImage(aim,aims[i][0]*boxSize,aims[i][1]*boxSize,boxSize,boxSize,null);
            }
            for(int i=0;i<box_Number;i++){
                if(boxs[i][2]==0)
                    g2.drawImage(box, boxs[i][0] * boxSize, boxs[i][1] * boxSize, boxSize, boxSize, null);
                else
                    g2.drawImage(finishedBox, boxs[i][0] * boxSize,boxs[i][1]*boxSize,boxSize,boxSize,null);
            }
            g2.drawImage(player,nowLocation[0]*boxSize,nowLocation[1]*boxSize,boxSize,boxSize,null);
        }
    }
    class KeyHandler extends KeyAdapter {
        public void keyPressed(KeyEvent event){
out:        switch(event.getKeyCode()){
                case 37:
                    int[] movingLeftTo = {nowLocation[0]-1,nowLocation[1]};
                    for(int i=0;i<border_Number;i++){
                        if(borders[i][0]==movingLeftTo[0]&&borders[i][1]==movingLeftTo[1])
                            break out;
                    }
                    for(int i=0;i<box_Number;i++){
                        if(boxs[i][0]==movingLeftTo[0]&&boxs[i][1]==movingLeftTo[1]){
                            int[] boxMovingLeftTo = {movingLeftTo[0]-1,movingLeftTo[1]};
                            for(int j=0;j<border_Number;j++){
                                if(borders[j][0]==boxMovingLeftTo[0]&&borders[j][1]==boxMovingLeftTo[1])
                                    break out;
                            }
                            for(int j=0;j<box_Number;j++){
                                if(boxs[j][0]==boxMovingLeftTo[0]&&boxs[j][1]==boxMovingLeftTo[1])
                                    break out;
                            }
                            boxs[i][0]--;
                            nowLocation[0]--;
                            boxs[i][2]=0;
                            for(int j=0;j<box_Number;j++){
                                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                                    boxs[i][2]=1;
                                    break;
                                }
                            }
                            mainBoard.repaint();
                            judge();
                            break out;
                        }
                    }
                    nowLocation[0]--;
                    mainBoard.repaint();
                    break;
                case 38:
                    int[] movingUpTo = {nowLocation[0],nowLocation[1]-1};
                    for(int i=0;i<border_Number;i++){
                        if(borders[i][0]==movingUpTo[0]&&borders[i][1]==movingUpTo[1])
                            break out;
                    }
                    for(int i=0;i<box_Number;i++){
                        if(boxs[i][0]==movingUpTo[0]&&boxs[i][1]==movingUpTo[1]){
                            int[] boxMovingUpTo = {movingUpTo[0],movingUpTo[1]-1};
                            for(int j=0;j<border_Number;j++){
                                if(borders[j][0]==boxMovingUpTo[0]&&borders[j][1]==boxMovingUpTo[1])
                                    break out;
                            }
                            for(int j=0;j<box_Number;j++){
                                if(boxs[j][0]==boxMovingUpTo[0]&&boxs[j][1]==boxMovingUpTo[1])
                                    break out;
                            }
                            boxs[i][1]--;
                            nowLocation[1]--;
                            boxs[i][2]=0;
                            for(int j=0;j<box_Number;j++){
                                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                                    boxs[i][2]=1;
                                    break;
                                }
                            }
                            mainBoard.repaint();
                            judge();
                            break out;
                        }
                    }
                    nowLocation[1]--;
                    mainBoard.repaint();
                    break;
                case 39:
                    int[] movingRightTo = {nowLocation[0]+1,nowLocation[1]};
                    for(int i=0;i<border_Number;i++){
                        if(borders[i][0]==movingRightTo[0]&&borders[i][1]==movingRightTo[1])
                            break out;
                    }
                    for(int i=0;i<box_Number;i++){
                        if(boxs[i][0]==movingRightTo[0]&&boxs[i][1]==movingRightTo[1]){
                            int[] boxMovingRightTo = {movingRightTo[0]+1,movingRightTo[1]};
                            for(int j=0;j<border_Number;j++){
                                if(borders[j][0]==boxMovingRightTo[0]&&borders[j][1]==boxMovingRightTo[1])
                                    break out;
                            }
                            for(int j=0;j<box_Number;j++){
                                if(boxs[j][0]==boxMovingRightTo[0]&&boxs[j][1]==boxMovingRightTo[1])
                                    break out;
                            }
                            boxs[i][0]++;
                            nowLocation[0]++;
                            boxs[i][2]=0;
                            for(int j=0;j<box_Number;j++){
                                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                                    boxs[i][2]=1;
                                    break;
                                }
                            }
                            mainBoard.repaint();
                            judge();
                            break out;
                        }
                    }
                    nowLocation[0]++;
                    mainBoard.repaint();
                    break;
                case 40:
                    int[] movingDownTo = {nowLocation[0],nowLocation[1]+1};
                    for(int i=0;i<border_Number;i++){
                        if(borders[i][0]==movingDownTo[0]&&borders[i][1]==movingDownTo[1])
                            break out;
                    }
                    for(int i=0;i<box_Number;i++){
                        if(boxs[i][0]==movingDownTo[0]&&boxs[i][1]==movingDownTo[1]){
                            int[] boxMovingDownTo = {movingDownTo[0],movingDownTo[1]+1};
                            for(int j=0;j<border_Number;j++){
                                if(borders[j][0]==boxMovingDownTo[0]&&borders[j][1]==boxMovingDownTo[1])
                                    break out;
                            }
                            for(int j=0;j<box_Number;j++){
                                if(boxs[j][0]==boxMovingDownTo[0]&&boxs[j][1]==boxMovingDownTo[1])
                                    break out;
                            }
                            boxs[i][1]++;
                            nowLocation[1]++;
                            boxs[i][2]=0;
                            for(int j=0;j<box_Number;j++){
                                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                                    boxs[i][2]=1;
                                    break;
                                }
                            }
                            mainBoard.repaint();
                            judge();
                            break out;
                        }
                    }
                    nowLocation[1]++;
                    mainBoard.repaint();
                    break;
                case 82:
                    for(int i=0;i<box_Number;i++) {
                        System.arraycopy(inputBoxs[level][i], 0, boxs[i], 0, 3);
                    }
                    for(int i=0;i<box_Number;i++){
                        boxs[i][2]=0;
                        for(int j=0;j<box_Number;j++){
                            if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                                boxs[i][2]=1;
                                break;
                            }
                        }
                    }
                    nowLocation = inputLocation[level].clone();
                    mainBoard.repaint();
                    break;

                default:
                    break;

            }
        }
    }
    class SetRestart implements ActionListener{
        public void actionPerformed(ActionEvent event){
            for(int i=0;i<box_Number;i++) {
                System.arraycopy(inputBoxs[level][i], 0, boxs[i], 0, 3);
            }
            for(int i=0;i<box_Number;i++){
                boxs[i][2]=0;
                for(int j=0;j<box_Number;j++){
                    if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                        boxs[i][2]=1;
                        break;
                    }
                }
            }
            nowLocation = inputLocation[level].clone();
            mainBoard.repaint();
            frame.requestFocus();
        }
    }
    public void judge(){
        for(int i=0;i<box_Number;i++)
            if(boxs[i][2]==0)
                return;
        Icon img = new ImageIcon("win.png");
        int chosen = JOptionPane.showConfirmDialog(mainBoard, "You Win!  Go to next level?", "You Win", 2, 3, img);
        if(chosen==0)
            nextLevel();
    }
    public void nextLevel(){
        if(level<max_level)
            level++;
        box_Number=inputBox_Number[level];
        border_Number=inputBorder_Number[level];
        for(int i=0;i<box_Number;i++) {
            System.arraycopy(inputBoxs[level][i], 0, boxs[i], 0, 3);
        }
        for(int i=0;i<box_Number;i++) {
            System.arraycopy(inputAims[level][i], 0, aims[i], 0, 2);
        }
        for(int i=0;i<border_Number;i++) {
            System.arraycopy(inputBorders[level][i], 0, borders[i], 0, 2);
        }
        nowLocation = inputLocation[level].clone();
        for(int i=0;i<box_Number;i++){
            boxs[i][2]=0;
            for(int j=0;j<box_Number;j++){
                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                    boxs[i][2]=1;
                    break;
                }
            }
        }
        mainBoard.repaint();
    }

}

