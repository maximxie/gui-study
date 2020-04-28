package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //定义蛇的数据结构
    int length;//蛇的长度
    int[] snakeX=new int[600];//蛇的x坐标
    int[] snakeY=new int[600];//蛇的y坐标
    String fx;
    //食物坐标
    int foodx;
    int foody;
    Random random=new Random();

    int score;//积分

    boolean isStart=false;
    boolean isFail=false;
    //定时器
    Timer timer = new Timer(100,this);//100ms刷新一次

    public GamePanel() {
        init();
        //获得焦点和键盘事件
        this.setFocusable(true);//获得焦点事件
        this.addKeyListener(this);//获得键盘监听事件
        timer.start();//游戏开始，定时器启动
    }
    //初始化方法
    public void init(){
        length=3;
        snakeX[0]=100;snakeY[0]=100;//脑袋
        snakeX[1]=75;snakeY[1]=100;//第一个身体
        snakeX[2]=50;snakeY[2]=100;//第二个身体
        fx="R";//初始方向右
        isStart=false;
        //把食物随机分布在界面上
        foodx=25+25*random.nextInt(34);
        foody=75+25*random.nextInt(24);
        score=0;
    }
    //绘制面板,游戏的所有东西都是要这个画笔来画
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        //绘制静态面板
        this.setBackground(Color.WHITE);
        Data.header.paintIcon(this,g,25,11);//头部广告栏画上去
        g.fillRect(25,75,850,600);//默认的游戏界面
        //画积分
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度"+length,750,35);
        g.drawString("分数"+score,750,55);

        Data.food.paintIcon(this,g,foodx,foody);//画食物
        //把小蛇画上去
        if(fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);//蛇头初始化向右
        }else if(fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //游戏状态
        if(isStart==false){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        if(isFail==true){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("失败，按下空格重新开始游戏",300,300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    //键盘监听事件
    @Override
    public void keyPressed(KeyEvent e) {
        int Keycode=e.getKeyCode();
        if(Keycode==KeyEvent.VK_SPACE){
            if(isFail){
                isFail=false;
                init();
            }else{
                isStart=!isStart;
            }
            repaint();
        }
        //小蛇移动
        if(Keycode==KeyEvent.VK_UP){
            fx="U";
        }else if(Keycode==KeyEvent.VK_DOWN){
            fx="D";
        }else if(Keycode==KeyEvent.VK_LEFT){
            fx="L";
        }else if(Keycode==KeyEvent.VK_RIGHT){
            fx="R";
        }
    }

    //事件监听
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && isFail==false){//游戏开始状态

            //吃食物
            if(snakeX[0]==foodx&&snakeY[0]==foody){
                length++;
                score+=10;
                foodx=25+25*random.nextInt(34);
                foody=75+25*random.nextInt(24);
            }
            //右移
            for (int i=length-1;i>0;i--){
                snakeX[i]=snakeX[i-1];
                snakeY[i]=snakeY[i-1];
            }

            //走向
            if(fx.equals("R")){
                snakeX[0]+=25;
                if(snakeX[0]>850){ snakeX[0]=25;}
            }else if(fx.equals("L")){
                snakeX[0]-=25;
                if(snakeX[0]<25){snakeX[0]=850;}
            }else if(fx.equals("U")){
                snakeY[0]-=25;
                if(snakeY[0]<75){snakeY[0]=650;}
            }else if(fx.equals("D")){
                snakeY[0]+=25;
                if(snakeY[0]>650){snakeY[0]=75;}
            }
            //失败判定
            for(int i=1;i<length;i++){
                if(snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail=true;
                }
            }

            repaint();//重画页面
        }
        timer.start();//定时器开启
    }
}

