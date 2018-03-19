package com.domain.rentalproperty.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DecodeFormat
import com.domain.rentalproperty.app.GlideApp
import com.domain.rentalproperty.modelbean.DataItem
import com.domain.rentalproperty.property.R
import com.domain.rentalproperty.view.RentalPropertyFragment.OnRentalPropertyListListener
import kotlinx.android.synthetic.main.rentalproperty_item.view.*

class RecyclerViewAdapter(private val mListenerRentalProperty: OnRentalPropertyListListener?) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var rentalpropertyList: List<DataItem> = arrayListOf()

    fun setRentalPropertyList(list : List<DataItem?>) {
        rentalpropertyList = list.filterNotNull()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rentalproperty_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rentalpropertyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = rentalpropertyList.get(position)
        GlideApp.with(holder.mView.context)
                .load(holder.item?.photo?.image?.url)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .placeholder(R.drawable.image_placeholder)
                .centerInside()
                .into(holder.mView.photo)
        holder.mView.title.text =  holder.item?.title
        holder.mView.address_1.text = holder.item?.location?.address_1
        holder.mView.suburb.text = "${holder.item?.location?.suburb}, ${holder.item?.location?.postcode}"
        holder.mView.name.text = "${holder.item?.owner?.firstName} ${holder.item?.owner?.lastName}"
        GlideApp.with(holder.mView.context)
                .load(holder.item?.owner?.avatar?.profile?.url)
                .dontAnimate()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .placeholder(R.drawable.profileimage_placeholder)
                .into(holder.mView.profile_image)
        holder.mView.bedroom.text = holder.item?.bedrooms.toString()
        holder.mView.bathroom.text = holder.item?.bathrooms.toString()
        holder.mView.carpark.text = holder.item?.carspots.toString()

        holder.mView.setOnClickListener {
            mListenerRentalProperty?.let {
                // Callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                holder.item?.let {
                    mListenerRentalProperty.onRentalPropertySelected(it)
                }

            }
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var item: DataItem? = null

        override fun toString(): String {
            return super.toString() + " '" + item?.title + "'"
        }
    }
}
