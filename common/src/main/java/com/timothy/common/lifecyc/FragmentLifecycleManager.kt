package com.timothy.common.lifecyc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentLifecycleManager private constructor(): FragmentManager.FragmentLifecycleCallbacks(){
    companion object{

        private val _instance = FragmentLifecycleManager()

        private const val TAG = "FLM"

        fun get(): FragmentLifecycleManager{
            return _instance
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        Log.d(TAG, f::class.java.name + " onFragmentCreated")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        Log.d(TAG, f::class.java.name + " onFragmentResumed")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        Log.d(TAG, f::class.java.name + " onFragmentPaused")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Log.d(TAG, f::class.java.name + " onFragmentDestroyed")
    }
}