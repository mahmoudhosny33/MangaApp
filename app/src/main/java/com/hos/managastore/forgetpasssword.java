package com.hos.managastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hos.managastore.DB;
import com.hos.managastore.R;

public class forgetpasssword extends AppCompatActivity {

    DB userDB;
    EditText mail;
    EditText buckuppass;
    String reset_password;
    String cusname;
    String e;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpasssword);
         userDB = new DB(getApplicationContext());
        mail=(EditText) findViewById(R.id.mail);
         buckuppass=(EditText) findViewById(R.id.backupPass);
        Button reset=(Button) findViewById(R.id.Reset);
       // Intent intent = getIntent();
        //cusname= intent.getStringExtra("CusName");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 e=mail.getText().toString();
                c=userDB.RecoveryPassword(e);
                if(e.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please write your Email",Toast.LENGTH_LONG).show();
                    buckuppass.setText("");
                }
                else {
                    if(c!=null){
                     buckuppass.setText(c.getString(0));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid Email..! ", Toast.LENGTH_LONG).show();
                        buckuppass.setText("");
                    }

                }
            }
        });
    }
}