package com.marketapp;

import javax.swing.*;
import com.marketapp.gui.MainPanel;

public class App {
    public static void main(String[] args) {
        // Yeni bir JFrame oluşturuluyor
        JFrame frame = new JFrame("Market Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // MainPanel sınıfı kullanılarak ana panel oluşturuluyor
        MainPanel mainPanel = new MainPanel();

        // Ana paneli frame'e ekleyin
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}