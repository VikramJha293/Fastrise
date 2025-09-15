package com.fastrise.app.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R

class SaleReportAdapter(private val items: List<CategorySaleMasterReport>) :
    RecyclerView.Adapter<SaleReportAdapter.SaleReportViewHolder>() {

    inner class SaleReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val tvSlNo: TextView = itemView.findViewById(R.id.tvSlNo)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvTarget: TextView = itemView.findViewById(R.id.tvTarget)
        val tvQty: TextView = itemView.findViewById(R.id.tvQty)
        val tvSupplierName: TextView = itemView.findViewById(R.id.tvSupplierName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sale_report, parent, false)
        return SaleReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaleReportViewHolder, position: Int) {
        val item = items[position]
        holder.tvCategory.text = item.CATEGORY
        holder.tvTarget.text = item.Target.toString()
        holder.tvQty.text = item.QTY.toString()
        holder.tvSupplierName.text = item.NAME
    }

    override fun getItemCount(): Int = items.size
}
