package com.domain.rentalproperty.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domain.rentalproperty.property.R
import kotlinx.android.synthetic.main.rentalproperty_details.view.*

/**
 * A fragment representing a single Property detail screen.
 */
class RentalPropertyDetailsFragment : Fragment() {
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                id = it.getString(ITEM_ID)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.rentalproperty_details, container, false)

        id?.let{
            rootView.property_detail.text = it
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ITEM_ID = "item_id"
    }
}
