package com.example.fourhw

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fourhw.observer.Person

class RecycleAdapter(private val listContacts: List<Person>) :
    RecyclerView.Adapter<RecycleAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(listContacts[position])
    }

    override fun getItemCount(): Int = listContacts.size

    class ContactViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater
            .inflate(R.layout.item_recycleview, parent, false)) {
        private var imageView: ImageView? = null
        private var nameText: TextView? = null
        private var contactText: TextView? = null

        init {
            imageView = itemView.findViewById(R.id.contactImageView)
            nameText = itemView.findViewById(R.id.nameTextView)
            contactText = itemView.findViewById(R.id.contactTextView)
        }

        fun bind(contact: Person){
            if (contact.typeContact){
                imageView?.setImageResource(R.drawable.ic_baseline_contact_mail_24)
            } else{
                imageView?.setImageResource(R.drawable.ic_baseline_contact_phone_24)
            }
            contactText?.text = contact.contact
            nameText?.text = contact.name
        }
    }
}