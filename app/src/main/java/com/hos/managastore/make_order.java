package com.hos.managastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class make_order extends AppCompatActivity {

    String customerID;
    String productID;

    ImageView imageView;
    TextView name;
    TextView price;
    TextView totalTxVi;
    EditText quantity;
    Button dec;
    Button inc;
    Button addProd;
    byte[]img;
    Bitmap bitmap;
    int qua;
    int total;
    int prodPrice;
    int totalQuantityOfProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        Intent intent=getIntent();
        customerID=intent.getStringExtra("customerID");
        productID=intent.getStringExtra("productID");

        imageView=(ImageView)findViewById(R.id.imageView1);
        name=(TextView)findViewById(R.id.Name);
        totalTxVi=(TextView)findViewById(R.id.total);
        price=(TextView)findViewById(R.id.price);
        quantity=(EditText)findViewById(R.id.quantity);
        dec=(Button)findViewById(R.id.decrease);
        inc=(Button)findViewById(R.id.increase);
        addProd=(Button)findViewById(R.id.addProd);
        final DB database=new DB(getApplicationContext());

        Cursor cursor=database.fetchProductByID(productID);
        totalQuantityOfProduct=cursor.getInt(4);

        img=cursor.getBlob(2);
        bitmap= BitmapFactory.decodeByteArray(img,0,img.length);
        imageView.setImageBitmap(bitmap);

        name.setText("Name : "+cursor.getString(1));
        price.setText("Price : "+cursor.getString(3));
        prodPrice=Integer.valueOf(cursor.getString(3));

        qua=0;
        quantity.setText(String.valueOf(qua));

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qua>0){
                    qua--;
                    quantity.setText(String.valueOf(qua));
                    total=qua*prodPrice;
                    totalTxVi.setText("Total Prise: "+String.valueOf(total)+"$");
                }
            }
        });
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qua<totalQuantityOfProduct){
                    qua++;
                    quantity.setText(String.valueOf(qua));
                    total=qua*prodPrice;
                    totalTxVi.setText("Total Prise: "+String.valueOf(total)+"$");
                }
            }
        });
        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalQuantityOfProduct-=qua;
                Intent intent1=new Intent(make_order.this,MapsActivity.class);
                intent1.putExtra("customerID",customerID);
                intent1.putExtra("nameOfProd",cursor.getString(1));
                intent1.putExtra("ProdID",productID);
                intent1.putExtra("quantity",qua);
                intent1.putExtra("Totalquantity",totalQuantityOfProduct);
                intent1.putExtra("total",total);
                startActivity(intent1);
            }
        });
    }
}