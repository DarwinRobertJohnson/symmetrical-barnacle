import java.lang.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class app{

    public static String revisionDate(String lastRevisionDate,int numRevision){
        String str=lastRevisionDate;
        int num_revision=numRevision;
        String next_rev_date;

        int monthDays[]={31,28,31,30,31,30,31,31,30,31,30,31};

        int month=Integer.parseInt(str.substring(5,7));
        int day=Integer.parseInt(str.substring(8,10));
        int mult=1;
        for(int i=0;i<num_revision;i++){
            mult*=2;
        }

        day=day+mult;

        if(day>monthDays[month-1]){
            day=day-monthDays[month-1];
            month++;
        }

        String monthString=""+month;
        if(month<9){
            monthString="0"+month;
        }
        
        String dayString=""+day;
        if(day<9){
            dayString="0"+day;
        }

        next_rev_date=str.substring(0,5)+monthString+"-"+dayString;

        return next_rev_date;
    }

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
        //addEntry();
        System.out.println(revisionDate("2023-02-20",0));
    }
}