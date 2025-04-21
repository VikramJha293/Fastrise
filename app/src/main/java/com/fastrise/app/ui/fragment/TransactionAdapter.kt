package com.fastrise.app.ui.fragment

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R

class TransactionAdapter(private val transactionList: List<Item>, requireContext: Context) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]

        holder.tvTransactionType.text = transaction.TRANS_TYPE
        holder.tvParticulars.text = transaction.PARTICULARS
        holder.tvBalance.text = "Balance: ${transaction.BALANCE}"
        holder.tvDate.text = "Date: ${transaction.INV_DATE}"

        if (transaction.DEBIT > 0) {
            holder.tvAmount.text = "Debit: ${transaction.DEBIT}"
            holder.tvAmount.setTextColor(Color.RED)
//            holder.indicator.setBackgroundResource(R.drawable.bg_red)  // ✅ Use Drawable for Red
        } else {
            holder.tvAmount.text = "Credit: ${transaction.CREDIT}"
            holder.tvAmount.setTextColor(Color.GREEN)
//            holder.indicator.setBackgroundResource(R.drawable.bg_green)  // ✅ Use Drawable for Green
        }
    }

    override fun getItemCount(): Int = transactionList.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTransactionType: TextView = itemView.findViewById(R.id.tvTransactionType)
        val tvParticulars: TextView = itemView.findViewById(R.id.tvParticulars)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvBalance: TextView = itemView.findViewById(R.id.tvBalance)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
//        val indicator: View = itemView.findViewById(R.id.indicator)
    }
}
