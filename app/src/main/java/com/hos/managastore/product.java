package com.hos.managastore;

import static android.widget.AdapterView.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class product extends AppCompatActivity {

    AutoCompleteTextView editText;
    private static int MICROPHONE_PERMISSION_CODE=200;
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    MediaRecorder mediaRecorder;
    GridView gridView;

    String[]names;
    Bitmap[]images;
    String[]prices;
    String[]quantities;
    String[]ProductID;
    byte[]image;
    Bitmap bitmap;
    String customerID;
    String categoryID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final DB database=new DB(getApplicationContext());
        gridView=(GridView)findViewById(R.id.gridview);
        Intent intent=getIntent();
        customerID=intent.getStringExtra("cusID");
        categoryID=intent.getStringExtra("catid");

        editText=(AutoCompleteTextView)findViewById(R.id.autoSearch);
        Button voice=(Button)findViewById(R.id.voice);
        Button scan=(Button)findViewById(R.id.scan);

        Cursor cursor=database.fetchProducts(categoryID);
        String[]products=new String[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            products[i]=cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.moveToFirst();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,products);
        editText.setAdapter(arrayAdapter);

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pro=arrayAdapter.getItem(position);
                Cursor cursor1=database.getProductByName(pro);
                String prodId=cursor1.getString(0);
                Intent intent1=new Intent(product.this,make_order.class);
                intent1.putExtra("customerID",customerID);
                intent1.putExtra("productID",prodId);
                startActivity(intent1);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(product.this);
                intentIntegrator.setPrompt("For Flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        int i=0;
        names=new String[cursor.getCount()];
        prices=new String[cursor.getCount()];
        quantities=new String[cursor.getCount()];
        ProductID=new String[cursor.getCount()];
        images=new Bitmap[cursor.getCount()];

        while (!cursor.isAfterLast()){
            names[i]=cursor.getString(1);
            prices[i]=cursor.getString(3);
            quantities[i]=cursor.getString(4);
            ProductID[i]=cursor.getString(0);
            image=cursor.getBlob(2);
            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
            images[i]=bitmap;
            cursor.moveToNext();
            i++;
        }
        Adapter adapter=new Adapter(names,prices,quantities,images,this);
        gridView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),"beforecccc",Toast.LENGTH_SHORT).show();



//        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),"cccc",Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"cccc",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(product.this,make_order.class);
                intent1.putExtra("productID",ProductID[i]);
                intent1.putExtra("customerID",customerID);
                startActivity(intent1);
            }
        });

        Toast.makeText(getApplicationContext(),"aftercccc",Toast.LENGTH_SHORT).show();

    }

    public class Adapter extends BaseAdapter {
        private String []ProductNames;
        private String []ProductPrices;
        private String []ProductQuantity;
        private Bitmap[] productImages;
        private Context context;
        private LayoutInflater layoutInflater;

        public Adapter(String[] productNames, String[] productPrices, String[] productQuantity, Bitmap[] productImages, Context context) {
            ProductNames = productNames;
            ProductPrices = productPrices;
            ProductQuantity = productQuantity;
            this.productImages = productImages;
            this.context = context;
            layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return productImages.length;
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
                convertView=layoutInflater.inflate(R.layout.prod_items,parent,false);
            }
            TextView Name=convertView.findViewById(R.id.Name);
            TextView price=convertView.findViewById(R.id.price);
            TextView quantity=convertView.findViewById(R.id.quant);
            ImageView image=convertView.findViewById(R.id.productImage);
            Button btn=convertView.findViewById(R.id.addToCard);

            Name.setText("Name : "+ProductNames[position]);
            price.setText("Prise : "+ProductPrices[position]);
            quantity.setText("Quantity : "+ProductQuantity[position]);
            image.setImageBitmap(productImages[position]);



            return convertView;
        }
    }
    public void btnRecordPressed(View v){
        Intent intent2=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent2.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi Speak Something");
        try{
            startActivityForResult(intent2,REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SPEECH_INPUT)
        {
            if(resultCode==RESULT_OK && data !=null){
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editText.setText(result.get(0));
            }

        }else {
            IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(intentResult.getContents()!=null){
                editText.setText(intentResult.getContents());
            }else{
                Toast.makeText(getApplicationContext(),"Opss .. you didn't scan anything",Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }else{
            return false;
        }
    }

    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDictionary=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(musicDictionary,"testRecordingFile" + ".mp3");
        return file.getPath();
    }
}