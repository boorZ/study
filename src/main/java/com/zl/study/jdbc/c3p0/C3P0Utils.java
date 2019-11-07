package com.zl.study.jdbc.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zl.study.jdbc.BeanConfig;

import java.beans.PropertyVetoException;
import java.sql.*;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/4/28
 * 版 本: v1.0
 **/
public class C3P0Utils {

    private static  ComboPooledDataSource cpds;
    private static Connection con;
    private static Statement sta;
    private static ResultSet rs;
    private static PreparedStatement ps;

    /**
     * 获取Connection
     * @return
     * @throws SQLException
     * @throws PropertyVetoException
     */
    public static Connection getconnection() throws SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql:///"+ BeanConfig.JPA_DATABASE+"?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        cpds.setUser(BeanConfig.JPA_USER);
        cpds.setPassword(BeanConfig.JPA_PASSWORD);
        // 得到一个Connection
        con = cpds.getConnection();
        return con;
    }

    public static ResultSet getconnection(String sql) throws SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://"+BeanConfig.JPA_IP+"/"+BeanConfig.JPA_DATABASE+
                "?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        cpds.setUser(BeanConfig.JPA_USER);
        cpds.setPassword(BeanConfig.JPA_PASSWORD);
        // 最多有多少个连接
//        cpds.setMaxPoolSize(10);
//        cpds.setInitialPoolSize(5);
        // 得到一个Connection
        con = cpds.getConnection();
        ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    public static ResultSet getConnection(String sql) throws SQLException, PropertyVetoException {
        con = getconnection();
        sta = con.createStatement();
        rs = sta.executeQuery(sql);
        return rs;
    }
    public static void close() throws SQLException {
        if (rs != null) rs.close();
        if (sta != null) sta.close();
        if (con != null) con.close();
        if (cpds != null) cpds.close();
    }
    public static void closePs() throws SQLException {
        if (ps != null) ps.close();
        if (con != null) con.close();
        if (cpds != null) cpds.close();
    }
    public static void closeSimp() throws SQLException {
        if (con != null) con.close();
        if (cpds != null) cpds.close();
    }
}
