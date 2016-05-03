package arrunchez.baumsoft.con.lafamiliaarrunchez;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.easing.linear.Linear;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import arrunchez.baumsoft.con.lafamiliaarrunchez.helpers.katana;

public class cuento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuento);


        Bundle extras = getIntent().getExtras();
        String fecha_inicial = extras.getString("fecha");

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputString1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        String inputString2 = "";
        if (fecha_inicial.equals("")){
            inputString2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        } else {
            inputString2 = fecha_inicial;
            Log.d("Fecha que llego", inputString2);
        }

        try {

            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            int dias = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            final Intent intento = new Intent(cuento.this, cuentouno.class);

            //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            //Log.d("Dias pasados en cuento", " - " + dias);
            if(dias == 0){
                intento.putExtra("fecha", "En un lugar no muy lejano vivía una familia de apellido Arrunchez, conformada por la mamá Alegría, el papá Prudencio,  su pequeño hijo Fantástico, Estrella la bebé,  y  la abuela, Consideración.\n" +
                        "Todos los días,  la familia Arrunchez se levantaba con la esperanza de encontrar la llave para abrir el tesoro que un Rey les había regalado.\n" +
                        "El Rey les había dicho que el secreto estaba en desarrollar cualidades, para que, mágicamente apareciera esa llave.\n" +
                        "Todos muy curiosos persiguieron a Justo, el secretario del Rey, para que les contara cuáles eran esas cualidades que debían desarrollar.\n" +
                        "Por su parte, Justo volaba apresuradamente pero alcanzó a escuchar el llamado de Fantástico:   “Justo, Justo, aquí…soy Fantástico…….”  y al acercarse, le preguntó insistentemente por el secreto.\n");
            }

            if(dias == -1){
                intento.putExtra("fecha", "Justo al escuchar a Fantástico preguntarle por el secreto contestó:\n" +
                        "\"Tienen que cumplir con la primera cualidad: La responsabilidad.\"  Y así, cada uno empezó a pensar cómo podía cumplir con esa cualidad. Alegría y Prudencio pensaron en asumir esta cualidad. Juntos trabajaron en el cuidado de la familia, preparando alimentos saludables, enseñándoles buenos hábitos a Estrella y a Fantástico como: hacer mucho ejercicio, cuidar  su cuerpo, sin olvidar cepillar sus dientes y usar la seda dental a diario. Ellos sabían que la mejor forma de enseñarle todo esto a sus pequeños era dándoles buen ejemplo. De igual modo, compartieron momentos agradables, de dialogo y de juego con sus hijos y con la abuela.\n" +
                        "\n" +
                        "Fantástico le preguntó a su abuela Consideración, cómo él podía ser más responsable. Su abuela le respondió “debes hacer tus tareas a tiempo, ser cumplido en el colegio y dedicar más tiempo a tu cuidado personal”.\n");
            }

            if(dias == -2){
                intento.putExtra("fecha", "Perfeccionada la primera cualidad para conseguir la llave, encontraron a una doncella quien era la encargada por el Rey para presentarles la segunda cualidad. Ella les dijo: “Queridos amigos: \"el Rey Pacífico les ordena a cada uno hacer un listado de tareas, para que avancen en la cualidad del Respeto. Recuerden que el respeto lleva a reconocer los derechos y la dignidad del otro. Incluye el respeto por nosotros mismos y por los demás”. \n" +
                        "\n" +
                        "Al día siguiente, la familia Arrunchez había cumplido con la tarea que la doncella les había encomendado. De esta manera cada uno de los miembros de la familia, tomó en cuenta las ideas de los demás; entendieron que cada persona es única y diferente a las otras y que lo más importante es dialogar para resolver las diferencias.\n" +
                        "Encontraron un largo camino y, mágicamente, la doncella de nuevo apareció. En ésta oportunidad, estaba acompañada de un pequeño duende amigo, quien era el encargado de recoger el listado propuesto por el Rey. A cambio, les entregó unos poderes mágicos para luchar por la justicia, la confianza y por la paz.\n");
            }

            if(dias == -3){
                intento.putExtra("fecha", "Armados con sus poderes, los miembros de la  familia Arrunchez empezaron una travesía por el inmenso castillo del Rey. Muy seguros de sí mismos, caminaban por largos laberintos siguiendo el destello de una estrella que orientaba su camino. Su principal interés era encontrar un cofre, que seguro escondía la siguiente cualidad. Caminaron por varias horas dentro del castillo hasta encontrar un puente levadizo que los conducía al cofre, el cual era de mil colores e irradiaba una fuerte luz titilante e incandescente. Cada uno de ellos, intentó abrirlo pero era imposible; era muy fuerte su cerradura. De repente, Prudencio pensó en utilizar los poderes que el duende les había entregado, pues era con lo único con que podrían abrir la cerradura. De repente, Prudencio pensó en utilizar los poderes que el duende les había entregado, pues era con lo único con que podrían abrir la cerradura. Efectivamente, estos poderes les permitieron encontrar el secreto guardado en el cofre, y ¡oh…. Sorpresa! guardaba un perfume, el perfume del Amor. \n" +
                        "Estaban felices, pues después de muchos trabajos, habían logrado lo que tanto buscaban.  Ya muy cansados compartieron juntos un lugar para descansar. Durmieron arrunchados, dándose mutuamente calor.  \n");
            }

            if(dias == -4){
                intento.putExtra("fecha", "Al día siguiente, Fantástico escuchó el canto de una cigarra que rondaba por el lugar, él la siguió y lo condujo a una despensa llena de alimentos; recogió frutos secos, miel y galletas para ganar energía;  también, encontró carne, huevos y leche para recobrar la fuerza de sus músculos y recordó que su abuela Consideración siempre le daba frutas, verduras y mucha agua para proteger su salud. Enseguida, corrió donde sus padres para compartir con ellos lo que había encontrado; comieron todos juntos y, ansiosos por encontrar la salida, buscaron un mapa que les orientara el camino para regresar. \n" +
                        "Al cabo del tiempo, regresaron a casa y, muy sorprendidos estaban, cuando vieron en la puerta a una pequeña gatita que parecía estar muy solitaria y con mucho frio; durante toda la noche había llovido mucho. No tenía hogar, por lo tanto la familia Arrunchez muy conmovida la acogió y le brindó cariño. Pero era necesario, además de darle una familia, ponerle un nombre, y es así como todos decidieron llamarla Fortaleza. A partir de ahí la familia la adoptó y estuvo muy pendiente del bienestar de su nueva mascota, quien les enseñó a no vencerse ante la dificultad.\n");
            }

            if(dias <= -5){
                intento.putExtra("fecha", "Después de un día largo de trabajo, los miembros de la familia Arrunchez se reunieron y empezaron a darse cuenta de que ya habían cumplido con varias cualidades que el Rey les exigió; también se acordaron de las experiencias vividas que les permitieron estar juntos. Recordaron a Pacífico, Justo, la doncella, el duende amigo, la cigarra y Fortaleza,  quienes les prestaron ayuda, les sirvieron de guía y les enseñaron las cualidades o valores que necesitaron para abrir el tesoro. \n" +
                        "Después de recordar todo esto estuvieron más unidos, alegres y se sentía un ambiente cálido y lleno de tranquilidad. Inmediatamente dentro de la casa encontraron el tesoro, como por arte de magia estaba abierto y Fortaleza reposaba dentro de él. \n" +
                        "\n" +
                        "La familia Arrunchez siguió viviendo momentos llenos de alegría, continuaron luchando juntos y teniendo muchos sueños en común.\n");
            }


            LinearLayout leercuento = (LinearLayout) findViewById(R.id.leercuento);
            leercuento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivityForResult(intento, 1);
                    //finish();

                }
            });





        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Request code", " " + requestCode);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("estado");
                katana kata = new katana();
                if(result.equals("si")){
                    kata.saveScore(cuento.this, 3, "3");
                } else {
                    kata.saveScore(cuento.this, 3, "6");
                }
                //kata = null;
                Log.d("Resultado", result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }


    }
}
