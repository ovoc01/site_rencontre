create table Utilisateur(
    idUser varchar(200),
    nameUser varchar(200),
    pwd varchar(200),
    PRIMARY KEY (idUser)
)
;
create sequence
 idUser
 start with 1
 increment by 1
 maxvalue 80
;



create table information(
    idUser varchar(200),
    croyant varchar(100),
    salaire double precision,
    longueur int,
    diplome varchar(100),
    nationality varchar(200),
    FOREIGN KEY(idUser) REFERENCES Utilisateur(idUser)
)


