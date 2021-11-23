package com.achmadalfiansyah.moviecatalog.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.achmadalfiansyah.moviecatalog.databinding.FragmentTvShowBinding

class TvShowFragment : Fragment() {

    private lateinit var tvShowViewModel: TvShowViewModel
    private var _binding: FragmentTvShowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvShowViewModel =
            ViewModelProvider(this).get(TvShowViewModel::class.java)

        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        tvShowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}