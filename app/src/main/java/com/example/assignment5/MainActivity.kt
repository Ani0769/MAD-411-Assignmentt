package com.example.assignment5

import Expense
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment5.ExpenseListAdapter
import com.example.assignment5.R
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var expenseNameInput: EditText
    private lateinit var expenseAmountInput: EditText
    private lateinit var addExpenseButton: Button
    private lateinit var datePickerButton: Button
    private lateinit var selectedDateText: TextView
    private lateinit var recyclerView: RecyclerView

    private val expenseList = mutableListOf<Expense>()
    private lateinit var expenseListAdapter: ExpenseListAdapter
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseNameInput = findViewById(R.id.expenseNameInput)
        expenseAmountInput = findViewById(R.id.expenseAmountInput)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        datePickerButton = findViewById(R.id.datePickerButton)
        selectedDateText = findViewById(R.id.selectedDateText)
        recyclerView = findViewById(R.id.recyclerView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        expenseListAdapter = ExpenseListAdapter(expenseList) { expense ->
            // Handle delete expense
            expenseList.remove(expense)
            expenseListAdapter.notifyDataSetChanged()
        }
        recyclerView.adapter = expenseListAdapter

        // Set up DatePickerDialog
        datePickerButton.setOnClickListener {
            openDatePicker()
        }

        // Add Expense button functionality
        addExpenseButton.setOnClickListener {
            addExpense()
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Open DatePickerDialog to pick a date
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDateFormatted = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                selectedDate = selectedDateFormatted
                selectedDateText.text = selectedDateFormatted
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun addExpense() {
        val expenseName = expenseNameInput.text.toString().trim()
        val expenseAmount = expenseAmountInput.text.toString().trim()

        // Input validation
        if (expenseName.isEmpty() || expenseAmount.isEmpty()) {
            Toast.makeText(this, "Please enter both expense name and amount", Toast.LENGTH_SHORT).show()
            return
        }

        // Ensure the amount is a valid number
        val amount = expenseAmount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        // Ensure the user selected a date
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
            return
        }

        // Create an Expense object and add it to the list
        val expense = Expense(expenseName, amount, selectedDate)
        expenseList.add(expense)

        // Notify adapter of data change
        expenseListAdapter.notifyDataSetChanged()

        // Clear input fields
        expenseNameInput.text?.clear()
        expenseAmountInput.text?.clear()
        selectedDateText.text = "No Date Selected"
    }
}