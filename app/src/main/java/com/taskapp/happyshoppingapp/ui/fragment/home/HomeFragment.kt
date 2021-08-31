package com.taskapp.happyshoppingapp.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.taskapp.happyshoppingapp.R
import com.taskapp.happyshoppingapp.data.models.Item
import com.taskapp.happyshoppingapp.data.models.OnItemClick
import com.taskapp.happyshoppingapp.databinding.FragmentHomeBinding
import com.taskapp.happyshoppingapp.ui.MainActivity
import com.taskapp.happyshoppingapp.ui.adapter.ItemAdapter
import com.taskapp.happyshoppingapp.util.AppDialogs

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.itemRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        val model = (activity as MainActivity).homeViewModel

        val adapter = ItemAdapter()

        binding.itemRecycler.adapter = adapter

        adapter.setOnItemClick(object : OnItemClick {
            override fun onClick(item: Any) {
                item as Item
                Toast.makeText(requireContext(), "You Click ${item.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        adapter.setOnDeleteClick(object : OnItemClick {
            override fun onClick(item: Any) {
                item as Item
                AppDialogs.Instance.askForConfirmation(
                    text = "${getString(R.string.deletConfirmationMessag)} ${item.name} item ?",
                    onItemClick = object : OnItemClick {
                        override fun onClick(i: Any) {
                            model.deleteItemById(item)
                        }
                    },
                )
            }
        })

        adapter.setOnLikeClick(object : OnItemClick {
            override fun onClick(item: Any) {
                item as Item
                item.like = !item.like
                adapter.update(item)
            }
        })

        model.deleteLiveData().observe(viewLifecycleOwner, {
            adapter.deleteItem(it)
        })

        (activity as MainActivity).setTitle(getString(R.string.homePage))
        model.getItemsLiveData().observe(viewLifecycleOwner, {
            adapter.setDataSource(it)
        })

        model.getItems()
        return binding.root
    }


}