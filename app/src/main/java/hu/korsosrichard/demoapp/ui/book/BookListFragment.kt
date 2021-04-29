package hu.korsosrichard.demoapp.ui.book

import RecyclerItemClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import hu.korsosrichard.demoapp.MainActivity
import hu.korsosrichard.demoapp.R
import hu.korsosrichard.demoapp.databinding.FragmentBookListBinding
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.models.BookAndAuthor
import hu.korsosrichard.demoapp.ui.author.AuthorFormFragment
import hu.korsosrichard.demoapp.utils.RemovingItemTouchHelperCallback
import hu.korsosrichard.demoapp.viewmodels.BookViewModel
import hu.korsosrichard.demoapp.viewmodels.ViewModelFactory
import javax.inject.Inject


class BookListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val bookViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BookViewModel::class.java].apply {
            loadBookAndAuthorList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        requireActivity().title = "Books"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBookListBinding.inflate(inflater)
        binding.recycleview.adapter = BookListAdapter()

        binding.viewModel = bookViewModel
        binding.lifecycleOwner = this
        binding.fab.setOnClickListener {
            (requireActivity() as MainActivity).showFragmentWithToolbar(
                BookFormFragment.newInstance(null), null, null
            )
        }

        ItemTouchHelper(object : RemovingItemTouchHelperCallback<Book>(this, bookViewModel) {
            override fun getData(position: Int): Book =
                bookViewModel.bookAndAuthorList.value!!.data!![position].book
        }).attachToRecyclerView(binding.recycleview)

        binding.recycleview.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recycleview,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val bookAndAuthor = bookViewModel.bookAndAuthorList.value!!.data!![position]

                        (requireActivity() as MainActivity).showFragment(
                            BookDetailsFragment.newInstance(bookAndAuthor.book.id), null, null
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