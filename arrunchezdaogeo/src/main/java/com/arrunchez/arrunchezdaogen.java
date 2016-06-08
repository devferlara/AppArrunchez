package com.arrunchez;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by dayessi on 7/04/16.
 */
public class arrunchezdaogen {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "arrunchez.baumsoft.con.lafamiliaarrunchez.gendao");
        addTables(schema);
        new DaoGenerator().generateAll(schema, "../AppArrunchez/app/src/main/java/");
    }


    public static void addTables(Schema schema) {

        /*
        * Tablas parametricas
        * */

        Entity avatars = schema.addEntity("Avatars");
        avatars.addIdProperty().autoincrement();
        avatars.addStringProperty("avatar").notNull();

        Entity alimentos = schema.addEntity("Alimentos");
        alimentos.addIdProperty().autoincrement();
        alimentos.addStringProperty("alimento").notNull();

        // Fin tablas parametricas


        Entity participantes = schema.addEntity("Participantes");
        participantes.addIdProperty().autoincrement();
        participantes.addStringProperty("participante").notNull();
        Property avatar_id = participantes.addLongProperty("avatar_id").notNull().getProperty();
        participantes.addToOne(avatars, avatar_id);

        Entity calificaciones = schema.addEntity("Calificaciones");
        calificaciones.addIdProperty().autoincrement();
        calificaciones.addStringProperty("date");
        Property participante_id_uno = calificaciones.addLongProperty("participante_id").notNull().getProperty();
        calificaciones.addToOne(participantes, participante_id_uno);

        Entity calimentos = schema.addEntity("Calimentos");
        calimentos.addIdProperty().autoincrement();
        calimentos.addBooleanProperty("estado");
        Property calificacione_id_uno = calimentos.addLongProperty("calificacion_id").notNull().getProperty();
        calimentos.addToOne(calificaciones, calificacione_id_uno);
        Property alimento_id = calimentos.addLongProperty("alimento_id").notNull().getProperty();
        calimentos.addToOne(alimentos, alimento_id);

        Entity calidientes = schema.addEntity("Calidientes");
        calidientes.addIdProperty().autoincrement();
        calidientes.addBooleanProperty("estado");
        Property calificacion_id_dos = calidientes.addLongProperty("calificacion_id").notNull().getProperty();
        calidientes.addToOne(calificaciones, calificacion_id_dos);

        Entity last_score_foods = schema.addEntity("Last_score_foods");
        last_score_foods.addIdProperty().autoincrement();
        last_score_foods.addStringProperty("score");

        Entity last_score_teeth = schema.addEntity("Last_score_teeth");
        last_score_teeth.addIdProperty().autoincrement();
        last_score_teeth.addStringProperty("score");

        Entity last_score_tale = schema.addEntity("Last_score_tale");
        last_score_tale.addIdProperty().autoincrement();
        last_score_tale.addStringProperty("score");

        Entity mac_bluetooth = schema.addEntity("Mac_bluetooth");
        mac_bluetooth.addIdProperty();
        mac_bluetooth.addStringProperty("mac");

    }
}

