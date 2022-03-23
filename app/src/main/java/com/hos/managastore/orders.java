package com.hos.managastore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class orders extends AppCompatActivity {
    GridView gridView;
    String[]names;
    String[]prices;
    String[]Quan;
    Bitmap []images;
    String[]idProd;
    byte[]img;
    Bitmap bitmap;
    Button submit;
    String customerID;
    String []orderDeteilesID;
    String []orderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        DB userDB=new DB(getApplicationContext());
        Intent intent=getIntent();
        gridView=(GridView)findViewById(R.id.gridviewOrders);
        submit=(Button)findViewById(R.id.submitOrder);
        if(intent.getExtras()!=null){
            customerID=intent.getStringExtra("cusID");
            Cursor cursor=userDB.fetchOrders(customerID);
            if(cursor!=null){
                names=new String[cursor.getCount()];
                prices=new String[cursor.getCount()];
                Quan=new String[cursor.getCount()];
                images=new Bitmap[cursor.getCount()];
                idProd=new String[cursor.getCount()];
                orderDeteilesID=new String[cursor.getCount()];
                orderID=new String[cursor.getCount()];
                Cursor deteils;
                Cursor product;
                int i=0;
                while (!cursor.isAfterLast()){
                    orderID[i]=cursor.getString(0);
                    deteils=userDB.fetchOrdersDetails(cursor.getString(0));
                    orderDeteilesID[i]=deteils.getString(0);
                    product=userDB.fetchProductByID(deteils.getString(1));
                    names[i]=product.getString(1);
                    img=product.getBlob(2);
                    bitmap= BitmapFactory.decodeByteArray(img,0,img.length);
                    images[i]=bitmap;
                    prices[i]=deteils.getString(3);
                    Quan[i]=deteils.getString(2);
                    idProd[i]=deteils.getString(1);
                    i++;
                    cursor.moveToNext();
                }
                ordersAdapter ordersAdapter=new ordersAdapter(names,images,prices,Quan,this);

                int total=0;
                for(int j=0;j<i;j++){
                    total+=Integer.valueOf(prices[j]);
                }
                gridView.setAdapter(ordersAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent1=new Intent(orders.this,updateOrder.class);
                        intent1.putExtra("orderDeteilesID",orderDeteilesID[position]);
                        intent1.putExtra("orderID",orderID[position]);
                        intent1.putExtra("customerID",customerID);
                        intent1.putExtra("idProd",idProd[position]);
                        startActivity(intent1);
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2=new Intent(orders.this,submit.class);
                        intent2.putExtra("customerID",customerID);
                        intent2.putExtra("orderDeteilesID",orderDeteilesID);
                        startActivity(intent2);
                    }
                });
            }

        }
    }

    public class ordersAdapter extends BaseAdapter {
        private String []imagesName;
        private Bitmap[] imagesPhoto;
        private String[]imagePrice;
        private String[]imageQuantity;
        private Context context;
        private LayoutInflater layoutInflater;

        public ordersAdapter(String[] imagesName, Bitmap[] imagesPhoto, String[] imagePrice, String[] imageQuantity, Context context) {
            this.imagesName = imagesName;
            this.imagesPhoto = imagesPhoto;
            this.imagePrice = imagePrice;
            this.imageQuantity = imageQuantity;
            this.context = context;
            layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesPhoto.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.orders_item,parent,false);
            }
            TextView tvName=convertView.findViewById(R.id.tvName1);
            TextView tvPrice=convertView.findViewById(R.id.tvPrice);
            TextView tvQuantity=convertView.findViewById(R.id.tvQuantity);
            ImageView imageView=convertView.findViewById(R.id.imageViewOrder);

            tvName.setText(imagesName[position]);
            imageView.setImageBitmap(imagesPhoto[position]);
            tvPrice.setText("Total Price: " + imagePrice[position]);
            tvQuantity.setText("Quantity : " + imageQuantity[position]);


            return convertView;
        }
    }
}