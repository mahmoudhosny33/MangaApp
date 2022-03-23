package com.hos.managastore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Login extends AppCompatActivity {
    EditText usernametxt;
    EditText passwordtxt;
    Button login;
    TextView forgetpass;
    DB userDB;
    CheckBox rememberme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean Login;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
   //     DB db=new DB(getApplicationContext());

//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.computer);
//        ByteArrayOutputStream byteArr=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArr);
//        byte[]img=byteArr.toByteArray();
//        db.insertCategory("computers",img);
//
//        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.book);
//        ByteArrayOutputStream byteArr1=new ByteArrayOutputStream();
//        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArr1);
//        byte[]img1=byteArr1.toByteArray();
//        db.insertCategory("books",img1);
//
//        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.vege);
//        ByteArrayOutputStream byteArr2=new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArr2);
//        byte[]img2=byteArr2.toByteArray();
//        db.insertCategory("Vegetables",img2);
//
//        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(),R.drawable.phones);
//        ByteArrayOutputStream byteArr3=new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.PNG,100,byteArr3);
//        byte[]img3=byteArr3.toByteArray();
//        db.insertCategory("phones",img3);

//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.computer2);
//        ByteArrayOutputStream byteArr=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArr);
//        byte[]img=byteArr.toByteArray();
//        db.insertProduct("mac",20000,10,img,1);
//
//        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.computer3);
//        ByteArrayOutputStream byteArr1=new ByteArrayOutputStream();
//        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArr1);
//        byte[]img1=byteArr1.toByteArray();
//        db.insertProduct("samsung",10000,15,img,1);
//
//        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.computer4);
//        ByteArrayOutputStream byteArr2=new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArr2);
//        byte[]img2=byteArr2.toByteArray();
//        db.insertProduct("dell",14000,12,img,1);
//
//        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(),R.drawable.computer5);
//        ByteArrayOutputStream byteArr3=new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.PNG,100,byteArr3);
//        byte[]img3=byteArr3.toByteArray();
//        db.insertProduct("touch",11000,20,img3,1);

//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.book1);
//        ByteArrayOutputStream byteArr=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArr);
//        byte[]img=byteArr.toByteArray();
//        db.insertProduct("assmbly",300,10,img,2);
//
//        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.book2);
//        ByteArrayOutputStream byteArr1=new ByteArrayOutputStream();
//        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArr1);
//        byte[]img1=byteArr1.toByteArray();
//        db.insertProduct("oop c++",400,15,img,2);
//
//        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.book3);
//        ByteArrayOutputStream byteArr2=new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArr2);
//        byte[]img2=byteArr2.toByteArray();
//        db.insertProduct("clean code",450,12,img,2);
//
//        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(),R.drawable.book4);
//        ByteArrayOutputStream byteArr3=new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.PNG,100,byteArr3);
//        byte[]img3=byteArr3.toByteArray();
//        db.insertProduct("java",700,20,img3,2);
//                Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.tomato);
//        ByteArrayOutputStream byteArr=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArr);
//        byte[]img=byteArr.toByteArray();
//        db.insertProduct("tomato",15,50,img,3);
//
//        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.watermelon);
//        ByteArrayOutputStream byteArr1=new ByteArrayOutputStream();
//        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArr1);
//        byte[]img1=byteArr1.toByteArray();
//        db.insertProduct("watermelon",20,20,img,3);
//
//        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.strawberry);
//        ByteArrayOutputStream byteArr2=new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArr2);
//        byte[]img2=byteArr2.toByteArray();
//        db.insertProduct("strawberry",30,40,img,3);
//
//        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(),R.drawable.mango);
//        ByteArrayOutputStream byteArr3=new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.PNG,100,byteArr3);
//        byte[]img3=byteArr3.toByteArray();
//        db.insertProduct("mango",25,40,img3,3);
//
//        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(),R.drawable.cucumber);
//        ByteArrayOutputStream byteArr4=new ByteArrayOutputStream();
//        bitmap4.compress(Bitmap.CompressFormat.PNG,100,byteArr4);
//        byte[]img4=byteArr4.toByteArray();
//        db.insertProduct("cucumber",10,40,img4,3);
//        Bitmap bitmap5= BitmapFactory.decodeResource(getResources(),R.drawable.carrot);
//        ByteArrayOutputStream byteArr5=new ByteArrayOutputStream();
//        bitmap5.compress(Bitmap.CompressFormat.PNG,100,byteArr5);
//        byte[]img5=byteArr5.toByteArray();
//        db.insertProduct("carrot",10,30,img5,3);
//        Bitmap bitmap6= BitmapFactory.decodeResource(getResources(),R.drawable.peas);
//        ByteArrayOutputStream byteArr6=new ByteArrayOutputStream();
//        bitmap6.compress(Bitmap.CompressFormat.PNG,100,byteArr6);
//        byte[]img6=byteArr6.toByteArray();
//        db.insertProduct("peas",6,20,img6,3);
//
//        Bitmap bitmap7= BitmapFactory.decodeResource(getResources(),R.drawable.broccol);
//        ByteArrayOutputStream byteArr7=new ByteArrayOutputStream();
//        bitmap7.compress(Bitmap.CompressFormat.PNG,100,byteArr7);
//        byte[]img7=byteArr7.toByteArray();
//        db.insertProduct("broccol",20,15,img7,3);

//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.iphone13);
//        ByteArrayOutputStream byteArr=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArr);
//        byte[]img=byteArr.toByteArray();
//        db.insertProduct("iphone13",22000,50,img,4);
//
//        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.sam_phone);
//        ByteArrayOutputStream byteArr1=new ByteArrayOutputStream();
//        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArr1);
//        byte[]img1=byteArr1.toByteArray();
//        db.insertProduct("samsong_phone",7000,20,img,4);
//
//        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.oppo);
//        ByteArrayOutputStream byteArr2=new ByteArrayOutputStream();
//        bitmap2.compress(Bitmap.CompressFormat.PNG,100,byteArr2);
//        byte[]img2=byteArr2.toByteArray();
//        db.insertProduct("oppo",5000,40,img,4);
//
//        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(),R.drawable.nokia);
//        ByteArrayOutputStream byteArr3=new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.PNG,100,byteArr3);
//        byte[]img3=byteArr3.toByteArray();
//        db.insertProduct("nokia",3000,40,img3,4);
//
//        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(),R.drawable.sony);
//        ByteArrayOutputStream byteArr4=new ByteArrayOutputStream();
//        bitmap4.compress(Bitmap.CompressFormat.PNG,100,byteArr4);
//        byte[]img4=byteArr4.toByteArray();
//        db.insertProduct("sony",3500,10,img4,4);
//
//        Bitmap bitmap5= BitmapFactory.decodeResource(getResources(),R.drawable.htc);
//        ByteArrayOutputStream byteArr5=new ByteArrayOutputStream();
//        bitmap5.compress(Bitmap.CompressFormat.PNG,100,byteArr5);
//        byte[]img5=byteArr5.toByteArray();
//        db.insertProduct("htc",2000,5,img5,4);
//
//        Bitmap bitmap6= BitmapFactory.decodeResource(getResources(),R.drawable.realme);
//        ByteArrayOutputStream byteArr6=new ByteArrayOutputStream();
//        bitmap6.compress(Bitmap.CompressFormat.PNG,100,byteArr6);
//        byte[]img6=byteArr6.toByteArray();
//        db.insertProduct("realme",4500,60,img6,4);
//
//        Bitmap bitmap7= BitmapFactory.decodeResource(getResources(),R.drawable.huawei);
//        ByteArrayOutputStream byteArr7=new ByteArrayOutputStream();
//        bitmap7.compress(Bitmap.CompressFormat.PNG,100,byteArr7);
//        byte[]img7=byteArr7.toByteArray();
//        db.insertProduct("huawei",4500,40,img7,4);

        userDB = new DB(getApplicationContext());
        usernametxt = (EditText) findViewById(R.id.username);
        passwordtxt= (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginbtn);
        rememberme=(CheckBox) findViewById(R.id.RememberMe);
        forgetpass=(TextView) findViewById(R.id.forgotpassword);
        sharedPreferences=getSharedPreferences("remember file",MODE_PRIVATE);
        Login= sharedPreferences.getBoolean("login",false);
        if(Login){
            Intent intent=new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern, pass;
                usern = usernametxt.getText().toString();
                pass = passwordtxt.getText().toString();
                Cursor c=userDB.checklogin(usern,pass);
                if (usern.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Fill All Fields", Toast.LENGTH_LONG).show();
                } else if (c==null) {
                    Toast.makeText(getApplicationContext(), "Incorrect login", Toast.LENGTH_LONG).show();
                    usernametxt.setText("");
                    passwordtxt.setText("");
                } else {
                    if (rememberme.isChecked()) {
                        editor=sharedPreferences.edit();
                        editor.putString("username",usern) ;
                        editor.putString("password",pass);
                        editor.putBoolean("login",true);
                        editor.apply();
                    }
                    Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Login.this, CategoryView.class);
                    i.putExtra("CusID",c.getString(0));
                    startActivity(i);
                }
            }
        });

        TextView signup=(TextView)findViewById(R.id.signup);
        String text="Sign up?";
        SpannableString ss=new SpannableString(text);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
              Intent i=new Intent(Login.this,Register.class);
               startActivity(i);
               // Toast.makeText(getApplicationContext(), " register", Toast.LENGTH_LONG).show();

            }
        };
        ss.setSpan(clickableSpan,0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup.setText(ss);
        signup.setMovementMethod(LinkMovementMethod.getInstance());

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,forgetpasssword.class);

               // i.putExtra("CusName" , usernametxt.getText().toString());
              //  i.putExtras(b);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "forget page", Toast.LENGTH_LONG).show();

            }
        });
    }
}
