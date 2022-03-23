package com.hos.managastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class updateOrder extends AppCompatActivity {
    Button delete;
    Button update;
    Button inc;
    Button dec;
    TextView Name;
    TextView Total;
    TextView quantity;
    EditText updateQuantity;
    int qua;
    int total;
    int prodQua;
    int userQua;
    int prodPrice;
    String nameProd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        DB userDB=new DB(getApplicationContext());
        Intent intent=getIntent();
        String customerID=intent.getStringExtra("customerID");
        String orderID=intent.getStringExtra("orderID");
        String orderDeteilesID=intent.getStringExtra("orderDeteilesID");
        String idProd=intent.getStringExtra("idProd");

        delete=(Button)findViewById(R.id.deleteOrder);
        update=(Button)findViewById(R.id.update);
        inc=(Button)findViewById(R.id.increaseOrder);
        dec=(Button)findViewById(R.id.decreaseOrder);
        updateQuantity=(EditText) findViewById(R.id.quanOrder);
        Name=(TextView)findViewById(R.id.nameOrder);
        Total=(TextView)findViewById(R.id.totalOrder);
        quantity=(TextView)findViewById(R.id.quantityOrder);

        Cursor cursor=userDB.fetchProductByID(idProd);
        Cursor deteil=userDB.fetchOrdersDetails(orderID);

        nameProd=cursor.getString(1);
        prodQua=cursor.getInt(4);
        userQua=deteil.getInt(2);
        prodPrice=cursor.getInt(3);

        Name.setText("Product Name : "+nameProd);
        prodQua+=userQua-1;

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDB.delOrderDeteiles(orderDeteilesID);
                userDB.delOrder(orderID);
                Intent intent1=new Intent(updateOrder.this,orders.class);
                intent1.putExtra("cusID",customerID);
                Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });
        qua=0;
        updateQuantity.setText(String.valueOf(qua));

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qua>0){
                    qua--;
                    updateQuantity.setText(String.valueOf(qua));
                    total=qua*prodPrice;
                    Total.setText("Total Prise: "+String.valueOf(total)+"$");
                }
            }
        });
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qua<prodQua){
                    qua++;
                    updateQuantity.setText(String.valueOf(qua));
                    total=qua*prodPrice;
                    Total.setText("Total Prise: "+String.valueOf(total)+"$");
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDB.updateQuantity(idProd,prodQua);
                userDB.updateOrder(orderID,total,qua);
                prodQua-=qua;
                userDB.updateQuantity(idProd,prodQua);
                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(updateOrder.this,orders.class);
                intent1.putExtra("cusID",customerID);
                startActivity(intent1);
            }
        });
    }
    }
