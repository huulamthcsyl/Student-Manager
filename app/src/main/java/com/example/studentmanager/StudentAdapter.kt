package com.example.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class StudentAdapter(val students: List<StudentModel>) : BaseAdapter() {
    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.student_item, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            itemView = convertView
            viewHolder = itemView.tag as ViewHolder
        }

        val student = students[position]
        viewHolder.textStudentName.text = student.name
        viewHolder.textStudentId.text = student.mssv

        return itemView;
    }

    class ViewHolder(itemView: View) {
        val textStudentName: TextView
        val textStudentId: TextView
        init {
            textStudentName = itemView.findViewById(R.id.textStudentName)
            textStudentId = itemView.findViewById(R.id.textStudentId)
        }
    }
}