package be.intecbrussel.guestbook.dao;


import be.intecbrussel.guestbook.model.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private String url;
    private String user;
    private String password;
    private Connection connection;

    public List<Message> getAllMessages() throws SQLException {
        List<Message> messages = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM GuestBook");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setName(rs.getString("Name"));
                message.setDate(rs.getTimestamp("Date").toLocalDateTime());
                message.setMessage(rs.getString("Message"));
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                closeConnection(connection);
            }
        }
        return messages;
    }

    public void createMessage(Message message) throws SQLException {
        System.out.println("In create message");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO GuestBook  " +
                    "VALUES (default ,?,?,?)");
            preparedStatement.setString(3, message.getMessage());
            preparedStatement.setTimestamp(1, Timestamp.valueOf(message.getDate()));
            preparedStatement.setString(2, message.getName());

            preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                closeConnection(connection);
            }
        }
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://noelvaes.eu/StudentDB", "student",
                    "student123");
        } catch (Exception ex) {
            System.out.println("Oops, something went wrong!");
            ex.printStackTrace(System.err);
        }

        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {

        connection.close();

    }

}
