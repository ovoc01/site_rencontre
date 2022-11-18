package objects;

public class Info extends BdObject {
    String IdUser;
    boolean EstCroyant;
    double Salaire;
    int Longueur;
    String Diplome;
    String Nationalite;
    String Genre;
    
    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(Object idUser) throws Exception {
        if(idUser==null){
            throw new Exception("idUser can not be null");
        }
        IdUser = String.valueOf(idUser);
    }

    public boolean getEstCroyant() {
        return EstCroyant;
    }

    public void setEstCroyant(Object estCroyant)  throws Exception{
        if(estCroyant==null){
            throw new Exception("croyant can not be null");
        }

        EstCroyant =Boolean.parseBoolean( String.valueOf(estCroyant));
    }

    public double getSalaire() {
        return Salaire;
    }

    public void setSalaire(Object Salaire)  throws Exception{
        if(Salaire==null )   {
            throw new Exception("Salaire can not be null");
        }

        this.Salaire = Double.parseDouble(String.valueOf(Salaire));
    }

    public int getLongueur() {
        return Longueur;
    }

    public void setLongueur(Object Longueur)  throws Exception{
        if(Longueur==null){
            throw new Exception("Longueur can not be null");
        }
        this.Longueur = Integer.parseInt(String.valueOf(Longueur));
    }

    public String getDiplome() {
        return Diplome;
    }

    public void setDiplome(Object Diplome) throws Exception{
        if(Diplome==null){
            throw new Exception("Diplome can not be null");
        }

        this.Diplome = String.valueOf(Diplome);
    }

    public String getNationalite() {
        return Nationalite;
    }

    public void setNationalite(Object Nationalite) throws Exception{
        if(Nationalite==null){
            throw new Exception("Nationalite can not be null");
        }

        this.Nationalite = String.valueOf(Nationalite);
    }

    public void setGenre(Object genre) throws Exception{
        if(genre==null){
            throw new Exception("Genre can not be null");
        }
        Genre = String.valueOf(genre);
    }

    public Info() throws Exception{

    }

    public Info(Object idUser,Object estCroyant,Object salaire,Object longueur,Object diplome, Object nationalite ,Object genre)throws Exception{
        setIdUser(idUser);
        setDiplome(diplome);
        setEstCroyant(estCroyant);
        setLongueur(longueur);
        setSalaire(salaire);
        setNationalite(nationalite);
        setGenre(genre);
        setTableName("information");
    }


}
