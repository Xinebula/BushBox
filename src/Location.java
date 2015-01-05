/**
 * Created by xiny on 2015/1/5.
 */
public class Location{
    private int x;
    private int y;
    public Location(int x1,int y1){
        this.x = x1;
        this.y = y1;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void moveUp(){
        y--;
    }
    public void moveDown(){
        y++;
    }
    public void moveLeft(){
        x--;
    }
    public void moveRight(){
        x++;
    }
}