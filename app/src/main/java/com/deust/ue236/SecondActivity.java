package com.deust.ue236;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity implements Serializable {

    public ArrayList<String> listeTexte = new ArrayList<>();

    ListView listeMsg;

    String prenom = "'prénom'";

    public int nbContact=0;

    public HashMap<Object, Object> contactChoix = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView texteListeChoisi = findViewById(R.id.contactsChoisi);
        contactChoix = (HashMap<Object, Object>) getIntent().getSerializableExtra("choixContacts");
        nbContact = contactChoix.size();

        String contacts = contactChoix.keySet().toString().replaceAll("(^\\[|\\])", "");

        listeMsg = findViewById(R.id.listeMsg);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, listeTexte);

        arrayAdapter2.add("Joyeux noël, "+prenom+" !");
        arrayAdapter2.add("Joyeux hanouka, "+prenom+" !");
        arrayAdapter2.add("Bon anniversaire, "+prenom+" !");
        arrayAdapter2.add("Bonne année, "+prenom+" !");
        arrayAdapter2.add("Joyeuses pâques, "+prenom+" !");
        arrayAdapter2.add("Félicitations, "+prenom+" !");

        listeMsg.setAdapter(arrayAdapter2);

        texteListeChoisi.setText("Vous avez sélectionné : "+contacts);

        Button boutonBack = findViewById(R.id.boutonBack);
        boutonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }

        });

        Button boutonFin = findViewById(R.id.boutonValider);
        boutonFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }

        });

        Button boutonChangerNom = findViewById(R.id.changerNom);
        boutonChangerNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

    }

    public void showAlertDialog() {

    }

    public void openActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("contactListe", contactChoix);
        startActivity(intent);
    }

    public void sendMsg() {

        String msg = (String) listeMsg.getItemAtPosition(listeMsg.getCheckedItemPosition());

        if (msg == null) {
            Context context=getApplicationContext();
            Toast toast = Toast.makeText(context, "Choisissez un message à envoyer", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setMessage("Voulez-vous envoyer ce message ?");
            builder.setCancelable(false);

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    StringBuffer sb = new StringBuffer(msg);
                    for (int i = 0; i < 10; i++) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    for (Map.Entry m : contactChoix.entrySet()) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(String.valueOf(m.getValue()), null, String.valueOf(sb) + String.valueOf(m.getKey()) + " !", null, null);
                    }

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Les messages ont bien été envoyés", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            });

            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Les messages n'ont pas été envoyé", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            AlertDialog dialog = builder.create();

            dialog.setTitle("Envoyer le message");
            dialog.show();

        }

    }
}