package arrunchez.baumsoft.con.lafamiliaarrunchez.models;

/**
 * Created by dayessi on 3/05/16.
 */
public class model_cdientes {
    private Long id;
    private String titulo_armado, titulo, descripcion, comentarios;

    public model_cdientes(Long id, String titulo_armado, String titulo, String descripcion, String comentarios) {
        this.id = id;
        this.titulo_armado = titulo_armado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.comentarios = comentarios;
    }

    public Long getId() { return this.id; }
    public String getTitulo_armado() { return this.titulo_armado; }
    public String getTitulo() { return this.titulo; }
    public String getDescripcion() { return this.descripcion; }
    public String getComentarios() { return this.comentarios; }

}
