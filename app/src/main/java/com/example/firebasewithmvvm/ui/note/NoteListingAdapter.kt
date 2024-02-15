package com.example.firebasewithmvvm.ui.note

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.util.addChip
import com.example.firebasewithmvvm.util.hide
import com.example.firebasewithmvvm.databinding.ItemNoteLayoutBinding
import java.text.SimpleDateFormat

class NoteListingAdapter(
    val onItemClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    private var list: MutableList<Note> = arrayListOf()

    var sdf = SimpleDateFormat("dd mm yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: MutableList<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(private val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.title.setText(item.id)
            binding.date.setText(sdf.format(item.date))
            binding.tags.apply {
                if (item.tags.isNullOrEmpty()){
                    hide()
                }
                else{
                    removeAllViews()
                    if (item.tags.size > 2){
                        item.tags.subList(0,2).forEach {  tag -> addChip(tag) }
                        addChip("+${item.tags.size - 2}")
                    }
                    else{
                        item.tags.forEach {
                            tag -> addChip(tag)
                        }
                    }
                }

            }
            binding.desc.apply {
                if (item.description.length > 120){
                    text = "${item.description.substring(0,120)}...."
                }
                else{
                    text = item.description
                }
            }
            binding.itemLayout.setOnClickListener {
                onItemClicked.invoke(bindingAdapterPosition, item)
            }
        }
    }
}