package com.deust.ue236;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FourthActivity extends AppCompatActivity {

    TextView textContact;

    EditText inputNom;

    public String nomContact;

    public HashMap<Object, Object> listeContactChange = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        nomContact = (String) getIntent().getSerializableExtra("nomContact");

        listeContactChange = (HashMap<Object, Object>) getIntent().getSerializableExtra("contactsHash");

        inputNom = (EditText) findViewById(R.id.inputChangeNom);

        textContact = findViewById(R.id.nomContactChange);
        textContact.setText("Changez le nom de : "+nomContact);

        Button btnValide = findViewById(R.id.boutonChangeNom);
        btnValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nouveauNom = String.valueOf(inputNom.getText());
                if (nouveauNom.isEmpty()) {
                    Context context=getApplicationContext();
                    Toast toast = Toast.makeText(context, "Veuillez écrire un nom", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    for (Map.Entry m : listeContactChange.entrySet()) {
                        if (nomContact.contains((CharSequence) m.getKey())) {
                            listeContactChange.remove(nomContact);
                            listeContactChange.put(nouveauNom, m.getValue());
                            Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                            intent.putExtra("contactListe", listeContactChange);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}