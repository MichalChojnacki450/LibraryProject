package net.larntech.libraryproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_description.view.*
import kotlinx.android.synthetic.main.example_item.view.*
import kotlinx.android.synthetic.main.example_item.view.image_view
import kotlin.collections.ArrayList
import android.app.AlertDialog


class ExampleAdapter(
        val exampleList: ArrayList<ExampleItem>,
        val context: Context,
) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>(){

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var name:TextView
        var desc:TextView
        var mMenus:ImageView

        init {
            name = itemView.findViewById(R.id.text_view_1)
            desc = itemView.findViewById(R.id.text_view_2)
            mMenus = itemView.findViewById(R.id.image_view)

            mMenus.setOnClickListener{popupMenu(it)}
        }

        fun bindItems(exampleItem: ExampleItem){
            itemView.text_view_1.text = exampleItem.name
            itemView.text_view_2.text = exampleItem.desc
            itemView.image_view.setImageResource(exampleItem.imageResource)
        }
        private fun popupMenu(itemView: View){
            val position = exampleList[adapterPosition]
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
                                Toast.makeText(context,"Book information Edited",Toast.LENGTH_LONG).show()
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
                                exampleList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(context,"Deleted this book",Toast.LENGTH_LONG).show()
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.example_item,parent,false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bindItems(exampleList[position])

        holder.itemView.setOnClickListener {
            val exampleItem = exampleList.get(position)

            var gLike : Int = exampleItem.like
            var gName : String = exampleItem.name
            var gDesc : String = exampleItem.desc
            var gImageView : Int = exampleItem.imageResource

            var intent = Intent(context,DescriptionActivity::class.java)

            intent.putExtra("iLike",gLike)
            intent.putExtra("iName", gName)
            intent.putExtra("iDesc",gDesc)
            intent.putExtra("iImageView",gImageView)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int{
        return exampleList.size
    }


}

