package com.example.seauk.database;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper myDb;
    EditText et_name, et_surname, et_marks,et_id;
    Button btn_insert, btn_view,btn_update,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DBHelper(this);
        init();
        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void init(){
        et_marks = findViewById(R.id.et_marks);
        et_name = findViewById(R.id.et_name);
        et_surname = findViewById(R.id.et_surname);
        et_id = findViewById(R.id.et_id);
        btn_insert = findViewById(R.id.btn_insert);
        btn_view = findViewById(R.id.btn_view);
        btn_update =findViewById(R.id.btn_update);
        btn_delete =findViewById(R.id.btn_delete);
    }

    public void deleteData(){
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(et_id.getText().toString());
                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateData(){
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate  = myDb.updataData(et_id.getText().toString(),
                        et_name.getText().toString(), et_surname.getText().toString(),
                        et_marks.getText().toString());
                if(isUpdate){
                    Toast.makeText(MainActivity.this, "Data is updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data is not updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addData() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(et_name.getText().toString(),
                        et_surname.getText().toString(), et_marks.getText().toString());
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewAll() {
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    //show message
                    showMessage("Error", "nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID:" + res.getString(0)+"\n");
                    buffer.append("NAME:" + res.getString(1)+"\n");
                    buffer.append("SURNAME:" + res.getString(2)+"\n");
                    buffer.append("MARKS:" + res.getString(3)+"\n\n");

                }
                showMessage("DATA", buffer.toString());
            }
        });
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}