package com.marketapp.gui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout; // CardLayout nesnesi
    private EmployeeDetailsPanel detailsPanel; // EmployeeDetailsPanel nesnesi
    private EmployeeFormPanel formPanel; // EmployeeFormPanel nesnesi
    private JPanel rightPanel; // Sağ panel nesnesi

    public MainPanel() {
        setLayout(new BorderLayout()); // Ana panel düzenini BorderLayout olarak ayarla

        JPanel leftPanel = new JPanel(); // Sol paneli oluştur
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Sol panel düzenini dikey olarak ayarla
        leftPanel.setBackground(Color.decode("#545454")); // Sol panelin arka plan rengini ayarla

        JButton homeButton = new JButton("Ana Ekran"); // Ana ekran butonunu oluştur
        JButton detailsButton = new JButton("Employee Details"); // Çalışan detayları butonunu oluştur
        JButton addButton = new JButton("Add Employee"); // Çalışan ekleme butonunu oluştur

        leftPanel.add(homeButton); // Ana ekran butonunu sol panele ekle
        leftPanel.add(detailsButton); // Çalışan detayları butonunu sol panele ekle
        leftPanel.add(addButton); // Çalışan ekleme butonunu sol panele ekle

        rightPanel = new JPanel(); // Sağ paneli oluştur
        cardLayout = new CardLayout(); // CardLayout nesnesini oluştur
        rightPanel.setLayout(cardLayout); // Sağ panel düzenini CardLayout olarak ayarla

        // Sağ panel arka plan rengini ayarla
        rightPanel.setBackground(Color.decode("#FFFFFF")); // Sağ panelin arka plan rengini beyaz yap

        JPanel homePanel = new JPanel(); // Ana ekran panelini oluştur
        homePanel.setBackground(Color.decode("#8181e3")); // Ana ekran panelinin arka plan rengini ayarla
        JLabel homeLabel = new JLabel("Ana Ekran"); // Ana ekran etiketi oluştur
        homeLabel.setForeground(Color.WHITE); // Etiket metnini beyaz yap
        homePanel.add(homeLabel); // Ana ekran etiketini ana ekran paneline ekle

        detailsPanel = new EmployeeDetailsPanel(); // Çalışan detayları panelini oluştur
        formPanel = new EmployeeFormPanel(); // Çalışan ekleme panelini oluştur

        // Sağ panele ana ekran, detaylar ve form panellerini ekle
        rightPanel.add(homePanel, "Home"); // Ana ekran panelini sağ panele ekle
        rightPanel.add(detailsPanel, "Details"); // Çalışan detayları panelini sağ panele ekle
        rightPanel.add(formPanel, "Form"); // Çalışan ekleme panelini sağ panele ekle

        // Butonlara eylem dinleyicileri ekle
        homeButton.addActionListener(e -> cardLayout.show(rightPanel, "Home")); // Ana ekran butonuna tıklanırsa ana ekran panelini göster
        detailsButton.addActionListener(e -> {
            detailsPanel.refresh(); // Detaylar panelinin verilerini yenile
            cardLayout.show(rightPanel, "Details"); // Detaylar butonuna tıklanırsa detaylar panelini göster
        });
        addButton.addActionListener(e -> cardLayout.show(rightPanel, "Form")); // Ekleme butonuna tıklanırsa form panelini göster

        add(leftPanel, BorderLayout.WEST); // Sol paneli ana panelin sol tarafına ekle
        add(rightPanel, BorderLayout.CENTER); // Sağ paneli ana panelin ortasına ekle
    }
}
