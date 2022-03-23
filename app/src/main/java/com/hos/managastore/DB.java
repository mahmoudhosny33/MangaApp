package com.hos.managastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static String dataName = "Mydatabase";
    SQLiteDatabase database;

    public DB(Context context) {
        super(context, dataName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer(CusID integer primary key autoincrement,CusName text not null,Email text not null," +
                "password text not null,gender text not null,birthdate text ,jop text )");
        db.execSQL("create table category(catid integer primary key  autoincrement ,catname text not null ,catImage blob)");
        db.execSQL("create table product(prodid integer primary key autoincrement,prodname text not null,prodImage blob," +
                "price integer not null , quantity integer not null , catid integer not null ," +
                "foreign key (catid) references category(catid))");
        db.execSQL("create table orders(orderID integer primary key,address text,CusID integer,FOREIGN KEY(CusID) REFERENCES customer (CusID))");

        db.execSQL("create table orderDetails(ID integer primary key autoincrement,prodid integer,quantity text not null," +
                "totalPrice text not null,orderID integer," +
                "FOREIGN KEY(prodid) REFERENCES productes(prodid),FOREIGN KEY(orderID) REFERENCES orders(orderID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists orders");
        db.execSQL("drop table if exists orderDetails");
        onCreate(db);
    }

    public void insertCustomer(String name, String mail, String password, String birthday, String gender, String jop) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CusName", name);
        values.put("Email", mail);
        values.put("password", password);
        values.put("gender", gender);
        values.put("birthdate", birthday);
        values.put("jop", jop);
        database.insert("customer", null, values);
        database.close();
    }

    public Cursor checklogin(String username, String password) {
        database = getReadableDatabase();

        Cursor cursor = database.rawQuery("Select * from customer where CusName like ?", new String[]{"%" + username + "%"});


        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getString(1).equals(username) && cursor.getString(3).equals(password)) {
                    return cursor;
                }
                cursor.moveToNext();
            }
        }
        database.close();
        return null;
    }

    public boolean userExists(String username) {

        database = getReadableDatabase();
        String[] arg = {username};
        Cursor c = database.rawQuery("SELECT CusName FROM customer WHERE CusName like ?", arg);

        if (c.getCount() != 0) {
            c.moveToFirst();
            return true;
        }
        database.close();
        return false;

    }

    public Cursor RecoveryPassword(String E) {
        database = getReadableDatabase();
        String[] arg = {E};
        Cursor c = database.rawQuery("select password from customer where Email like ?", arg);
        if (c.getCount() != 0) {
            c.moveToFirst();
            return c;
        }
        database.close();
        return null;
    }

    public void insertCategory(String name,byte[]img){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("catname",name);
        row.put("catImage",img);
        database.insert("category",null,row);
        database.close();
    }
    public Cursor fetchAllCategory() {
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from category",null);
        cursor.moveToFirst();
        if(cursor.getCount()!=0)
        {
            return cursor;
        }
        database.close();
        return null;
    }
    public String getCatID(String Catname){
        database=getReadableDatabase();
        String[]arr={Catname};
        Cursor cursor=database.rawQuery("select * from category where catname like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        database.close();
        return null;
    }
    public void insertProduct(String name,int price,int qua,byte[]img,int id){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("prodname",name);
        row.put("price",price);
        row.put("quantity",qua);
        row.put("prodImage",img);
        row.put("catid",id);
        database.insert("product",null,row);
        database.close();
    }
    public Cursor fetchProducts(String CatId){
        database=getReadableDatabase();
        String[]arr={CatId};
        Cursor cursor=database.rawQuery("select * from product where catid = ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }

    public Cursor fetchOrderDetailsById(String Id){
        database=getReadableDatabase();
        String[]arr={Id};
        Cursor cursor=database.rawQuery("select * from orderDetails where ID like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }
    public void updateQuantiyty(int qua,String id){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("quantity",qua);
        database.update("product",row,"prodid like ?",new String[]{ id });
        database.close();
    }
    public Cursor fetchProductByID(String Id){
        database=getReadableDatabase();
        String[]arr={Id};
        Cursor cursor=database.rawQuery("select * from product where prodid = ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }

    public void insertOrder(String address,int id){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("address",address);
        row.put("CusID",id);
        database.insert("orders",null,row);
        database.close();
    }

    public Cursor getOrderId(String address, String cusID){
        database=getReadableDatabase();
        String[]arr={address,cusID};
        Cursor cursor=database.rawQuery("select * from orders where address like ? and CusID like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }
    public void insertOrderDetils(int prodID,String quantity,String totalPrice,int orderId){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("prodid",prodID);
        row.put("quantity",quantity);
        row.put("totalPrice",totalPrice);
        row.put("orderID",orderId);
        database.insert("orderDetails",null,row);
        database.close();
    }
    public void updateQuantity(String prodID,int quantity){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("quantity",quantity);
        database.update("product",row,"prodid like ?",new String[]{ prodID });
        database.close();
    }

    public Cursor getProductByName(String name){
        database=getReadableDatabase();
        String[]arr={name};
        Cursor cursor=database.rawQuery("select * from product where prodname like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }
    public Cursor fetchOrders(String CustomerId){
        database=getReadableDatabase();
        String[]arr={CustomerId};
        Cursor cursor=database.rawQuery("select * from orders where cusID like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }
    public Cursor fetchOrdersDetails(String orderId){
        database=getReadableDatabase();
        String[]arr={orderId};
        Cursor cursor=database.rawQuery("select * from orderDetails where orderId like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }
    public Cursor fetchOrderDetailsByID(String Id){
        database=getReadableDatabase();
        String[]arr={Id};
        Cursor cursor=database.rawQuery("select * from orderDetails where ID like ?",arr);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return cursor;
        }
        database.close();
        return null;
    }
    public void delOrderDeteiles(String orderId){
        database=getWritableDatabase();
        database.delete("orderDetails","ID='"+orderId+"'",null);
        database.close();
    }
    public void delOrderByCustomerId(String cusID){
        database=getWritableDatabase();
        database.delete("orders","cusID='"+cusID+"'",null);
        database.close();
    }
    public void delOrder(String orderId){
        database=getWritableDatabase();
        database.delete("orders","orderID='"+orderId+"'",null);
        database.close();
    }
    public void updateOrder(String OrderID,int price,int quantity){
        database=getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("quantity",quantity);
        row.put("totalPrice",price);
        database.update("orderDetails",row,"orderId like ?",new String[]{ OrderID });
        database.close();
    }
}
