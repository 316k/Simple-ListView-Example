package ca.umontreal.iro.hurtubin.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Exemple d'utilisation d'un GridView avec un adapter sur mesure.
 * Notes : l'utilisation d'un ListView est sensiblement la même
 * la différence est au niveau du `grid.setNumColumns(...)` qui n'existe
 * pas pour les ListView
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> friends = new ArrayList<>();

    EditText name;

    Button add;

    GridView grid;

    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.edit);
        add = (Button) findViewById(R.id.button);
        grid = (GridView) findViewById(R.id.grid);

        /* Un GridView est comme un ListView à deux dimensions
           On peut choisir le nombre de colonnes à utiliser, le nombre
           de lignes est détecté automatiquement, selon le nombre d'éléments
           à afficher
         */
        grid.setNumColumns(2);

        friends.add("Jack");
        friends.add("Jeanne");
        friends.add("John");
        friends.add("Julie");

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                // Fonction appelée pour savoir combien d'éléments
                // seront affichés dans la liste
                return friends.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                /* Les Views sont "recyclées" lorsque possible
                   Lorsqu'une View disparaît de l'écran parce qu'on scroll, l'objet peut être
                   réutilisé plutôt que reconstruit de zéro.

                   La première fois que la grille est affichée, on doit cependant inflater les premiers
                   objets, d'où le if(convertView == null) { ... inflate ... }
                 */
                if (convertView == null) {
                    // Ici, on utilise un Layout prédéfini dans Android : le simple_list_item_1
                    // Ce layout comporte un seul élément : un TextView avec l'id "text1"
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                String name = friends.get(position);

                // On récupère le TextView à l'intérieur du convertView
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

                textView.setText(name);

                // Colorie le background d'un élément sur 2 en gris
                if (position % 3 == 0)
                    convertView.setBackgroundColor(Color.argb(100, 97, 197, 68));
                else if (position % 3 == 1)
                    convertView.setBackgroundColor(Color.GRAY);
                else
                    convertView.setBackgroundColor(Color.TRANSPARENT);

                return convertView;
            }
        };

        grid.setAdapter(adapter);

        // Ajoute un nom lorsqu'on clique sur le bouton
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = MainActivity.this.name;

                String name = editText.getText().toString();

                // On ajoute "name" à la grille affichée
                friends.add(name);

                // IMPORTANT : on doit notifier l'adapteur que les données utilisées ont changé
                adapter.notifyDataSetChanged();
            }
        });

        // Retire un nom lorsqu'on appuie longtemps dessus
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                friends.remove(i);
                // idem : on doit notifier l'adapteur que les données utilisées ont changé
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        // Modifie un nom lors d'un simple clic
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                friends.set(i, "Nom modifié");
                // Idem
                adapter.notifyDataSetChanged();
            }
        });
    }
}
