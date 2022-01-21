package com.deust.ue236;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity implements Serializable {

    public ArrayList<String> listeContactsTexte = new ArrayList<>();

    public HashMap<Object, Object> contactList = new HashMap<>();

    ListView listeContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        contactList = (HashMap<Object, Object>) getIntent().getSerializableExtra("contactListe");

        listeContact= findViewById(R.id.choixChange);

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, listeContactsTexte);

        for(Map.Entry m : contactList.entrySet()) {
            String contacts = String.valueOf(m.getKey());
            arrayAdapter3.add(contacts);
        }

        listeContact.setAdapter(arrayAdapter3);

        Button boutonChanger = findViewById(R.id.boutonChange);
        boutonChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        Button boutonRetour = findViewById(R.id.boutonRetour);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

    }

    public void openActivity4() {
        String contact = (String) listeContact.getItemAtPosition(listeContact.getCheckedItemPosition());
        if (contact == null) {
            Context context=getApplicationContext();
            Toast toast = Toast.makeText(context, "Choisissez un contact", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this, FourthActivity.class);
            intent.putExtra("nomContact", contact);
            intent.putExtra("contactsHash", contactList);
            startActivity(intent);
        }
    }

    public void openActivity2() {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("choixContacts", contactList);
        startActivity(intent);
    }
}