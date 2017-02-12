package com.example.dominik.wirtualnakarta;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Dominik on 21.11.2016.
 */
public class Transactions extends Activity {

    private String actual_card;
    int cards_amount = 0;
    int actual = 0;
    Account cards_table[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        TextView t1 = (TextView) findViewById(R.id.textView9);

        Intent intent = getIntent();
        cards_amount = intent.getIntExtra("amount", 0);
        actual = intent.getIntExtra("actual", 0);
        cards_table = (Account[]) getIntent().getExtras().getSerializable("cards_table");


        //Intent intent = getIntent();
        final Account acc = (Account) getIntent().getExtras().getSerializable("Card");

        t1.setText("NR konta: " + acc.getNumber() + ", środki: " + acc.getBalance() + " EURO" + "\nWłaściciel: " + acc.getOwner());



        final Button b1 = (Button) findViewById(R.id.button14);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), NumericPanel.class);
                i.putExtra("Card2", acc);
                i.putExtra("operation", 1);
                i.putExtra("amount", cards_amount);
                i.putExtra("cards_table", cards_table);
                startActivity(i);
            }
        });

        final Button b2 = (Button) findViewById(R.id.button15);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                Intent i = new Intent(getApplicationContext(), NumericPanel.class);
                i.putExtra("Card2", acc);
                i.putExtra("operation", 2);
                i.putExtra("amount", cards_amount);
                i.putExtra("cards_table", cards_table);
                startActivity(i);
            }
        });
    }

    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };






    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        String new_card = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            Intent i = new Intent(getApplicationContext(), NewCard.class);
            i.putExtra("new_card_number", new_card);
            i.putExtra("amount", cards_amount);
            i.putExtra("cards_table", cards_table);
            startActivity(i);
        }


    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

}
