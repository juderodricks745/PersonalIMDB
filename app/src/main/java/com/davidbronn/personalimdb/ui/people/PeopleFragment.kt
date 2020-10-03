package com.davidbronn.personalimdb.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.databinding.PeopleFragmentBinding
import com.davidbronn.personalimdb.utils.extensions.urlWithListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PeopleFragment : Fragment() {

    private lateinit var viewModel: PeopleViewModel
    private lateinit var binding: PeopleFragmentBinding

    private val args: PeopleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.people_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PeopleViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchPerson(args.peopleID)
        binding.personHeader.ivPersonProfile.urlWithListener(args.peopleUrl) { }

    }
}