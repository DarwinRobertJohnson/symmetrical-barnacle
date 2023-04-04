
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.lang.model.util.ElementScanner14;

class dbHandler{
    private ArrayList<String> listItems;

    public dbHandler(){
        listItems=new ArrayList<String>();
    }
    public String revisionDate(String lastRevisionDate,int numRevision){
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

    public void showTopics(ArrayList<String> list) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection con=DriverManager.getConnection("jdbc:sqlite:../databaseFiles/revision");
        Statement stm=con.createStatement();
        ResultSet rs=stm.executeQuery("select * from subject1");
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        int num_revision;
        PreparedStatement ps=con.prepareStatement("update subject1 set next_revision_date=?,num_rev=? where topic=?");
        PreparedStatement ps2=con.prepareStatement("delete from subject1 where topic=?");
        while(rs.next()){
            if(date.equals(rs.getString("next_revision_date"))){
                num_revision=rs.getInt("num_rev");
                if(num_revision<5){
                list.add(rs.getString("topic"));
                System.out.println(rs.getString("topic"));
                num_revision++;
                String next_revision_date=revisionDate(rs.getString("next_revision_date"),num_revision);
                ps.setString(1, next_revision_date);
                ps.setInt(2, num_revision);
                ps.setString(3, rs.getString("topic"));
                ps.executeUpdate();
            }
            }
        }
        ps2.close();
        ps.close();
        stm.close();
        con.close();

    }

    public void addEntry() throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection con=DriverManager.getConnection("jdbc:sqlite:../databaseFiles/revision");
        Statement stm=con.createStatement();
        Scanner sc=new Scanner(System.in);

        String topic=new String();
        System.out.print("Enter Topic:");
        topic=sc.nextLine();
        
        String firstRevisionDate;

        PreparedStatement state=con.prepareStatement("insert into subject1 values(?,?,?,0)");
        state.setString(1,topic);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        state.setString(2, date);
        firstRevisionDate=revisionDate(date, 0);
        state.setString(3,firstRevisionDate);
        

        state.executeUpdate();
        state.close();
        stm.close();
        con.close();
    }

    public void printList(){
        for(String x:listItems){
            System.out.println(x);
        }
    }

}

class terminalInterface{
    private dbHandler db;
    private String command;
    Scanner sc;
    ArrayList<String> als;
    public terminalInterface(){
        db=new dbHandler();
        sc=new Scanner(System.in);
        command=new String();
    }

    public void runCommand() throws Exception{
        switch(command){
            case "show":
                db.showTopics(als);
                break;
            case "add":
                db.addEntry();
                break;
            case "exit":
                return;
            case "help":
                System.out.println("Command List:\nshow\nadd\nexit");
                break;
            default:
                System.out.println("Command List:\nshow\nadd\nexit");
        }
    }

    public void startProgram() throws Exception{
        while(command!="exit"){
            System.out.print(">>>");
            command=sc.nextLine();
            runCommand();
            if(command.equalsIgnoreCase("exit"))
                break;
        }
    }
}

public class dbHandlerTester{


    public static void main(String[] args) throws Exception{
        terminalInterface ti=new terminalInterface();
        ti.startProgram();
    }
}

