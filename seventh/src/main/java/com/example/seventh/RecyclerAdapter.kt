package com.example.seventh

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.seventh.DB.Contact
import kotlin.properties.Delegates

class RecyclerAdapter(private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder>() {

    var listContacts: List<Contact> by Delegates.observable(emptyList()){
            _, oldValue, newValue ->
        notifyChanges(oldValue, newValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(listContacts[position])
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(listContacts[position].id)
        }
    }

    override fun getItemCount(): Int = listContacts.size

    class ContactViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater
            .inflate(R.layout.item_recyclerview, parent, false)) {
        private var imageView: ImageView? = null
        private var nameText: TextView? = null
        private var contactText: TextView? = null

        init {
            imageView = itemView.findViewById(R.id.contactImageView)
            nameText = itemView.findViewById(R.id.nameTextView)
            contactText = itemView.findViewById(R.id.contactTextView)
        }

        fun bind(contact: Contact) {
            if (contact.contactType) {
                imageView?.setImageResource(R.drawable.ic_baseline_contact_mail_24)
            } else {
                imageView?.setImageResource(R.drawable.ic_baseline_contact_phone_24)
            }
            contactText?.text = contact.contact
            nameText?.text = contact.name
        }
    }

    private fun notifyChanges(oldList: List<Contact>, newList: List<Contact>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }
}

interface CellClickListener {
    fun onCellClickListener(id: String)
}