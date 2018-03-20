package fernandez.pau.bitacoralist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pablofd on 06/03/2018.
 */

public class BitacoraListAdapter extends ArrayAdapter<BitacoraItem> {
    public BitacoraListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BitacoraItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Crear un nou View si és necessari (no cal si convertView no és null)
        View root = convertView; // arrel d'un item de la llista
        if (root == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            root = inflater.inflate(R.layout.bitacora_item, parent, false);
        }

        TextView bitacoraTitle = root.findViewById(R.id.bitacoraTitle);
        BitacoraItem item = getItem(position);
        bitacoraTitle.setText(item.getText());

        TextView bitacoraTime = root.findViewById(R.id.bitacoraTime);
        bitacoraTime.setText(item.getTime());

        return root;
    }
}
