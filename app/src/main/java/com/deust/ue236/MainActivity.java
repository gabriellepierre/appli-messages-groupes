package com.deust.ue236;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> listeContact = new ArrayList<>();

    public HashMap<String, String> hashMap = new HashMap<>();
    public HashMap<Object, Object> contactsChoix = new HashMap<>();

    public ArrayList<String> arrayList = new ArrayList<>();

    ListView maListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maListe = findViewById(R.id.maListe);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);

        maListe.setAdapter(arrayAdapter);

        Button boutonValider = findViewById(R.id.bouton1);
        boutonValider.setOnClickListener(v -> openActivity2());

        Button selectContacts = findViewById(R.id.selectContact);
        selectContacts.setOnClickListener((v -> selectAllContacts()));

        readContacts();

    }

    public void readContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount()>0) {
            while (cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                int num = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (num == 1) {
                    String[] selectionArgs2 = new String[]{id};
                    Cursor cur2 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", selectionArgs2, null);
                    while (cur2.moveToNext()) {
                        String phone = cur2.getString(cur2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        hashMap.put(name, phone);
                        arrayList.add(name);
                    }

                }
            }
        }
    }

    public void selectAllContacts() {
        for (int i=0; i<arrayList.size(); i++) {
            maListe.setItemChecked(i, true);
        }
    }

    public void openActivity2() {

        SparseBooleanArray checkedItems = maListe.getCheckedItemPositions();

        Intent intent = new Intent(this, SecondActivity.class);

        if (checkedItems != null) {
            for (int i=0; i<checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    String item = maListe.getAdapter().getItem(
                            checkedItems.keyAt(i)).toString();
                    listeContact.add(item);
                }
            }
        }

        if (listeContact.isEmpty()) {
            Context context=getApplicationContext();
            Toast toast = Toast.makeText(context, "Choisissez au moins un contact", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            for(Map.Entry m : hashMap.entrySet()) {
                if (listeContact.contains(m.getKey())){
                    contactsChoix.put(m.getKey(), m.getValue());
                }
            }
            intent.putExtra("choixContacts", contactsChoix);
            startActivity(intent);
        }
    }
}