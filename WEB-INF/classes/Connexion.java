package con1;
import java.sql.*;
public class Connexion {
    Connection connection;
    String db_name;
    String user;
    String pwd;
    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public Connexion(String db_name,String user,String pwd) throws SQLException{
        setDb_name(db_name);
        setUser(user);
        setPwd(pwd);
        try {
            db_connect();
            getConnection().setAutoCommit(false);
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    public void db_connect() throws Exception{
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost/"+getDb_name(),getUser(),getPwd());

        //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scott","root","password");
        //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+getDb_name(),getUser(),getPwd());
    }

    public Connection getConnection(){return  connection;}
    
}
