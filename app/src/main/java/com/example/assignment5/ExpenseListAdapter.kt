package com.example.assignment5

import Expense
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseListAdapter(
    private val expenseList: MutableList<Expense>,
    private val onItemClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.expenseName.text = expense.name
        holder.expenseAmount.text = "$${expense.amount}"
        holder.expenseDate.text = expense.date

        holder.deleteButton.setOnClickListener {
            expenseList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = expenseList.size

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseName: TextView = itemView.findViewById(R.id.expenseNameTextView)
        val expenseAmount: TextView = itemView.findViewById(R.id.expenseAmountTextView)
        val expenseDate: TextView = itemView.findViewById(R.id.expenseDateTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
}