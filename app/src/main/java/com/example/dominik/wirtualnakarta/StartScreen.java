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
import android.widget.TextView;

/**
 * Created by Dominik on 21.11.2016.
 */
public class StartScreen extends Activity {

    private Account cards_table[] = new Account[124];
    private String actual_card;
    int cards_amount = 0;
    int actual_int = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

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
        Intent i = new Intent(getApplicationContext(), NewCard.class);
        startActivity(i);

        actual_card = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));

        if(acc_is_exist() == 0)
        {
            cards_table[cards_amount] = new Account(actual_card, 20000, "Użytkownik testowy");
            cards_amount++;


        }

        int acc_act_id = acc_table_num_id_by_card_number(actual_card);
        String acc_balance = ""+cards_table[acc_act_id].getBalance();

        Intent a = new Intent(getApplicationContext(), NewCard.class);
        startActivity(a);
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
