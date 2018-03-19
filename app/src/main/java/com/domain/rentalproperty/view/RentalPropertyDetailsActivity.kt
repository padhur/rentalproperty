package com.domain.rentalproperty.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.domain.rentalproperty.property.R
import kotlinx.android.synthetic.main.activity_rentalproperty_detail.*

/**
 * An activity representing a single Property detail screen. This
 * activity is only used on narrow width devices.
 * in a [RentalPropertyActivity].
 */
class RentalPropertyDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rentalproperty_detail)
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = RentalPropertyDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(RentalPropertyDetailsFragment.ITEM_ID,
                            intent.getStringExtra(RentalPropertyDetailsFragment.ITEM_ID))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.property_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, RentalPropertyActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
