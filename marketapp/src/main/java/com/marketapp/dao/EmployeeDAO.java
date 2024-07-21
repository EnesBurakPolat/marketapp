package com.marketapp.dao;

// DatabaseConnection sınıfını içe aktar
import com.marketapp.util.DatabaseConnection;
// SQL sınıflarını içe aktar
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class EmployeeDAO {

    // Veritabanı bağlantısını oluştur
    public Connection getConnection() {
        System.out.println("Trying to connect to the database...");
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection(); // DatabaseConnection sınıfını kullan
            System.out.println("Successfully connected to the database.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Error: " + e.getMessage());
        }
        return connection;
    }

    // Veritabanından tüm çalışanları getir
    public void getAllEmployees() {
        System.out.println("Fetching all employees from the database...");
        Connection connection = getConnection();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String sql = "SELECT * FROM employees"; // Çalışanları seçen SQL sorgusu
                ResultSet rs = stmt.executeQuery(sql);
                
                // İşlem sonucunu yazdır
                while (rs.next()) {
                    System.out.println("Employee: " + rs.getString("first_name") + " " + rs.getString("last_name") + ", " + rs.getString("position"));
                }
                rs.close();
                stmt.close();
                
                System.out.println("Employees fetched successfully.");
            } catch (SQLException e) {
                System.err.println("Error fetching employees: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Bağlantıyı kapat
                } catch (SQLException e) {
                    System.err.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }

    // Yeni bir çalışan ekle
    public void addEmployee(String firstName, String lastName, String position, double salary) {
        System.out.println("Adding employee to the database...");
        Connection connection = getConnection();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String sql = "INSERT INTO employees (first_name, last_name, position, salary) VALUES ('" 
                            + firstName + "', '" + lastName + "', '" + position + "', " + salary + ")"; // Çalışan ekleme SQL sorgusu
                stmt.executeUpdate(sql);
                stmt.close();
                
                System.out.println("Employee added: " + firstName + " " + lastName + ", " + position);
            } catch (SQLException e) {
                System.err.println("Error adding employee: " + e.getMessage());
            } finally {
                try {
                    connection.close(); // Bağlantıyı kapat
                } catch (SQLException e) {
                    System.err.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
    }
}