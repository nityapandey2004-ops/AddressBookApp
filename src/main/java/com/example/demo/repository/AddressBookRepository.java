package com.example.demo.repository;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import com.example.demo.repository.DBConnection;


public class AddressBookRepository {
    Scanner sc = new Scanner(System.in);
    public void addContact() {
        try {
            Connection connection = DBConnection.getConnection();

            System.out.println("Enter First Name:");
            String firstName = sc.nextLine();

            System.out.println("Enter Last Name:");
            String lastName = sc.nextLine();

            System.out.println("Enter Address:");
            String address = sc.nextLine();

            System.out.println("Enter City:");
            String city = sc.nextLine();

            System.out.println("Enter State:");
            String state = sc.nextLine();

            System.out.println("Enter Zip:");
            String zip = sc.nextLine();

            System.out.println("Enter Phone Number:");
            String phone = sc.nextLine();

            System.out.println("Enter Email:");
            String email = sc.nextLine();

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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int retrieveContacts() {
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM contacts";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int totalContacts = rs.getFetchSize();
            
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
            }
            connection.close();
            return totalContacts;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}