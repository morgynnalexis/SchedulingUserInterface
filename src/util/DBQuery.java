/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Morgynn
 */
public class DBQuery {
    
    private static PreparedStatement statement;
    
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException{
        
        statement = conn.prepareStatement(sqlStatement);
    }
    
    public static PreparedStatement getPreparedStatement(){
        
        return statement;
    }
    
}
