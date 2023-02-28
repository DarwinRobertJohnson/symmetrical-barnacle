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

        int month=Integer.parseInt(str.substring(3,5));
        int day=Integer.parseInt(str.substring(0,2));
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

        next_rev_date=dayString+"-"+monthString+str.substring(5,10);

        return next_rev_date;
    }

    public static void addEntry() throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection con=DriverManager.getConnection("jdbc:sqlite:databaseFiles/revision");
        Statement stm=con.createStatement();
        Scanner sc=new Scanner(System.in);

        String topic=new String();
        topic=sc.nextLine();
        
        String firstRevisionDate;

        PreparedStatement state=con.prepareStatement("insert into subject1 values(?,?,?,0)");
        System.out.println("statement prepared");
        state.setString(1,topic);
        System.out.println("num1 set");
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        System.out.println(date);    //Local Date
        state.setString(2, date);
        System.out.println("num2 set");
        firstRevisionDate=revisionDate(date, 0);
        System.out.println("First revision date got");
        state.setString(3,firstRevisionDate);
        System.out.println("num3 set");

        state.executeUpdate();
        state.close();
        stm.close();
        con.close();
    }

    public static void main(String[] args) throws Exception{
        addEntry();
        //System.out.println(revisionDate("28-02-2023",0));
    }
}