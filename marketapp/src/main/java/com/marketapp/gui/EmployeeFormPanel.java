package com.marketapp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.marketapp.util.DatabaseConnection;

public class EmployeeFormPanel extends JPanel {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField positionField;
    private JTextField salaryField;
    private JButton addButton;

    public EmployeeFormPanel() {
        setLayout(new GridBagLayout()); // GridBagLayout kullanarak esnek düzenleme
        setBackground(Color.decode("#404040")); // Arka plan rengini ayarla

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Hücreler arası boşlukları azalt
        gbc.fill = GridBagConstraints.HORIZONTAL; // Genişletme
        gbc.weightx = 1.0; // Alanın genişlemesine izin ver
        gbc.weighty = 0; // Dikeyde genişleme ayarla

        // Etiketleri ve giriş alanlarını oluştur
        JLabel firstNameLabel = createLabel("First Name:"); // Ad etiketini oluştur
        firstNameField = createTextField(); // Ad giriş alanını oluştur

        JLabel lastNameLabel = createLabel("Last Name:"); // Soyad etiketini oluştur
        lastNameField = createTextField(); // Soyad giriş alanını oluştur

        JLabel positionLabel = createLabel("Position:"); // Pozisyon etiketini oluştur
        positionField = createTextField(); // Pozisyon giriş alanını oluştur

        JLabel salaryLabel = createLabel("Salary:"); // Maaş etiketini oluştur
        salaryField = createTextField(); // Maaş giriş alanını oluştur

        // Butonu oluştur
        addButton = new JButton("Add Employee"); // Çalışan ekleme butonunu oluştur
        addButton.setBackground(Color.decode("#3c077d")); // Buton arka plan rengini ayarla
        addButton.setForeground(Color.BLACK); // Buton metin rengini siyah yap
        addButton.setPreferredSize(new Dimension(150, 30)); // Buton boyutunu ayarla

        // Alanları ve butonu panele ekle
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST; // Etiketleri sola hizala
        add(firstNameLabel, gbc); // Ad etiketini ekle

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(firstNameField, gbc); // Ad giriş alanını ekle

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastNameLabel, gbc); // Soyad etiketini ekle

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastNameField, gbc); // Soyad giriş alanını ekle

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(positionLabel, gbc); // Pozisyon etiketini ekle

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(positionField, gbc); // Pozisyon giriş alanını ekle

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(salaryLabel, gbc); // Maaş etiketini ekle

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(salaryField, gbc); // Maaş giriş alanını ekle

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1.0; // Butonu panelin altına itmek için weighty'yi artır
        add(addButton, gbc); // Çalışan ekleme butonunu ekle

        // İmleci beyaz yapmak için ekle
        setCaretColor(Color.WHITE); // Metin alanı imlecinin rengini beyaz yap

        // Butonun eylemini tanımla
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee(); // Butona tıklandığında addEmployee() metodunu çağır
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text); // Yeni bir etiket oluştur
        label.setForeground(Color.WHITE); // Etiket metninin rengini beyaz yap
        return label; // Etiketi döndür
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(); // Yeni bir metin alanı oluştur
        textField.setBackground(Color.decode("#333333")); // Metin alanının arka plan rengini ayarla
        textField.setForeground(Color.WHITE); // Metin rengini beyaz yap
        textField.setPreferredSize(new Dimension(150, 25)); // Metin alanı boyutunu ayarla
        return textField; // Metin alanını döndür
    }

    private void setCaretColor(Color color) {
        firstNameField.setCaretColor(color); // Ad alanı imlecinin rengini ayarla
        lastNameField.setCaretColor(color); // Soyad alanı imlecinin rengini ayarla
        positionField.setCaretColor(color); // Pozisyon alanı imlecinin rengini ayarla
        salaryField.setCaretColor(color); // Maaş alanı imlecinin rengini ayarla
    }

    private void addEmployee() {
        String firstName = firstNameField.getText(); // Adı al
        String lastName = lastNameField.getText(); // Soyadı al
        String position = positionField.getText(); // Pozisyonu al
        String salaryText = salaryField.getText(); // Maaşı al

        if (firstName.isEmpty() || lastName.isEmpty() || position.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required"); // Eksik alanlar için uyarı göster
            return; // Metoddan çık
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryText); // Maaşı double türüne dönüştür
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid salary amount"); // Geçersiz maaş için uyarı göster
            return; // Metoddan çık
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (first_name, last_name, position, salary) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, firstName); // Adı ayarla
            stmt.setString(2, lastName); // Soyadı ayarla
            stmt.setString(3, position); // Pozisyonu ayarla
            stmt.setDouble(4, salary); // Maaşı ayarla
            stmt.executeUpdate(); // Veriyi veritabanına ekle

            JOptionPane.showMessageDialog(this, "Employee added successfully"); // Başarı mesajı göster
        } catch (SQLException ex) {
            ex.printStackTrace(); // Hata durumunda yığını yazdır
        }
    }
}