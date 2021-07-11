package com.lla.panicbutton.ui.fragments.onboardingfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.lla.panicbutton.R
import com.lla.panicbutton.adapters.ViewPagerAdapter
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            Welcome2Fragment(),
            Welcome3Fragment(),
            Welcome4Fragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager2.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            Welcome2Fragment(),
            Welcome3Fragment(),
            Welcome4Fragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager2.adapter = adapter

        val springDotsIndicator =
            requireView().findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)
        val viewPager = requireView().findViewById<ViewPager2>(R.id.viewPager2)
        springDotsIndicator?.setViewPager2(viewPager)

        view.viewPagerNextButton.setOnClickListener {
            if (viewPager.currentItem + 1 < adapter.itemCount) {
                viewPager.currentItem += 1
            } else {
                findNavController().navigate(R.id.action_viewPagerFragment_to_uploadFragment)
            }
        }
    }
}