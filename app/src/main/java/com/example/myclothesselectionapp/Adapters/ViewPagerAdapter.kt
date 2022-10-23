package com.example.myclothesselectionapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.myclothesselectionapp.Models.Maindatamodel
import com.example.myclothesselectionapp.R
import java.util.*

class ViewPagerAdapter(val context:Context , val imageList: ArrayList<Maindatamodel>) : PagerAdapter() {
	// on below line we are creating a method
	// as get count to return the size of the list.
	override fun getCount(): Int {
		return imageList.size
	}

	// on below line we are returning the object
	override fun isViewFromObject(view: View, `object`: Any): Boolean {
		return view === `object` as LinearLayout
	}

	// on below line we are initializing
	// our item and inflating our layout file
	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		// on below line we are initializing
		// our layout inflater.
		val mLayoutInflater =
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

		// on below line we are inflating our custom
		// layout file which we have created.
		val itemView: View = mLayoutInflater.inflate(R.layout.dress_layout, container, false)

		// on below line we are initializing
		// our image view with the id.
		val top: ImageView = itemView.findViewById<View>(R.id.IV_dress_top) as ImageView
		val bottom: ImageView = itemView.findViewById<View>(R.id.IV_dress_bottom) as ImageView

		// on below line we are setting
		// image resource for image view.
         val size:Int=imageList.size-1
		//for (i in 0..(size)) {
			top.setImageResource(imageList.get(position).imgtop.get(position))
	//		bottom.setImageResource(imageList.get(position).imgbottom.get(position))
		//}



		// on the below line we are adding this
		// item view to the container.
		Objects.requireNonNull(container).addView(itemView)

		// on below line we are simply
		// returning our item view.
		return itemView
	}

	// on below line we are creating a destroy item method.
	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
		// on below line we are removing view
		container.removeView(`object` as LinearLayout)
	}
}
