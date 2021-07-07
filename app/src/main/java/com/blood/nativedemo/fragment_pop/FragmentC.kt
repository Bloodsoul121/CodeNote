package com.blood.nativedemo.fragment_pop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.blood.nativedemo.R
import com.blood.nativedemo.databinding.LayoutFragmentABinding

class FragmentC : Fragment() {

    private val TAG = "FragmentC"

    private lateinit var binding: LayoutFragmentABinding

    override fun onAttach(context: Context) {
        Log.i(TAG, "onAttach: ")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView: ")
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_a, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated: ")
        super.onActivityCreated(savedInstanceState)
        binding.title.text = "FragmentC"
        binding.btn2.text = "back FragmentB"
        binding.btn2.setOnClickListener {
            fragmentManager?.popBackStack("FragmentB", POP_BACK_STACK_INCLUSIVE)
//            fragmentManager?.popBackStack("FragmentB", 0)
        }
    }

    override fun onResume() {
        Log.i(TAG, "onResume: ")
        super.onResume()
    }

    override fun onPause() {
        Log.i(TAG, "onPause: ")
        super.onPause()
    }

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }

}