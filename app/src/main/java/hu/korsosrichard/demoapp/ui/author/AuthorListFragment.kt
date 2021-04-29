package hu.korsosrichard.demoapp.ui.author

import RecyclerItemClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.android.support.AndroidSupportInjection
import hu.korsosrichard.demoapp.MainActivity
import hu.korsosrichard.demoapp.databinding.FragmentAuthorListBinding
import hu.korsosrichard.demoapp.utils.RemovingItemTouchHelperCallback
import hu.korsosrichard.demoapp.viewmodels.AuthorViewModel
import hu.korsosrichard.demoapp.viewmodels.ViewModelFactory
import javax.inject.Inject


class AuthorListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authorViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthorViewModel::class.java].apply {
            loadList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = "Authors"

        val binding = FragmentAuthorListBinding.inflate(inflater)
        binding.recycleview.adapter = AuthorListAdapter()
        binding.viewModel = authorViewModel
        binding.lifecycleOwner = this
        binding.fab.setOnClickListener {
            (requireActivity() as MainActivity).showFragmentWithToolbar(
                AuthorFormFragment.newInstance(null), null, null
            )
        }

        ItemTouchHelper(
            RemovingItemTouchHelperCallback(
                this,
                authorViewModel
            )
        ).attachToRecyclerView(binding.recycleview)

        binding.recycleview.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recycleview,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val author = authorViewModel.list.value!!.data!![position]

                        (requireActivity() as MainActivity).showFragmentWithToolbar(
                            AuthorFormFragment.newInstance(author), null, null
                        )
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // do whatever
                    }
                })
        )

        return binding.root
    }

}