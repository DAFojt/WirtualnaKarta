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
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import java.io.Serializable;


public class MainActivity extends Activity {
    private Account cards_table[] = new Account[124];
    private String actual_card;

    int cards_amount = 0;
    int actual_int = 0;
    TextView textbig;
    static Account CL_actual_card;
    int operation = 0;
    int actual_id;
    int getCards_amount()
    {
        return cards_amount;
    }


    public void new_card_action()
    {
        if((Account) getIntent().getExtras().getSerializable("Card")!=null) {
            CL_actual_card = (Account) getIntent().getExtras().getSerializable("Card");

            actual_card = CL_actual_card.getNumber();

            if (acc_is_exist() == 0) {
                cards_table[cards_amount] = CL_actual_card;
                cards_amount++;
                System.out.println("Ilość kart2: " + cards_amount);
                Intent i = new Intent(getApplicationContext(), Transactions.class);
                i.putExtra("Card", CL_actual_card);
                i.putExtra("amount", cards_amount);
                i.putExtra("cards_table", cards_table);
                i.putExtra("actual", acc_table_num_id_by_card_number(actual_card));
                startActivity(i);
            } else {
                                Intent i = new Intent(getApplicationContext(), Transactions.class);
                i.putExtra("Card", CL_actual_card);
                i.putExtra("amount", cards_amount);
                i.putExtra("cards_table", cards_table);
                i.putExtra("actual", acc_table_num_id_by_card_number(actual_card));
                startActivity(i);
            }
        }
    }



    void set_actual_card(String actual)
    {
        actual_card = actual;
    }


    // list of NFC technologies detected:
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        cards_amount = intent.getIntExtra("amount", 0);
        if(cards_amount!=0)
        cards_table = (Account[]) getIntent().getExtras().getSerializable("cards_table");


        System.out.println("Ilość kart: " + cards_amount);


        actual_card = intent.getStringExtra("Card");

        if(acc_is_exist() == 1) {
            Intent i = new Intent(getApplicationContext(), Transactions.class);
            i.putExtra("Card", CL_actual_card);
            i.putExtra("amount", cards_amount);
            i.putExtra("cards_table", cards_table);
            i.putExtra("actual", acc_table_num_id_by_card_number(actual_card));
            startActivity(i);
        }
        int operation = intent.getIntExtra("operation", 0); // here 0 is the default value
            //NAPRAW LOGIKE!
            if((operation == 1)&&(acc_is_exist() == 0))
                new_card_action();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

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


        //!!!ZAWSZE MA PRZEJSC DO NEW CARD!!!//

        //if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            //((TextView)findViewById(R.id.t1)).setText(
            //        "NR KARTY: " +
            //                ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)) + "DEV : Ilosc kart: " + cards_amount);
        //}

        actual_card = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
        if (acc_is_exist() == 0)
        {
            Intent i = new Intent(getApplicationContext(), NewCard.class);
            i.putExtra("Card", CL_actual_card);
            i.putExtra("amount", cards_amount);
            i.putExtra("cards_table", cards_table);
            i.putExtra("actual", acc_table_num_id_by_card_number(actual_card));
            startActivity(i);
        }




        //int acc_act_id = acc_table_num_id_by_card_number(actual_card);
        //((TextView)findViewById(R.id.t1)).setText("NR KARTY: "+ cards_table[acc_act_id].get_number());
        //String acc_balance = ""+cards_table[acc_act_id].get_balance();
        //((TextView)findViewById(R.id.t2)).setText("Stan konta: "+acc_balance + " EURO");
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

    int acc_is_exist() {
        if (cards_amount != 0)
            for (int i = 0; i < cards_amount; i++) {
                if(cards_table[i].getNumber().equals(actual_card))
                    return 1;
            }
        return 0;
    }

    int acc_table_num_id_by_card_number(String card_number)
    {
        if(cards_amount!=0)
        for(int i = 0;i<cards_amount;i++)
        {
            if(card_number.equals(cards_table[i].getNumber()))
            {
                return i;
            }
        }
        return -1;
    }

}

