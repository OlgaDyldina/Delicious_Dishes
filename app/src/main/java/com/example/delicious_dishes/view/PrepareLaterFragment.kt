package com.example.delicious_dishes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.delicious_dishes.databinding.PrepareLaterFragmentBinding
import com.example.delicious_dishes.util.AnimationHelper



class PrepareLaterFragment : Fragment() {

        private lateinit var binding: PrepareLaterFragmentBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = PrepareLaterFragmentBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            AnimationHelper.performFragmentCircularRevealAnimation(binding.prepareLaterFragmentRoot, requireActivity(), 3)
        }
    }