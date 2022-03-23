package com.hos.managastore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText repassword;
    EditText birthday;
    EditText job;
    Spinner Gender;
    Button register;
    DB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        birthday = (EditText) findViewById(R.id.birthday);
        job = (EditText) findViewById(R.id.job);
        Gender = (Spinner) findViewById(R.id.spiner);
        register = (Button) findViewById(R.id.registerbtn);
        userDB = new DB(getApplicationContext());

        final DatePickerDialog.OnDateSetListener Date;

        final Calendar calendar = Calendar.getInstance();
        Date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                String format = "dd/MM/yyyy";
                SimpleDateFormat SDF = new SimpleDateFormat(format, Locale.US);
                birthday.setText(SDF.format(calendar.getTime()));
            }
        };
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Register.this, Date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mail = email.getText().toString();
                String birth = birthday.getText().toString();
                String jobtitle = job.getText().toString();
                String gender = Gender.getSelectedItem().toString();

                if(username.equals("")||pass.equals("")||mail.equals("")||birth.equals("")
                        ||repass.equals("")){
                    Toast.makeText(getApplicationContext(),"Should fill all fields",Toast.LENGTH_LONG).show();
                }
                else if (!isEmailValid(mail)) {
                    Toast.makeText(getApplicationContext(), "Wrong E-Mail Format", Toast.LENGTH_LONG).show();
                    email.setText("");
                }

                else if(pass.length() < 6||repass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password Is Too Short", Toast.LENGTH_LONG).show();
                    password.setText("");
                }

                else if(!pass.equals(repass)) {
                    Toast.makeText(getApplicationContext(), "Password Doesn't Match", Toast.LENGTH_LONG).show();
                    password.setText("");
                    repassword.setText("");
                }
                else if(userDB.userExists(username))
                {
                    Toast.makeText(getApplicationContext(), "Usrname isn't available", Toast.LENGTH_LONG).show();
                    name.setText("");
                }
                else{
                    userDB.insertCustomer(username,mail,pass,birth,gender,jobtitle);
                    Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Register.this,Login.class);
                    //i.putExtra("custid",userDB.get)
                    startActivity(i);
                }

            }

        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}