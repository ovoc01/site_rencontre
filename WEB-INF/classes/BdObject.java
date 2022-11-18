package objects;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Vector;

import con1.Connexion;
public class BdObject {
    String tableName;
    String primaryKey;
    Object primaryKey_value;
    String prefix ;
    int lengthPk;
    String nameFunction;


    public void setPrefix(String prefix1){
        prefix = prefix1;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setLengthPk(int lengthPk) {
        this.lengthPk = lengthPk;
    }

    public int getLengthPk() {
        return lengthPk;
    }

    public void setNameFunction(String nameFunction) {
        this.nameFunction = nameFunction;
    }

    public String getNameFunction() {
        return nameFunction;
    }

    public void setPrimaryKey_value(Object value){
        primaryKey_value = value;
    }

    public Object getPrimaryKey_value(){
        return this.primaryKey_value;
    }
    public void setPrimaryKey(String primaryKey) throws Exception{
        if(primaryKey==null||primaryKey.equals("")){
            throw new Exception("Null object cannot be foreign Key");
        }
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setTableName(String t)throws Exception{
        if(t==null || t.equals("")){
            throw new Exception("Table name cannot be empty or null");
        }
        tableName = t;
    }

    public String getTableName(){
        return tableName;
    }

    public BdObject(String tableName,String primaryKey) throws Exception{
        setPrimaryKey(primaryKey);
        setTableName(tableName);
    }

    public BdObject(){

    }

    public Object createObject(ResultSet val,boolean isArray) throws Exception {
        Object obj = this.getClass().getConstructor().newInstance();
        ResultSetMetaData rsm = val.getMetaData();
        if(!isArray){val.next();}
        for (int i = 0; i <rsm.getColumnCount() ; i++) {
            String field= obj.getClass().getDeclaredFields()[i].getName();
            String func = obj.getClass().getDeclaredMethod("set"+field, Object.class).getName();
            //   System.out.println(func+","+val.getObject(i+1));
            //System.out.println(val.getObject(i+1));
            obj.getClass().getDeclaredMethod(func, Object.class).invoke(obj,val.getObject(i+1));
        }

        return  obj;
    }

    public void insertToDb(Connexion c,String db_name) throws Exception{
        boolean connexIsNull= initConnexion(c,db_name);;

        ResultSetMetaData rsm = c.getConnection().createStatement().executeQuery("select * from "+getTableName()).getMetaData();
        String sql = "insert into "+getTableName()+" values(";
        try{
            for (int i = 0; i < rsm.getColumnCount() ; i++) {
                String func =getClass().getDeclaredMethod("get"+getClass().getDeclaredFields()[i].getName()).getName();
                if (getClass().getDeclaredMethod(func).invoke(this) instanceof Number ||
                        String.valueOf(getClass().getDeclaredMethod(func).invoke(this)).toLowerCase().equals("default")
                        || getClass().getDeclaredMethod(func).invoke(this) instanceof Boolean){
                    sql+= getClass().getDeclaredMethod(func).invoke(this);
                }
                else if(getClass().getDeclaredMethod(func).invoke(this) instanceof String
                        || getClass().getDeclaredMethod(func).invoke(this) instanceof Date ){
                    sql+="'" +String.valueOf(getClass().getDeclaredMethod(func).invoke(this))+"'" ;
                }
                System.out.println(sql);
                sql+=(i+1< rsm.getColumnCount())? ",":"";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        sql+=")";
        System.out.println(sql);
        c.getConnection().createStatement().executeUpdate(sql);
        c.getConnection().commit();
        if(connexIsNull){c.getConnection().close();}
    }

    public void displayObject(int nbr_column) throws  Exception{
        String info = "";
        for (int i = 0; i < nbr_column; i++) {
            info+=getClass().getDeclaredFields()[i].getName()+":";
            String func =getClass().getDeclaredMethod("get"+getClass().getDeclaredFields()[i].getName()).getName();
            info+= getClass().getDeclaredMethod(func).invoke(this) +" ";
        }
        System.out.println(info);
    }


    public void update(Connexion c, String[] fieldName,Object[] values){
        Vector<Integer> index = getIndex(fieldName);
        try {
            boolean isNull = initConnexion(c,"object");
            updateObjectAttr(index,values);
            String sql = "update "+getTableName()+" set ";
            for (int i = 0; i < values.length; i++) {
                sql+=fieldName[i]+"="+values[i];
                sql+=(i+1<fieldName.length)? ",":"";

            }
            sql+= "where "+getPrimaryKey()+"="+getPrimaryKey_value();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void initFK() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(int i = 0;i<getClass().getDeclaredFields().length;i++){
            String field  = getClass().getDeclaredFields()[i].getName();
            if(primaryKey.toLowerCase().equals(field.toLowerCase())){
                String func =getClass().getDeclaredMethod("get"+getClass().getDeclaredFields()[i].getName()).getName();
                primaryKey_value = getClass().getDeclaredMethod(func).invoke(this);
            }
        }
    }

    public Vector<Integer> getIndex(String[] fieldName){
        Vector<Integer> listIndex = new Vector<>();
        for (int i = 0; i <getClass().getDeclaredFields().length ; i++) {
            for (int j = 0; j <fieldName.length ; j++) {
                if(getClass().getDeclaredFields()[i].getName().toLowerCase().equals(fieldName[j].toLowerCase())){
                    listIndex.add(i);
                }
            }
        }

        return listIndex;
    }

    public void updateObjectAttr(Vector<Integer> index,Object[] values){
        try{
            for (int i = 0; i <index.size() ; i++) {
                String field= getClass().getDeclaredFields()[index.get(i)].getName();
                String func = getClass().getDeclaredMethod("set"+field, Object.class).getName();
                getClass().getDeclaredMethod(func, Object.class).invoke(this,values[i]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean initConnexion(Connexion c,String db_name) throws SQLException{
        boolean isNull = false;
        if(c==null){
            try{c = new Connexion(db_name,"postgres","password"); isNull = true;}
            catch (SQLException e){
                e.printStackTrace();
                throw new SQLException();}
        }
        return isNull;
    }

    public String contructPk(Connexion c) throws  Exception{
        String seq = getSequence(c);
        String id = compleZero(seq,getLengthPk()-getPrefix().length());
        setPrimaryKey_value(getPrefix()+id);
        return getPrefix()+id;
    }

    public String getSequence(Connexion c) throws  Exception{
        String query = "select nextval('"+getNameFunction()+"')";
        ResultSet rs = c.getConnection().createStatement().executeQuery(query);
        rs.next();
        String seq = rs.getString(1);
        return seq;
    }

    public String compleZero(String seq, int count){
        String sentence = "";
        for (int i = 0; i <count - seq.length() ; i++) {
            sentence+="0";
        }
        sentence+=seq;
        return sentence;
    }

    
}
