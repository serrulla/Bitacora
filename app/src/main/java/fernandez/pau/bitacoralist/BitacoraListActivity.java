package fernandez.pau.bitacoralist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BitacoraListActivity extends AppCompatActivity {

    public static final String FILENAME = "items.txt";
    public static final int MAX_BYTES = 10000;
    private ListView list;
    private ArrayList<BitacoraItem> items; // Model de dades
    private BitacoraListAdapter adapter;
    private EditText new_item;

    private void writeItemList() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (BitacoraItem item : items) {
                String line = String.format("%s;%b\n", item.getText(), item.getTime());
                fos.write(line.getBytes());
            }
            fos.close();
        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {
            Toast.makeText(this, "No puc escriure el fitxer", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean readItemList() {
        items = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            byte[] buffer = new byte[MAX_BYTES];
            int nread = fis.read(buffer);
            if (nread > 0) {
                String content = new String(buffer, 0, nread);
                String[] lines = content.split("\n");
                for (String line : lines) {
                    if (!line.isEmpty()) {
                        String[] parts = line.split(";");
                        items.add(new BitacoraItem(parts[0]));
                    }
                }
            }
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            Toast.makeText(this, "No puc llegir el fitxer", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora_list);

        if (!readItemList()) {
            Toast.makeText(this, "Bienvenido a tu cuaderno de bitácoras", Toast.LENGTH_SHORT).show();
        }

        list = (ListView) findViewById(R.id.list);
        new_item = (EditText) findViewById(R.id.new_item);

        adapter = new BitacoraListAdapter(this, R.layout.bitacora_item, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                adapter.notifyDataSetChanged();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                onRemoveItem(pos);
                return true;
            }
        });

        // EXEMPLE d'ús de Date

        // Obtenir un instant de temps
        Date date = new Date(); // agafa l'instant actual del dispositiu

        // Utilitzem un objecte Calendar per esbrinar els detalls de la data
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min  = calendar.get(Calendar.MINUTE);

        Toast.makeText(
                this,
                String.format("%02d/%02d/%04d %02d:%02d", day, month+1, year, hour, min),
                Toast.LENGTH_LONG
        ).show();

    }

    private void onRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(
                String.format(Locale.getDefault(),
                        "Estàs segur que vols esborrar '%s'",
                        items.get(pos).getText())
        );
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                items.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void onAddItem(View view) {
        String item_text = new_item.getText().toString();
        if (!item_text.isEmpty()) {
            items.add(new BitacoraItem(item_text));
            adapter.notifyDataSetChanged();
            new_item.setText("");
            list.smoothScrollToPosition(items.size() - 1);
        }
    }

    private void edit(String text, int position, int requestCode){
        Intent intent = new Intent(this, Edit)

    }
}
