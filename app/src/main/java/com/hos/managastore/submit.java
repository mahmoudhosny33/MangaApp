package com.hos.managastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class submit extends AppCompatActivity {
    Button submit;
    TextView price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        submit=(Button)findViewById(R.id.finalSubmit);
        price=(TextView)findViewById(R.id.totalSubmition);

        DB userDB = new DB(getApplicationContext());

        Intent intent=getIntent();
        String customerID=intent.getStringExtra("customerID");
        String []orderDeteilesID=intent.getStringArrayExtra("orderDeteilesID");
        Cursor cursor;
        int total=0;
        for(int i=0;i<orderDeteilesID.length;i++){
            cursor=userDB.fetchOrderDetailsByID(orderDeteilesID[i]);
            total+=cursor.getInt(3);
        }
        price.setText("Total Prise : "+total+" $");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<orderDeteilesID.length;i++){
                    userDB.delOrderDeteiles(orderDeteilesID[i]);
                }
                userDB.delOrderByCustomerId(customerID);
                Toast.makeText(getApplicationContext(),"Orders Submited Successfully, Let's make orders again",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(submit.this,ConfirmMail.class);
                startActivity(i);
            }
        });
    }
}