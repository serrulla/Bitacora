package fernandez.pau.bitacoralist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {

    public String beforeEdit;
    private int pos;
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        edit_text = (EditText) findViewById(R.id.edit_text);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        pos = intent.getIntExtra("pos", 0);
        beforeEdit = text;
        if (text!=null){
            edit_text.setText(text);
        }
    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra("text", edit_text.getText().toString());
        data.putExtra("pos", pos);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!edit_text.getText().toString().equals(beforeEdit)){
            String msg = getResources().getString(R.string.back_alert);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.back_title));
            builder.setMessage(msg);
            builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    onSave(edit_text);
                }
            });
            builder.setNegativeButton(R.string.dontsave, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                }

            });
            builder.create().show();
        }
        else{
            finish();
        }
    }
}
