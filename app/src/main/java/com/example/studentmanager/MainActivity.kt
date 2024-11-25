package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var students = mutableListOf(
        StudentModel(1, "Nguyễn Văn An", "SV001"),
        StudentModel(2, "Trần Thị Bảo", "SV002"),
        StudentModel(3, "Lê Hoàng Cường", "SV003"),
        StudentModel(4, "Phạm Thị Dung", "SV004"),
        StudentModel(5, "Đỗ Minh Đức", "SV005"),
        StudentModel(6, "Vũ Thị Hoa", "SV006"),
        StudentModel(7, "Hoàng Văn Hải", "SV007"),
        StudentModel(8, "Bùi Thị Hạnh", "SV008"),
        StudentModel(9, "Đinh Văn Hùng", "SV009"),
        StudentModel(10, "Nguyễn Thị Linh", "SV010"),
    )
    lateinit var launcher: ActivityResultLauncher<Intent>;
    lateinit var studentAdapter: StudentAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentAdapter = StudentAdapter(students)
        val studentListView = findViewById<ListView>(R.id.studentList)

        studentListView.adapter = studentAdapter;
        val toolbar = findViewById<Toolbar>(R.id.optionMenu)
        setSupportActionBar(toolbar)

        registerForContextMenu(studentListView)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), {
            if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra("name")
                val mssv = it.data?.getStringExtra("mssv")
                val id = it.data?.getIntExtra("id", -1)
                if (name == null || mssv == null) {
                    Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                if (id != -1) {
                    students[id!! - 1] = StudentModel(id, name!!, mssv!!)
                } else {
                    students.add(StudentModel(students.size + 1, name!!, mssv!!))
                }
                studentAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddStudent::class.java)
                launcher.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, AddStudent::class.java)
                intent.putExtra("id", students[position].id)
                intent.putExtra("name", students[position].name)
                intent.putExtra("mssv", students[position].mssv)
                launcher.launch(intent)
            }
            R.id.action_delete -> {
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}