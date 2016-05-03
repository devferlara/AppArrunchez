package arrunchez.baumsoft.con.lafamiliaarrunchez.models;

/**
 * Created by baumsoft on 22/04/16.
 */
public class model_alimentos {

    private boolean a_1, a_2, a_3, a_4, a_5, a_6;
    private int id;
    private String nombre;

    public model_alimentos(boolean a1, boolean a2, boolean a3, boolean a4, boolean a5, boolean a6, int id_, String nombre_){
        this.a_1 = a1;
        this.a_2 = a2;
        this.a_3 = a3;
        this.a_4 = a4;
        this.a_5 = a5;
        this.a_6 = a6;
        this.id = id_;
        this.nombre = nombre_;
    }

    public boolean get_a1(){ return this.a_1; }
    public boolean get_a2(){ return this.a_2; }
    public boolean get_a3(){ return this.a_3; }
    public boolean get_a4(){ return this.a_4; }
    public boolean get_a5(){ return this.a_5; }
    public boolean get_a6(){ return this.a_6; }
    public int getId(){ return this.id; }
    public String getNombre(){ return this.nombre; }

}
