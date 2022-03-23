package com.hos.managastore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hos.managastore.RecycleViewAdaptCategory;
import com.hos.managastore.RecyclerItemClickListener;
import com.hos.managastore.R;

import java.io.Console;
import java.util.ArrayList;

public class CategoryView extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent1 = new Intent(CategoryView.this, orders.class);
                intent1.putExtra("cusID",customerID);
                startActivity(intent1);
                return true;
        }
        return false;
    }
    RecyclerView rv;
    DB userDB;
    String customerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        rv=findViewById(R.id.rv_category);

        ArrayList<Category> c=new ArrayList<>();
        Intent intent=getIntent();
        customerID=intent.getStringExtra("CusID");
        c.add(new Category(R.drawable.computer,"computers"));
        c.add(new Category(R.drawable.book,"books"));
        c.add(new Category(R.drawable.vege,"Vegetables"));
        c.add(new Category(R.drawable.phones,"phones"));
        RecycleViewAdaptCategory adapt=new RecycleViewAdaptCategory(c);
        userDB=new DB(getApplicationContext());
        RecyclerView.LayoutManager M=new LinearLayoutManager(this);
        rv.setLayoutManager(M);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapt);
       rv.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv, new RecyclerItemClickListener.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
               String catname=c.get(position).getName();
               Toast.makeText(getApplicationContext(),catname,Toast.LENGTH_LONG).show();
               String catid=userDB.getCatID(catname);
               Intent intent = new Intent(CategoryView.this, product.class);
               intent.putExtra("catid", catid);
               intent.putExtra("cusID", customerID);
               startActivity(intent);
               // do whatever
           }

           @Override
           public void onLongItemClick(View view, int position) {
               // do whatever
           }
       }));
    }
}