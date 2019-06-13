package com.qk365.datadict.common;


import java.sql.*;


public class JDBCUtlTool {
    public static boolean getConnection( String driver, String url, String name,String pwd){
        try{
            Class.forName(driver);
            Connection  conn=DriverManager.getConnection(url,name,pwd);//获取连接对象
            if(!conn.isClosed()){
                System.out.println("测试成功!");
                conn.close();
                return true;
            }else {
                System.out.println("测试失败!");
                conn.close();
                return false;
            }

        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
        try{
            if(rs!=null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(ps!=null){
                ps.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
