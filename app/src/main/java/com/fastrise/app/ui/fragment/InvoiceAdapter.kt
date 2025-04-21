package com.fastrise.app.ui.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R

class InvoiceAdapter(
    private val invoiceList: List<SaleResponseModelItem>,
    requireContext: Context
) :
    RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_invoice, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val invoice = invoiceList[position]
        holder.tvInvoiceNo.text = "Invoice No: ${invoice.INV_NO}"
        holder.tvInvoiceDate.text = "Date: ${invoice.INV_DATE}"
        holder.tvSupplier.text = "Supplier: ${invoice.Supplier}"
        holder.tvCustomer.text = "Customer: ${invoice.NAME}"
        holder.modelno.text = "Model No: ${invoice.MODEL_NO}"
        holder.tvItem.text = "Item: ${invoice.ITEM_NAME}"
        holder.tvQuantity.text = "Quantity: ${invoice.QTY}"
        holder.tvPrice.text = "Price: ${invoice.PRICE}"
        holder.tvTotalPrice.text = "Total Price: ₹${invoice.TOTAL_PRICE}"
        holder.payby.text = "Pay Mode: ${invoice.PAY_MODE}"
        holder.SerialNo.text = "Serail No: ${invoice.SerialNo}"
        holder.description.text = "Product Description: ${invoice.Discription}"
        holder.didYouSell.text = "Did You Sell: ${invoice.didYouSell}"
    }

    override fun getItemCount(): Int = invoiceList.size

    class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInvoiceNo: TextView = itemView.findViewById(R.id.tvInvoiceNo)
        val tvInvoiceDate: TextView = itemView.findViewById(R.id.tvInvoiceDate)
        val tvSupplier: TextView = itemView.findViewById(R.id.tvSupplier)
        val tvCustomer: TextView = itemView.findViewById(R.id.tvCustomer)
        val modelno: TextView = itemView.findViewById(R.id.modelno)
        val tvItem: TextView = itemView.findViewById(R.id.tvItem)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvTotalPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)
        val payby: TextView = itemView.findViewById(R.id.paymode)
        val SerialNo: TextView = itemView.findViewById(R.id.SerialNo)
        val description: TextView = itemView.findViewById(R.id.description)
        val didYouSell: TextView = itemView.findViewById(R.id.didYouSell)
    }
}