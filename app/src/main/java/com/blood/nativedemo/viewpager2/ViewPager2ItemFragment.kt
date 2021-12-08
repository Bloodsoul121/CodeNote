package com.blood.nativedemo.viewpager2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blood.nativedemo.databinding.LayoutItemViewpagerBinding

/**
 * @Author: cgz
 * @Email: caiguangzu.cgz@alibaba-inc.com
 * @CreateDate: 2021/8/28 13:23
 * @Description:
 */
class ViewPager2ItemFragment private constructor() : Fragment() {

    companion object {

        private const val EXTRA_MSG = "extra_num"

        fun newInstance(msg: String): ViewPager2ItemFragment {
            val fragment = ViewPager2ItemFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_MSG, msg)
            fragment.arguments = bundle
            return fragment
        }

    }

    private val TAG = "ViewPager2ItemFragment ${memAddress()}"

    private lateinit var binding: LayoutItemViewpagerBinding

    override fun onAttach(context: Context) {
        Log.i(TAG, "onAttach: ")
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView: ")
        binding = LayoutItemViewpagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated: ")
        val msg = arguments?.getString(EXTRA_MSG)
            ?: throw IllegalArgumentException("arguments error")
        binding.tv1.text = "$msg text1"
        binding.tv2.text = "$msg text2"
    }

    override fun onStart() {
        Log.i(TAG, "onStart: ")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume: ")
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i(TAG, "onDetach: ")
        super.onDetach()
    }

    private fun memAddress(): String {
        return Integer.toHexString(System.identityHashCode(this))
    }

}