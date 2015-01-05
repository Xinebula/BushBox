
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

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
    int boxSize = 30;
    //input the puzzle below
    int[][] borders = {{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{0,1},{0,2},{0,3},{0,4},{0,5},{1,5},{2,5},{3,5},{4,5},{5,5},{5,1},{5,2},{5,3},{5,4}};
    int border_Number = 20;
    int[][] boxs = {{2,3,0},{2,2,0}};
    //The Third number indicates whether the box is in aim area (default=0)
    int box_Number = 2;
    int[][] aims = {{3,3},{3,2}};
    int[] nowLocation = {1,2};
    Board mainBoard;
    public Window() {
        for(int i=0;i<box_Number;i++){
            boxs[i][2]=0;
            for(int j=0;j<box_Number;j++){
                if(boxs[i][0]==aims[j][0]&&boxs[i][1]==aims[j][1]){
                    boxs[i][2]=1;
                    break;
                }
            }
        }
        JFrame frame = new JFrame("Hello Swing");
        mainBoard = new Board();
        frame.add(mainBoard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        frame.setBounds(env.getCenterPoint().x - 300, env.getCenterPoint().y - 200, 600, 400);
        Image img = new ImageIcon("icon.gif").getImage();
        frame.setIconImage(img);
        frame.setTitle("PushBox");
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        KeyHandler key = new KeyHandler();
        frame.addKeyListener(key);
        frame.setVisible(true);
    }

    class Board extends JComponent {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(Color.RED);
            for(int i=0;i<border_Number;i++){
                Rectangle2D toDraw = new Rectangle2D.Double(borders[i][0]*boxSize,borders[i][1]*boxSize,boxSize,boxSize);
                g2.fill(toDraw);
            }
            g2.setPaint(Color.PINK);
            for(int i=0;i<box_Number;i++){
                Rectangle2D toDraw = new Rectangle2D.Double(aims[i][0]*boxSize,aims[i][1]*boxSize,boxSize,boxSize);
                g2.fill(toDraw);
            }
            for(int i=0;i<box_Number;i++){
                if(boxs[i][2]==0)
                    g2.setPaint(Color.YELLOW);
                else
                    g2.setPaint(Color.CYAN);
                Rectangle2D toDraw = new Rectangle2D.Double(boxs[i][0]*boxSize,boxs[i][1]*boxSize,boxSize,boxSize);
                g2.fill(toDraw);
            }
            g2.setPaint(Color.GREEN);
            Rectangle2D toDraw = new Rectangle2D.Double(nowLocation[0]*boxSize,nowLocation[1]*boxSize,boxSize,boxSize);
            g2.fill(toDraw);
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
                default:
                    break;

            }
        }
    }
    public void judge(){
        for(int i=0;i<box_Number;i++)
            if(boxs[i][2]==0)
                return;
        Icon img = new ImageIcon("win.png");
        JOptionPane option = new JOptionPane();
        option.showConfirmDialog(mainBoard,"You Win!  Go to next level?","You Win",2,3,img);
//        System.exit(0);
    }
}

