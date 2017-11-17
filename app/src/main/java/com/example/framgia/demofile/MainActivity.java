package com.example.framgia.demofile;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText edtContent;
    Button btnWrite, btnRead;
    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtContent = (EditText) findViewById(R.id.edtContent);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnRead.setOnClickListener(readFile);
        btnWrite.setOnClickListener(writeFile);
    }

    View.OnClickListener writeFile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String str = edtContent.getText().toString();
            try {
                FileOutputStream fOut = openFileOutput("myFile.txt", MODE_PRIVATE);

                OutputStreamWriter osw = new
                        OutputStreamWriter(fOut);

                //---Bắt đầu quá trình ghi file---
                osw.write(str);
                osw.flush();
                osw.close();

                //---Hiển thị ra là đã lưu thành công---
                Toast.makeText(getBaseContext(),
                        "File saved successfully!",
                        Toast.LENGTH_SHORT).show();

                //---Xóa edit text---
                edtContent.setText("");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    };

    View.OnClickListener readFile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                FileInputStream fIn = openFileInput("myFile.txt");
                InputStreamReader isr = new InputStreamReader(fIn);

                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String s = "";

                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    //---Chuyển từ kiểu char sang string---
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    s += readString;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }
                //---set text vừa đọc ra lên ô nhập liệu
                // read---
                edtContent.setText(s);
                Toast.makeText(getBaseContext(), "File loaded successfully!", Toast.LENGTH_SHORT).show();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    };

}
