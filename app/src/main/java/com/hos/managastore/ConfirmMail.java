package com.hos.managastore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmMail extends AppCompatActivity {

    int valide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_mail);



            TextView tx=(TextView) findViewById(R.id.econ);
            tx.setEnabled(false);
            ((Button)findViewById(R.id.button2)).setEnabled(false);
            ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    tx.setEnabled(true);
                    ((Button)findViewById(R.id.button2)).setEnabled(true);
                    Random rand = new Random();
                    valide = 100000 + rand.nextInt((  999999-100000) );
                    mailsend mail = new mailsend("mk.elazizy@gmail.com", "112001Mk",
                            "123456789mahmoud68@gmail.com",
                            "Project Reset Password",
                            "Your Verification Code is "+String.valueOf(valide));
                    mail.execute();}

            });
            ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tx.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "empty pass", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (tx.getText().toString().equals(String.valueOf(valide))) {
                            Intent i = new Intent(ConfirmMail.this, CategoryView.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "wrong pass", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    class mailsend extends AsyncTask<Void, Void, Void> {
    private final String senderEmail;
    private final String senderPass;
    private final String email;
    private final String subject;
    private final String message;
    public mailsend(String senderEmail, String senderPass, String email, String subject, String message) {
        this.senderEmail = senderEmail;
        this.senderPass = senderPass;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPass);
                    }
                });
        try {
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress(senderEmail));
            mm.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mm.setSubject(subject);
            mm.setText(message);
            Transport.send(mm);
        } catch (MessagingException e) {
            Log.d("SendMail", "doInBackground: "+e.getMessage());
        }
        return null;
    }
}






