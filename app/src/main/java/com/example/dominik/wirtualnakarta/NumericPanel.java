package com.example.dominik.wirtualnakarta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Dominik on 21.11.2016.
 */
public class NumericPanel extends Activity {


    int actual_int = 0;
    int cards_amount;
    Account cards_table[];
    int actual_card;
    TextView textbig;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numeric_panel);

        Intent intent = getIntent();
        cards_amount = intent.getIntExtra("amount", 0);
        actual_card = intent.getIntExtra("actual", 0);
        cards_table = (Account[]) getIntent().getExtras().getSerializable("cards_table");

        textbig = (TextView) findViewById(R.id.textViewBIG);

        final Account acc = (Account) getIntent().getExtras().getSerializable("Card2");


        final Button b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 7;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 8;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b5 = (Button) findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 9;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b6 = (Button) findViewById(R.id.button6);
        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 4;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b7 = (Button) findViewById(R.id.button7);
        b7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 5;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b8 = (Button) findViewById(R.id.button8);
        b8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 6;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b9 = (Button) findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 1;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b10 = (Button) findViewById(R.id.button10);
        b10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 2;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b11 = (Button) findViewById(R.id.button11);
        b11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10 + 3;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b12 = (Button) findViewById(R.id.button12);
        b12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = actual_int * 10;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b13 = (Button) findViewById(R.id.button13);
        b13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                actual_int = 0;
                String act = "" + actual_int;
                ((TextView) findViewById(R.id.textViewBIG)).setText(act);
            }
        });

        final Button b16 = (Button) findViewById(R.id.button16);
        b16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Transactions.class);
                Intent intent = getIntent();
                int op = intent.getIntExtra("operation", 0);

                switch(op) {
                    case 1:
                        cards_table[actual_card].enrich(actual_int);
                        break;
                    case 2:
                        cards_table[actual_card].unenrich(actual_int);

                        //
                        //OPERACJE
                        //

                }
                i.putExtra("amount", cards_amount);
                i.putExtra("cards_table", cards_table);
                i.putExtra("actual", actual_card);
                startActivity(i);

            }
        });

        final Button b17 = (Button) findViewById(R.id.button17);
        b17.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Transactions.class);
                startActivity(i);

            }
        });


    }

    int get_actual_int()
    {
        return actual_int;
    }

}
