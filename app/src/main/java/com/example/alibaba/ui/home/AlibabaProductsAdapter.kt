package com.example.alibaba.ui.home

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alibaba.R
import com.example.alibaba.ui.room.Alibaba

class AlibabaProductsAdapter(
    private val listLine: List<Alibaba>
) :
    RecyclerView.Adapter<AlibabaProductsAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listar_productos,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listLine.size
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.nameTextView.text = listLine[position].nombre
        holder.descriptionTextView.text = listLine[position].descripcion
        holder.stockTextView.text = listLine[position].stock
        val bitmap = BitmapFactory.decodeByteArray(listLine[position].imagen, 0, listLine[position].imagen!!.size)
        holder.itemImageProduct.setImageBitmap(bitmap)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        var stockTextView: TextView = itemView.findViewById(R.id.stockTextView)
        var descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        var itemImageProduct:ImageView = itemView.findViewById(R.id.itemImageProduct)

    }

}