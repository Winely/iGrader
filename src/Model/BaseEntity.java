package Model;

import Database.DAO;

public class BaseEntity {
    public void save(){
        new DAO().save(this);
    }

    public void update(){
        new DAO().update(this);
    }

    public void refresh() { new DAO().refresh(this);}
}
