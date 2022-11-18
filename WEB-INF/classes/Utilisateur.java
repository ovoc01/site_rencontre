package objects;

import con1.Connexion;

import java.sql.ResultSet;
import java.sql.Statement;
public class Utilisateur extends BdObject {
    String IdUser;
    String Nom;
    String Pwd;

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        this.Pwd = pwd;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public Utilisateur(String pwd, String nom) throws Exception{
        super.setPrefix("USR");
        super.setNameFunction("idUser");
        Connexion c = new Connexion("postgres","postgres","password");
        setLengthPk(8);
        setIdUser(contructPk(c));
        setTableName("Utilisateur");
        setNom(nom);
        setPwd(pwd);
        //setIdUser("default");
    }

    public Utilisateur() throws Exception{
        super.setPrefix("USR");
        super.setNameFunction("idUser");
        Connexion c = new Connexion("postgres","postgres","password");
        setLengthPk(8);
        setIdUser(contructPk(c));
        setTableName("Utilisateur");
    }
    public boolean isInDatabase() throws Exception{
        Connexion c = null;
        boolean  isInDatabase = false;
        try{
            c = new Connexion("postgres","postgres","password");
            Statement statement = c.getConnection().createStatement();
            String sql = "Select * from Utilisateur where nameUser='"+getNom()+"' and pwd='"+getPwd()+"'";
            // System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            int i = 0;
            while(resultSet.next()){
                i++;
            }
            if(i > 0){
                isInDatabase = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            c.getConnection().close();
        }

        return isInDatabase;
    }

    public String getIdInDatabase() throws Exception{
        Connexion c = null;
        String  idUser = "";
        try{
            c = new Connexion("postgres","postgres","password");
            Statement statement = c.getConnection().createStatement();
            String sql = "Select * from Utilisateur where nameUser='"+getNom()+"' and pwd='"+getPwd()+"'";
            // System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            int i = 0;
            while(resultSet.next()){
                idUser = resultSet.getString(1);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            c.getConnection().close();
        }

        return idUser;
    }
    
}
    