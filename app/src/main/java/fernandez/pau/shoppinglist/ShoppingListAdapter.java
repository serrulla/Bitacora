package fernandez.pau.shoppinglist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.List;

/**
 * Created by pablofd on 06/03/2018.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {
    public ShoppingListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ShoppingItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Crear un nou View si és necessari (no cal si convertView no és null)
        View root = convertView; // arrel d'un item de la llista
        if (root == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            root = inflater.inflate(R.layout.shopping_item, parent, false);
        }

        CheckBox checkBox = (CheckBox) root.findViewById(R.id.checkBox);
        ShoppingItem item = getItem(position);
        checkBox.setText(item.getText());

        return root;
    }
}
