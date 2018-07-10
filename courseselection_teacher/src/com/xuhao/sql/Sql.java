package com.xuhao.sql;

import javafx.scene.control.Alert;
import java.sql.*;

import com.xuhao.sql.SqlBase;

public class Sql {

    //登录部分
    public int userChecker(String uid, String pwd) {
        /*
         * @return val
         * val = 0: 用户名或密码错误
         * val = 1: 用户为管理员且密码正确
         * val = 2: 用户为学生且密码正确
         */
        int val = 0;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT uid, upwd,utype from user";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("uid").equals(uid) && rs.getString("upwd").equals(pwd) && rs.getString("utype").equals("0")) {
                    val = 1;
                } else if (rs.getString("uid").equals(uid) && rs.getString("upwd").equals(pwd) && rs.getString("utype").equals("1")) {
                    val = 0;
                }
            }
            rs.close();
            sqlExc.destroy(stmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }
    public String getUserName(String uid) {
        String name = "用户";
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT uid, uname from user";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("uid").equals(uid)) {
                    name = rs.getString("uname");
                }
            }
            rs.close();
            sqlExc.destroy(stmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    //course部分
    public void insertCourse(String tid, String cid, String cname, String point){
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT tid from teacher";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("tid").equals(tid)) {
                    //插入
                    String sqlInsert = "INSERT INTO course(cid, cname, tid, point) VALUES("
                            + cid
                            + ",\'" + cname + "\',"
                            + tid
                            + "," + point
                            + ")";
                    stmt.executeUpdate(sqlInsert);//会关闭rs
                    break;
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("警告");
                    alert.setHeaderText("教师信息错误");
                    alert.showAndWait();
                }
            }
            sqlExc.destroy(stmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAllCourse(){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT course.cid, course.cname, course.tid, course.point, teacher.tid, teacher.tname " +
                    "FROM course, teacher " +
                    "WHERE course.tid = teacher.tid";
            rs = stmt.executeQuery(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet searchCourse(String cname){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            //根据cname从course表中获取课程信息，并根据对应tid从teacher表中获取教师姓名
            /*
            * SELECT course.cid, course.cname, course.tid, course.point, teacher.tid, teacher.tname FROM course, teacher
            * WHERE course.tid == teacher.tid AND course.cname == cname
             */
            String sql = "SELECT course.cid, course.cname, course.tid, course.point, teacher.tid, teacher.tname " +
                    "FROM course, teacher " +
                    "WHERE course.tid = teacher.tid AND course.cname = " +
                    "\'" + cname + "\'";
            rs = stmt.executeQuery(sql);
            //sqlExc.destroy(stmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    } //其余两个都是通过getAllCouse后遍历resultset来进行查找的
    public void changeCourse(String id, String name, String tid, String point){
        boolean exist = false;
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT  *" +
                    "FROM course";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("cid").equals(id)){
                    exist = true;
                    //修改标志位并执行修改语句
                    sql = "UPDATE course SET cid =" +
                            id +
                            ",cname = " +
                            "\"" + name + "\"" +
                            ",tid = " +
                            tid +
                            ",point = " +
                            point+
                            " WHERE cid =" +
                            id;
                    stmt.executeUpdate(sql);
                    break;
                }
            }
            if(exist == false){
                //执行插入语句
                sql = "INSERT INTO course(cid,cname, tid, point) VALUES("+
                        "\"" + id + "\"," +
                        "\"" + name + "\"," +
                        "\"" + tid + "\"," +
                        "\"" + point + "\"" + ")";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteCourse(String id){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "DELETE FROM course " +
                    "WHERE cid =" + id;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //student部分
    public ResultSet getAllStudent(){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT  *" +
                    "FROM student";
            rs = stmt.executeQuery(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void changeStudent(String id, String name, String gender, String major, String year){
        boolean exist = false;
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT  *" +
                    "FROM student";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("sid").equals(id)){
                    exist = true;
                    //修改标志位并执行修改语句
                    Statement stmt2 = sqlExc.create();
                    sql = "UPDATE student SET sid =" +
                            id+
                            ",sname = " +
                            "\"" + name + "\"" +
                            ",gender = " +
                            gender +
                            ",major = " +
                            "\"" + major + "\"" +
                            ",year = " +
                            year+
                            " WHERE sid =" + id;
                    stmt2.executeUpdate(sql);
                    break;
                }
            }
            if(exist == false){
                //执行插入语句
                sql = "INSERT INTO student(sid,sname, gender, major, year) VALUES("+
                        id +
                        ",\"" + name + "\"," +
                        gender +
                        ",\"" + major + "\"," +
                        year +")";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteStudent(String id){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "DELETE FROM student " +
                    "WHERE sid =" +
                    "\"" + id + "\"";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //teacher部分
    public ResultSet getAllTeacher(){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT  *" +
                    "FROM teacher";
            rs = stmt.executeQuery(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void changeTeacher(String id, String name){
        boolean exist = false;
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT  *" +
                    "FROM teacher";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("tid").equals(id)){
                    exist = true;
                    //修改标志位并执行修改语句
                    sql = "UPDATE teacher SET tid =" +
                            id +
                            ",tname = " +
                            "\"" + name + "\"" +
                            " WHERE tid =" +
                            id;
                    stmt.executeUpdate(sql);
                    break;
                }
            }
            if(exist == false){
                //执行插入语句
                sql = "INSERT INTO teacher(tid,tname) VALUES("+
                       id +"," +
                        "\"" + name + "\"" + ")";
                int i = stmt.executeUpdate(sql);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteTeacher(String id){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "DELETE FROM teacher " +
                    "WHERE tid =" +
                    id;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //选课部分
    public ResultSet getTakenCourse(String sid){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "SELECT course.cid, course.cname,course.point,teacher.tname" +
                    " FROM teacher,course,takecourse" +
                    " Where takecourse.cid = course.cid AND course.tid = teacher.tid AND takecourse.sid = " +
                    sid;
            rs = stmt.executeQuery(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    public void takeCourse(String sid, String cid){
        //tid是外键，所以不用检测是否存在
        //向takecourse插入数据
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "INSERT INTO takecourse(sid,cid) VALUES("+
                    sid +"," +
                    cid + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cancelCourse(String sid, String cid){
        ResultSet rs = null;
        try {
            SqlBase sqlExc = new SqlBase();
            Statement stmt = sqlExc.create();
            String sql = "DELETE FROM takecourse " +
                    "WHERE sid =" +
                    sid + " AND " +
                    " cid =" +
                    cid;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

