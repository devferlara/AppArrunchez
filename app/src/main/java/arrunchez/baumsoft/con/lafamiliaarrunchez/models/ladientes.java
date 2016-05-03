package arrunchez.baumsoft.con.lafamiliaarrunchez.models;

import arrunchez.baumsoft.con.lafamiliaarrunchez.gendao.Calidientes;

/**
 * Created by dayessi on 10/04/16.
 */
public class ladientes {

    private String nombres, avatar;

    public Calidientes cdientes;

    public ladientes(String nombres, String avatar, Calidientes cdientes) {
        this.nombres = nombres;
        this.avatar = avatar;
        this.cdientes = cdientes;
    }

    public String getNombres() { return this.nombres; }
    public String getAvatar() { return this.avatar; }

}
