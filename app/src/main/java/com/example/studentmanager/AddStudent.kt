package com.example.studentmanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student)

        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            findViewById<EditText>(R.id.editTextName).setText(intent.getStringExtra("name"))
            findViewById<EditText>(R.id.editTextMSSV).setText(intent.getStringExtra("mssv"))
            findViewById<TextView>(R.id.txtHeader).text = "Chỉnh sửa sinh viên"
        }

        findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            val txtName = findViewById<EditText>(R.id.editTextName).text.toString()
            val txtMssv = findViewById<EditText>(R.id.editTextMSSV).text.toString()
            if(txtName.isEmpty() || txtMssv.isEmpty()) {
                setResult(RESULT_CANCELED)
            } else {
                intent.putExtra("name", txtName)
                intent.putExtra("mssv", txtMssv)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}