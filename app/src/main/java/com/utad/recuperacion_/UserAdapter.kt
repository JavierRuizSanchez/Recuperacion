package com.utad.recuperacion_

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class UserAdapter(context: Context, userList: ArrayList<User>) :
    ArrayAdapter<User>(context, 0, userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        val currentUser = getItem(position)

        viewHolder.textViewFullName.text = currentUser?.fullName
        viewHolder.textViewlastName.text = currentUser?.lastName
        Picasso.get().load(currentUser?.photo).into(viewHolder.imageViewPhoto)

        return itemView!!
    }

    private class ViewHolder(view: View) {
        val textViewFullName: TextView = view.findViewById(R.id.textViewFullName)
        val textViewlastName: TextView = view.findViewById(R.id.textViewlastName)
        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
    }
}
