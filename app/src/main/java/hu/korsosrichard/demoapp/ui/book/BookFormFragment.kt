package hu.korsosrichard.demoapp.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import hu.korsosrichard.demoapp.databinding.FragmentBookFormBinding
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.ui.FormFragment
import hu.korsosrichard.demoapp.utils.CoreAdapter
import hu.korsosrichard.demoapp.utils.Status
import hu.korsosrichard.demoapp.utils.Validator
import hu.korsosrichard.demoapp.viewmodels.AuthorViewModel
import hu.korsosrichard.demoapp.viewmodels.BookViewModel
import hu.korsosrichard.demoapp.viewmodels.ViewModelFactory
import javax.inject.Inject

class BookFormFragment : FormFragment<Book>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val bookViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BookViewModel::class.java]
    }

    private val authorViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthorViewModel::class.java].apply {
            loadList()
        }
    }

    private lateinit var binding: FragmentBookFormBinding

    val book by lazy {
        arguments?.getParcelable<Book>("book")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookFormBinding.inflate(inflater)
        if (book != null) {
            binding.book = book
        }

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        authorViewModel.list.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                binding.author.adapter = CoreAdapter(it.data!!, first = "Select the author")
                val index = it.data.indexOfFirst { author -> author.id == book?.authorId }
                binding.author.setSelection(index + 1)
            }
        })
    }

    override fun validate(): Boolean {
        var result = true;
        result = Validator.asRequired(binding.title.text, binding.errorTitle) && result
        result = Validator.asRequired(
            binding.author.selectedItemPosition > 0,
            binding.errorAuthor
        ) && result
        return result
    }

    override fun save() {
        val newItem = Book(
            book?.id ?: 0,
            binding.author.selectedItemId,
            binding.title.text.toString().trim(),
            binding.description.text.toString().trim(),
            binding.isbn.text.toString().trim(),
            binding.publication.text.toString().trim().toIntOrNull()
        )

        if (book != null) {
            bookViewModel.update(newItem).observe(viewLifecycleOwner, ResultObserver())
        } else {
            bookViewModel.insert(newItem).observe(viewLifecycleOwner, ResultObserver())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(book: Book?) =
            BookFormFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("book", book)
                }
            }
    }
}