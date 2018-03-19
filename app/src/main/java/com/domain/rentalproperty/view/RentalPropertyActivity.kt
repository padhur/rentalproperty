package com.domain.rentalproperty.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.domain.rentalproperty.modelbean.DataItem
import com.domain.rentalproperty.property.R
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModel
import com.domain.rentalproperty.viewmodel.RentalPropertyViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_rentalproperty.*
import kotlinx.android.synthetic.main.rentalproperty_container.*
import javax.inject.Inject

class RentalPropertyActivity : AppCompatActivity(),
        RentalPropertyFragment.OnRentalPropertyListListener,
        HasSupportFragmentInjector
{
    /**
     * Whether or not the activity is in dual-pane mode, i.e. running on a tablet
     * device.
     */
    private var dualPane: Boolean = false
    private lateinit var rentalpropertyFragment: RentalPropertyFragment

    private val rentalPropertyFragment = "RENTAL_PROPERTY_FRAGMENT"

    @Inject
    protected lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    protected lateinit var rentalpropertyViewModelFactory: RentalPropertyViewModelFactory

    private lateinit var rentalpropertyViewModel: RentalPropertyViewModel

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rentalproperty)

        setSupportActionBar(toolbar)
        toolbar.title = title

        rentalpropertyViewModel = ViewModelProviders.of(this@RentalPropertyActivity, rentalpropertyViewModelFactory).get(RentalPropertyViewModel::class.java)

        if (property_detail_container != null) {
            //for large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in dual-pane mode.
            dualPane = true
        }

        if(savedInstanceState == null) {
            rentalpropertyFragment = RentalPropertyFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.property_list_container, rentalpropertyFragment, rentalPropertyFragment)
                    .commit()
        } else {
            rentalpropertyFragment = supportFragmentManager.findFragmentByTag(rentalPropertyFragment) as RentalPropertyFragment
        }

    }

    override fun onRentalPropertySelected(item: DataItem) {
        if (dualPane) {
                    val fragment = RentalPropertyDetailsFragment().apply {
                        arguments = Bundle().apply {
                            putString(RentalPropertyDetailsFragment.ITEM_ID, item.id)
                        }
                    }
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.property_detail_container, fragment)
                            .commit()
        } else {
            val intent = Intent(this, RentalPropertyDetailsActivity::class.java).apply {
                putExtra(RentalPropertyDetailsFragment.ITEM_ID, item.id)
            }
            startActivity(intent)
        }
    }
}
