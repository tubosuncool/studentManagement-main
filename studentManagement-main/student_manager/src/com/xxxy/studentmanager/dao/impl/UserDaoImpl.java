package com.xxxy.studentmanager.dao.impl;

import com.xxxy.studentmanager.bean.User;
import com.xxxy.studentmanager.dao.UserDao;
import com.xxxy.studentmanager.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/*

        这是一个实现了UserDao接口的Java类，用于对用户数据进行访问。该类包含了三个方法，分别用于向数据库插入新用户、通过用户名查询数据库中是否存在该用户以及通过用户名和密码查询数据库中是否存在该用户。

        在插入新用户的方法中，该类首先获取数据库连接，然后使用预处理语句将用户信息插入数据库中，并返回插入的记录数。在查询用户是否存在的方法中，该类同样获取数据库连接，并使用预处理语句查询数据库中是否存在该用户，最后返回查询结果。
        在查询用户是否存在的同时，该类还定义了一个计数器变量count，用于记录查询结果，若查询结果存在，则将count的值设置为1。在查询用户是否存在的方法中，该类同样使用预处理语句查询数据库中是否存在该用户，并返回查询结果。

        在每个方法的最后，该类使用DBUtil类的close()方法关闭数据库连接、预处理语句和结果集对象。此外，每个方法都使用try-catch块来捕获可能发生的异常，并在出现异常时打印堆栈跟踪信息以便调试。
*/

public class UserDaoImpl implements UserDao {

    /**
     * 数据库插入的方法封装
     * @param user
     * @return 1代表插入成功  0失败
     */
    @Override
    public int insert(User user) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into user (username, password, telephone) values (?, ?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getTelephone());
            count = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, null);
        }
        return count;
    }

    /**
     * 通过用户名查询数据库
     * @param username 用户名
     * @return 0没有存在， 1存在
     */
    @Override
    public int selectByUsername(String username) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select username from user where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }

        return count;
    }

    /**
     * 通过用户名和密码查询数据库
     * @param username 用户名
     * @param password 密码
     * @return 1存在 0不存在
     */
    @Override
    public int selectByUnameAndPwd(String username, String password) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select username,password from user where username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }
        return count;
    }


}
