package com.taskapp.happyshoppingapp.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.models.app.ProductItem
import com.taskapp.happyshoppingapp.data.call_back.OnItemClick
import com.taskapp.happyshoppingapp.databinding.FragmentHomeBinding
import com.taskapp.happyshoppingapp.ui.MainActivity
import com.taskapp.happyshoppingapp.ui.adapter.ItemAdapter
import com.taskapp.happyshoppingapp.util.askForConfirmation
import com.taskapp.happyshoppingapp.util.showErrorDialog

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var model: HomeViewModel
    private fun initViewModel() {
        model = (activity as MainActivity).homeViewModel

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViewModel()

        binding.itemRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = ItemAdapter()

        binding.itemRecycler.adapter = adapter

        adapter.onItemClick = object : OnItemClick {
            override fun onClick(item: Any) {
                item as ProductItem
                Toast.makeText(requireContext(), "You Click ${item.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        adapter.onDeleteClick = object : OnItemClick {
            override fun onClick(item: Any) {
                item as ProductItem
                askForConfirmation(
                    activity = requireActivity(),
                    message = "${getString(R.string.deletConfirmationMessag)} ${item.name} item ?",
                    onItemClick = object : OnItemClick {
                        override fun onClick(item_: Any) {
                            model.deleteItemById(item)
                        }
                    },
                )
            }
        }



        adapter.onLikeClick = object : OnItemClick {
            override fun onClick(item: Any) {
                item as ProductItem
                item.like = !item.like
                adapter.update(item)
            }

        }

        model.deleteLiveData().observe(viewLifecycleOwner, {

            it as Map<String, Any>
            if (!(it["error"] as Boolean)) {
                adapter.deleteItem(it["data"] as ProductItem)
            } else {
                showErrorDialog(requireActivity(), errorMessage = it["error_message"].toString())
            }

        })

        (activity as MainActivity).setTitle(getString(R.string.homePage))
        model.getItemsLiveData().observe(viewLifecycleOwner, {
            it as Map<String, Any>
            if (!(it["error"] as Boolean)) {
                adapter.setDataSource(it["data"] as List<ProductItem>)
            } else {
                showErrorDialog(requireActivity(), errorMessage = it["error_message"].toString())
            }
        })

        model.getItems()
        return binding.root
    }




}