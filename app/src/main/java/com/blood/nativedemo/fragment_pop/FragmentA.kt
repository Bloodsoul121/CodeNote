package com.blood.nativedemo.fragment_pop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.blood.nativedemo.R
import com.blood.nativedemo.databinding.LayoutFragmentABinding

class FragmentA : Fragment() {

    private val TAG = "FragmentA"

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
        binding.title.text = "FragmentA"
        binding.btn.text = "skip FragmentB"
        binding.btn.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, FragmentB())
            transaction?.addToBackStack("FragmentA")
            transaction?.commit()
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