package com.blood.nativedemo.viewpager2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blood.nativedemo.databinding.ActivityViewPager2Binding
import com.blood.nativedemo.databinding.LayoutItemViewpagerBinding

class ViewPager2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPager2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPager2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
//        val adapter = ViewAdapter(this)
        val adapter = FragmentAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        adapter.update(listOf("page1", "page2", "page3"))
    }

    private class ViewAdapter(private val context: Context) :
        RecyclerView.Adapter<ViewAdapter.MyViewHolder>() {

        private val data = ArrayList<String>()

        fun update(list: List<String>) {
            data.clear()
            data.addAll(list)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding = LayoutItemViewpagerBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.tv1.text = "${data[position]} text1"
            holder.binding.tv2.text = "${data[position]} text2"
        }

        override fun getItemCount(): Int {
            return data.size
        }

        private class MyViewHolder(val binding: LayoutItemViewpagerBinding) :
            RecyclerView.ViewHolder(binding.root)

    }

    private class FragmentAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        private val data = ArrayList<String>()

        fun update(list: List<String>) {
            data.clear()
            data.addAll(list)
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun createFragment(position: Int): Fragment {
            Log.i("FragmentAdapter", "createFragment: $position")
            return ViewPager2ItemFragment.newInstance(data[position])
        }

    }

}