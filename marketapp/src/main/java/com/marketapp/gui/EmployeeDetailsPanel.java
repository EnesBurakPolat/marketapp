package com.marketapp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import com.marketapp.util.DatabaseConnection;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;

public class EmployeeDetailsPanel extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    public EmployeeDetailsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#000000")); // Arka plan rengini ayarla

        // Arama alanı oluşturma
        searchField = new JTextField(20);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                if (searchText.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                }
            }
        });

        // Arama alanını panelin üstüne ekle
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Ara:"));
        searchPanel.add(searchField);

        // DefaultTableModel'i geçersiz kılarak hücrelerin düzenlenmesini devre dışı bırakın
        tableModel = new DefaultTableModel(new String[] { "ID", "First Name", "Last Name", "Position", "Salary" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tüm hücrelerin düzenlenmesini devre dışı bırak
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class; // ID sütunu için Integer
                    case 4: return Double.class;  // Salary sütunu için Double
                    default: return String.class; // Diğer sütunlar için String
                }
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tek seçim modunda ayarla
        employeeTable.setDefaultEditor(Object.class, null); // Tüm hücre düzenleyicilerini devre dışı bırak
        employeeTable.setBackground(Color.decode("#FFFFFF")); // Tablo arka plan rengini ayarla
        employeeTable.setForeground(Color.BLACK); // Tablo yazı rengini ayarla

        // TableRowSorter oluştur ve tabloya ata
        sorter = new TableRowSorter<>(tableModel);
        employeeTable.setRowSorter(sorter);

        // Sütun hizalamalarını ayarla
        TableColumnModel columnModel = employeeTable.getColumnModel();

        // Özel bir hücre render'ı oluştur
        TableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.LEFT); // Sol hizalama
                return c;
            }
        };

        // Özel bir para formatı render'ı oluştur
        TableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            private final DecimalFormat formatter = new DecimalFormat("#,###.00"); // Para formatı belirleyin

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Double) {
                    setText(formatter.format((Double) value)); // Formatla ve değeri ayarla
                } else {
                    setText(value.toString()); // Diğer türlerde metni ayarla
                }
                return c;
            }
        };

        // Kolon render'larını ayarla
        columnModel.getColumn(0).setCellRenderer(leftAlignRenderer); // ID sütunu için sola hizalama
        columnModel.getColumn(4).setCellRenderer(currencyRenderer); // Salary sütunu için para formatı

        add(searchPanel, BorderLayout.NORTH); // Arama panelini üst kısma ekle
        add(new JScrollPane(employeeTable), BorderLayout.CENTER); // Tablonun bulunduğu scroll pane ekle
        loadEmployeeDetails(); // İlk yükleme
    }

    public void refresh() {
        // Tablodaki mevcut verileri temizle
        tableModel.setRowCount(0);
        // Verileri yeniden yükle
        loadEmployeeDetails();
    }

    private void loadEmployeeDetails() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            while (rs.next()) {
                tableModel.addRow(new Object[] {
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("position"),
                    rs.getDouble("salary")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}