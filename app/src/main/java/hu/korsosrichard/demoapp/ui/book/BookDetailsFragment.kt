package hu.korsosrichard.demoapp.ui.book

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import hu.korsosrichard.demoapp.MainActivity
import hu.korsosrichard.demoapp.R
import hu.korsosrichard.demoapp.databinding.FragmentBookDetailsBinding
import hu.korsosrichard.demoapp.ui.SimpleToolbarFragment
import hu.korsosrichard.demoapp.viewmodels.BookViewModel
import hu.korsosrichard.demoapp.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_book_details.view.*
import kotlinx.android.synthetic.main.fragment_book_form.view.*
import javax.inject.Inject

class BookDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val bookViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BookViewModel::class.java]
    }

    val id by lazy {
        requireArguments().getLong("id")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBookDetailsBinding.inflate(inflater)

        (requireActivity() as MainActivity).let {
            it.setSupportActionBar(binding.detailsToolbar)
            binding.detailsToolbar.setNavigationOnClickListener {
                (requireActivity() as MainActivity).onBackPressed()
            }
            it.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
//        setHasOptionsMenu(true)

        bookViewModel.id = id

        binding.viewModel = bookViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        binding.fab.setOnClickListener {
            (requireActivity() as MainActivity).showFragmentWithToolbar(
                BookFormFragment.newInstance(bookViewModel.bookAndAuthor.value?.data?.book), null, null
            )
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_book_details, menu)
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Long) = BookDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong("id", id)
            }
        }
    }
}