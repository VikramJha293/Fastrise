package com.fastrise.app.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R

class SaleListAdapter(private val invoiceList: List<Item>, context: Context) :
    RecyclerView.Adapter<SaleListAdapter.InvoiceViewHolder>() {

    class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val invNo: TextView = itemView.findViewById(R.id.inv_no)
        val invDate: TextView = itemView.findViewById(R.id.inv_date)
        val shopName: TextView = itemView.findViewById(R.id.shop_name)
        val name: TextView = itemView.findViewById(R.id.name)
        val mobileNo: TextView = itemView.findViewById(R.id.mobile_no)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val qty: TextView = itemView.findViewById(R.id.qty)
        val price: TextView = itemView.findViewById(R.id.price)
        val totalPrice: TextView = itemView.findViewById(R.id.total_price)
        val payMode: TextView = itemView.findViewById(R.id.pay_mode)
        val seraialNo: TextView = itemView.findViewById(R.id.seraialNo)
        val description: TextView = itemView.findViewById(R.id.description)
        val disdyousell: TextView = itemView.findViewById(R.id.disdyousell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_sale_list_row, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val invoice = invoiceList[position]
        holder.invNo.text = "Invoice No: ${invoice.INV_NO}"
        holder.invDate.text = "Date: ${invoice.INV_DATE}"
        holder.shopName.text = "Shop Name: ${invoice.SHOP_NAME} "
        holder.name.text = "Customer: ${invoice.NAME}"
        holder.mobileNo.text = "Mobile: ${invoice.MOBILE_NO}"
        holder.itemName.text = "Item: ${invoice.ITEM_NAME}"
        holder.qty.text = "Qty: ${invoice.QTY}"
        holder.price.text = "Price: ₹${invoice.PRICE}"
        holder.totalPrice.text = "Total: ₹${invoice.TOTAL_PRICE}"
        holder.payMode.text = "Payment: ${invoice.PAY_MODE}"
        holder.seraialNo.text = "Serial No: ${invoice.SerialNo}"
        holder.description.text = "Product Description: ${invoice.Discription}"
        holder.disdyousell.text = "DidYouSell: ${invoice.didYouSell}"
    }

    override fun getItemCount() = invoiceList.size
}