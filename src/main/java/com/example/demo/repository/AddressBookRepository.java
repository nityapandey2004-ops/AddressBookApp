package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Date;

import com.example.demo.model.Contact;
import com.example.demo.repository.DBConnection;


public class AddressBookRepository {
    Scanner sc = new Scanner(System.in);
    public boolean addContact(String firstName, String lastName, String address, String city, String state, String zip, String phone, String email) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "INSERT INTO contacts(first_name,last_name,address,city,state,zip,phone_number,email) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, address);
            statement.setString(4, city);
            statement.setString(5, state);
            statement.setString(6, zip);
            statement.setString(7, phone);
            statement.setString(8, email);

            statement.executeUpdate();
            System.out.println("Contact added to database.");
            connection.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int retrieveContacts() {
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM contacts";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int totalContacts = 0;
            
            while (rs.next()) {
                System.out.println(
                        rs.getString("first_name") + " " +
                        rs.getString("last_name") + ", " +
                        rs.getString("address") + ", " +
                        rs.getString("city") + ", " +
                        rs.getString("state") + ", " +
                        rs.getString("zip") + ", " +
                        rs.getString("phone_number") + ", " +
                        rs.getString("email")
                );
                totalContacts++;
            }
            connection.close();
            return totalContacts;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Contact retrieveContactByFirstName(String firstName) {
        Contact contact = null;
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM contacts WHERE first_name=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                contact = new Contact(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                );
            }
            connection.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return contact;
    }
    
    public int updateContactByFirstName(String firstName, String lastName, String address, String city, String state, String zip, String phone, String email) {
        int result = 0;
        try {
            Connection connection = DBConnection.getConnection();

            String query = "UPDATE contacts SET last_name=?, address=?, city=?, state=?, zip=?, phone_number=?, email=? WHERE first_name=?";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, lastName);
            ps.setString(2, address);
            ps.setString(3, city);
            ps.setString(4, state);
            ps.setString(5, zip);
            ps.setString(6, phone);
            ps.setString(7, email);
            ps.setString(8, firstName);

            result = ps.executeUpdate();
            connection.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public int retrieveContactsByDateRange(String startDate, String endDate) {
        int count = 0;
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM contacts WHERE date_added BETWEEN ? AND ?";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                System.out.println(
                        rs.getString("first_name") + " " +
                        rs.getString("last_name") + ", " +
                        rs.getString("address") + ", " +
                        rs.getString("city") + ", " +
                        rs.getString("state") + ", " +
                        rs.getString("zip") + ", " +
                        rs.getString("phone_number") + ", " +
                        rs.getString("email") + ", " +
                        rs.getDate("date_added")
                );
                count++;
            }
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countContactsByCity(String city) {
        int count = 0;
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT count_contacts_by_city(?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int countContactsByState(String state) {
        int count = 0;
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT count_contacts_by_state(?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, state);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}