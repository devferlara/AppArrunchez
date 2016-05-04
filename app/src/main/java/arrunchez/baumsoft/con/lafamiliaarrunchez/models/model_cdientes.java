package arrunchez.baumsoft.con.lafamiliaarrunchez.models;

/**
 * Created by dayessi on 3/05/16.
 */
public class model_cdientes {
    private Long id;
    private String title, descri;

    public model_cdientes(Long id, String title, String descri) {
        this.id = id;
        this.title = title;
        this.descri = descri;
    }

    public Long getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescri() { return this.descri; }

}
