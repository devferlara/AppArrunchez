package arrunchez.baumsoft.con.lafamiliaarrunchez.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baumsoft on 29/04/16.
 */
public class model_calificacion_individual {

    private int id;
    List<Integer> resultados_alimentos = new ArrayList<Integer>();
    private int resultados_dientes;

    public model_calificacion_individual(int id, List<Integer> resultados_alimentos, int resultados_dientes){
        this.id = id;
        this.resultados_alimentos = resultados_alimentos;
        this.resultados_dientes = resultados_dientes;
    }

    public int getId(){ return id; }
    public List<Integer> getResultados_alimentos(){ return resultados_alimentos; }
    public int getResultados_dientes(){ return resultados_dientes; }
}
