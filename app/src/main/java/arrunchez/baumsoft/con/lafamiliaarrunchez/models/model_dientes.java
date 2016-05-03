package arrunchez.baumsoft.con.lafamiliaarrunchez.models;

/**
 * Created by baumsoft on 25/04/16.
 */
public class model_dientes {

    private String nombre;
    private boolean estado;
    private int id;

    public model_dientes(String nombre, boolean estado, int id){
        this.nombre = nombre;
        this.estado = estado;
        this.id = id;
    }

    public String getNombre(){ return this.nombre; }
    public boolean getEstado(){ return this.estado; }
    public int getId(){ return this.id; }

}
