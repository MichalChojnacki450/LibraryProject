package net.larntech.libraryproject

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.example_item.view.*

class BuyAdapter (
    val BuyList: ArrayList<BookmarkItem>,
    val context: Context,
): RecyclerView.Adapter<BuyAdapter.BuyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item,parent,false)
        return  BuyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuyAdapter.BuyViewHolder, position: Int) {
        holder.bindItems(BuyList[position])
    }

    override fun getItemCount(): Int {
        return  BuyList.size
    }
    inner class BuyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var name: TextView
        var desc: TextView
        var mMenus: ImageView

        init {
            name = itemView.findViewById(R.id.text_view_1)
            desc = itemView.findViewById(R.id.text_view_2)
            mMenus = itemView.findViewById(R.id.image_view)

            mMenus.setOnClickListener{popupMenu(it)}
        }

        fun bindItems(exampleItem: BookmarkItem){
            itemView.text_view_1.text = exampleItem.name
            itemView.text_view_2.text = exampleItem.desc
            itemView.image_view.setImageResource(exampleItem.imageResource)
        }
        private fun popupMenu(itemView: View){
            val position = BuyList[adapterPosition]
            val popupMenu = PopupMenu(context,itemView)
            popupMenu.inflate(R.menu.delete_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val  itemView = LayoutInflater.from(context).inflate(R.layout.activity_additem,null)
                        val name = itemView.findViewById<EditText>(R.id.Name)
                        val desc = itemView.findViewById<EditText>(R.id.Desc)
                        AlertDialog.Builder(context)
                            .setView(itemView)
                            .setPositiveButton("OK"){
                                    dialog,_->
                                position.name = name.text.toString()
                                position.desc = desc.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(context,"Book information Edited", Toast.LENGTH_LONG).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this book?")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                BuyList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(context,"Deleted this book", Toast.LENGTH_LONG).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else-> true
                }
            }
            popupMenu.show()
            val  popup = PopupMenu::class.java.getDeclaredField("mPoppu")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

}