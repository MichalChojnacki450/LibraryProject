package net.larntech.libraryproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_items.view.*

class ItemAdapter(var itemModalList: List<itemModal>): RecyclerView.Adapter<ItemAdapter.ItemAdapterVH>() {

    fun setData(itemModalList: List<itemModal>){
        this.itemModalList = itemModalList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterVH {
        return ItemAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.row_items,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ItemAdapterVH, position: Int) {
        var itemModal = itemModalList[position]
        holder.imageView.setImageResource(itemModal.image)
        holder.name.text=itemModal.names
        holder.desc.text=itemModal.desc
    }

    override fun getItemCount(): Int {
        return itemModalList.size
    }
    class ItemAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView = itemView.imageView
        var name = itemView.tvName
        var desc = itemView.tvDesc

    }
}