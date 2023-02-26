import java.lang.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class app{

    public static void addEntry() throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection con=DriverManager.getConnection("jdbc:sqlite:databaseFiles/revision");
        Statement stm=con.createStatement();
        Scanner sc=new Scanner(System.in);

        String topic=new String();
        topic=sc.nextLine();
        
        PreparedStatement state=con.prepareStatement("insert into subject1 values(?,?,?,0)");
        System.out.println("statement prepared");
        state.setString(1,topic);
        System.out.println("num1 set");
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        state.setString(2, date);
        System.out.println("num2 set");
        state.setInt(3,0);
        System.out.println("num3 set");

        state.executeUpdate();
        state.close();
        stm.close();
        con.close();
    }

    public static void main(String[] args) throws Exception{
        addEntry();
    }
}