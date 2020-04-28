package snake;

import javax.swing.*;
import java.awt.*;

public class StartGame {
    public static void main(String[] args) {
        JFrame frame=new JFrame();

        frame.setBounds(10,10,900,720);
        frame.setResizable(false);//窗口大小不可变
        frame.setVisible(true);//可见
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//可关闭

        frame.add(new GamePanel());
    }
}
