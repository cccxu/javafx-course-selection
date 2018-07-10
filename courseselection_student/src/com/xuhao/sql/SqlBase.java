package com.xuhao.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlBase {
    public Statement create(){
        Connection conn = null;
        Statement stmt = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //打开连接
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/courseselection?useSSL=false&" +
                            "serverTimezone=UTC&allowPublicKeyRetrieval=true&user=root&password=123456789");
            //执行查询
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public void destroy(Statement stmt){
        try{
            Connection conn = stmt.getConnection();
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
