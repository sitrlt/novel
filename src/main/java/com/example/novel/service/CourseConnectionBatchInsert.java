/*
package com.example.novel.service;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseConnectionBatchInsert {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public void addSelectStudent(List<course> list, String id, String time)
    {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

            String sql = "INSERT INTO select_stu (sid, cid,time) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println("存到数据库里"+id);
            for (course c : list) {
                statement.setString(1, id);
                statement.setString(2, c.getId());
                statement.setString(3, time);
                System.out.println(id+c.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void batchInsertCourseConnections(List<course> connections,String id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO course_connection (cid, csid) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (course c : connections) {
                statement.setString(1, c.getId());
                statement.setString(2, id);
                System.out.println(id+c.getId());
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getConnectionCourseId(String id)
    {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM course_connection WHERE csid = ?";


            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            List<String> dataList = new ArrayList<>();

            while (resultSet.next()) {
                String data = resultSet.getString("cid");  // 为实际的列名
                dataList.add(data);
            }

            return dataList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
*/
