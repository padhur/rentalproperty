package com.domain.rentalproperty.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domain.rentalproperty.modelbean.DataItem
import com.domain.rentalproperty.property.R
import com.domain.rentalproperty.view.adapter.RecyclerViewAdapter
import com.domain.rentalproperty.viewmodel.MappedPropertyList
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModel
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_rentalproperty.view.*
import javax.inject.Inject

/**
 * A fragment representing a rental property list of Items.
 */
class RentalPropertyFragment : Fragment() {
    private var listenerRentalProperty: OnRentalPropertyListListener? = null
    private lateinit var adapter: RecyclerViewAdapter

    @Inject
    protected lateinit var rentalpropertyViewModelFactory: RentalPropertyViewModelFactory

    private lateinit var rentalpropertyViewModel: RentalPropertyViewModel

    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_rentalproperty, container, false)

        val context = rootView.getContext()
        rootView.property_list.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(listenerRentalProperty)
        rootView.property_list.adapter = adapter
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rentalpropertyViewModel = ViewModelProviders.of(activity!!, rentalpropertyViewModelFactory).get(RentalPropertyViewModel::class.java)

        setupView()
    }

    private fun showMessage(message: String) {
        rootView.property_list.visibility = View.GONE
        rootView.message.text = message
        rootView.message.visibility = View.VISIBLE
    }

    private fun showRentalPropertyList(it: List<DataItem?>) {
        adapter.setRentalPropertyList(it)
        rootView.property_list.visibility = View.VISIBLE
        rootView.message.visibility = View.GONE
        rootView.is_premium_button.isEnabled = true
    }

    private fun setupView() {
        rootView.is_premium_button.isChecked = rentalpropertyViewModel.isPremium

        rentalpropertyViewModel.propertyListLiveData.observe(this, Observer<MappedPropertyList> {
            if (rentalpropertyViewModel.isPremium) {
                it?.premiumPropertyList?.data?.let {
                    showRentalPropertyList(it)
                }
            } else {
                it?.allPropertyList?.data?.let {
                    showRentalPropertyList(it)
                }
            }
        })

        with(rootView.is_premium_button) {
            isEnabled = rentalpropertyViewModel.propertyListLiveData.value != null
            setOnCheckedChangeListener { button, isPremium ->
                run {
                    rentalpropertyViewModel.isPremium = isPremium
                    rentalpropertyViewModel.propertyListLiveData.value?.let {
                        if (isPremium) {
                            it.premiumPropertyList?.data?.let {
                                adapter.setRentalPropertyList(it)
                            }
                        } else {
                            it.allPropertyList?.data?.let {
                                adapter.setRentalPropertyList(it)
                            }
                        }
                    }
                }
            }
        }

        rentalpropertyViewModel.loadingStatus.observe(this, Observer<Boolean> {
            isLoading ->
                if(isLoading == true)
                    rootView.progressBar.visibility = View.VISIBLE
                else
                    rootView.progressBar.visibility = View.GONE
        })

        rentalpropertyViewModel.errorStatus.observe(this, Observer<Throwable> {
            showMessage(getString(R.string.could_not_load_rentalproperty_data))
        })
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is OnRentalPropertyListListener) {
            listenerRentalProperty = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnRentalPropertyListListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerRentalProperty = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity.
     */
    interface OnRentalPropertyListListener {
        fun onRentalPropertySelected(item: DataItem)
    }

    companion object {

        fun newInstance(): RentalPropertyFragment {
            return RentalPropertyFragment()
        }
    }
}
