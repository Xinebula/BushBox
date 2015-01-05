
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

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
    int[][] borders = {{0,0},{1,0},{2,0},{3,0},{4,0},{0,1},{0,2},{0,3},{0,4},{1,4},{2,4},{3,4},{4,4},{4,1},{4,2},{4,3}};
    int border_Number = 16;
    int[][] boxs = {{1,1},{2,2}};
    int box_Number = 2;
    int[] nowLocation = {1,2};
    Board mainBoard;
    public Window()
    {
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
            g2.setPaint(Color.YELLOW);
            for(int i=0;i<box_Number;i++){
                Rectangle2D toDraw = new Rectangle2D.Double(a.getX()*boxSize,a.getY()*boxSize,boxSize,boxSize);
                g2.fill(toDraw);
            }
            g2.setPaint(Color.GREEN);
            Rectangle2D toDraw = new Rectangle2D.Double(nowLocation.getX()*boxSize,nowLocation.getY()*boxSize,boxSize,boxSize);
            g2.fill(toDraw);
        }
    }
    class KeyHandler extends KeyAdapter {
        public void keyPressed(KeyEvent event){
            switch(event.getKeyCode()){
                case 37:
                    Location movingLeftTo = new Location(nowLocation.getX()-1,nowLocation.getY());
                    if(borders.contains(movingLeftTo))
                        break;
                    if(boxs.contains(movingLeftTo)){
                        Location boxMovingLeftTo = new Location(movingLeftTo.getX()-1,movingLeftTo.getY());
                        if(borders.contains(boxMovingLeftTo))
                            break;
                        if(boxs.contains(boxMovingLeftTo))
                            break;
                        boxs.remove(movingLeftTo);
                        boxs.add(boxMovingLeftTo);
                        nowLocation.moveLeft();
                        break;
                    }
                    nowLocation.moveLeft();
                    mainBoard.repaint();
                    break;
                case 38:
                    Location movingUpTo = new Location(nowLocation.getX(),nowLocation.getY()-1);
                    if(borders.contains(movingUpTo))
                        break;
                    if(boxs.contains(movingUpTo)){
                        Location boxMovingUpTo = new Location(movingUpTo.getX(),movingUpTo.getY()-1);
                        if(borders.contains(boxMovingUpTo))
                            break;
                        if(boxs.contains(boxMovingUpTo))
                            break;
                        boxs.remove(movingUpTo);
                        boxs.add(boxMovingUpTo);
                        nowLocation.moveUp();
                        break;
                    }
                    nowLocation.moveUp();
                    mainBoard.repaint();
                    break;
                case 39:
                    Location movingRightTo = new Location(nowLocation.getX()+1,nowLocation.getY());
                    if(borders.contains(movingRightTo))
                        break;
                    if(boxs.contains(movingRightTo)){
                        Location boxMovingRightTo = new Location(movingRightTo.getX()+1,movingRightTo.getY());
                        if(borders.contains(boxMovingRightTo))
                            break;
                        if(boxs.contains(boxMovingRightTo))
                            break;
                        boxs.remove(movingRightTo);
                        boxs.add(boxMovingRightTo);
                        nowLocation.moveRight();
                        break;
                    }
                    nowLocation.moveRight();
                    mainBoard.repaint();
                    break;
                case 40:
                    Location movingDownTo = new Location(nowLocation.getX(),nowLocation.getY()+1);
                    if(borders.contains(movingDownTo))
                        break;
                    if(boxs.contains(movingDownTo)){
                        Location boxMovingDownTo = new Location(movingDownTo.getX(),movingDownTo.getY()+1);
                        if(borders.contains(boxMovingDownTo))
                            break;
                        if(boxs.contains(boxMovingDownTo))
                            break;
                        boxs.remove(movingDownTo);
                        boxs.add(boxMovingDownTo);
                        nowLocation.moveDown();
                        break;
                    }
                    nowLocation.moveDown();
                    mainBoard.repaint();
                    break;
                default:
                    break;

            }
        }
    }
}

