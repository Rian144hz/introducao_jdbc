package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args){

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            st = conn.createStatement();

           rs = st.executeQuery("select * from department");

           while (rs.next()){
               int id = rs.getInt("Id");
               String name = rs.getString("Name");

               System.out.println(id+", "+name);
           }
        }catch (DbException | SQLException db){
            System.out.println(db.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
