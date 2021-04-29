package hu.korsosrichard.demoapp.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import hu.korsosrichard.demoapp.databinding.FragmentAuthorFormBinding
import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.ui.FormFragment
import hu.korsosrichard.demoapp.utils.Validator
import hu.korsosrichard.demoapp.utils.defaultDateFormat
import hu.korsosrichard.demoapp.utils.showDatePickerDialog
import hu.korsosrichard.demoapp.viewmodels.AuthorViewModel
import hu.korsosrichard.demoapp.viewmodels.ViewModelFactory
import java.util.*
import javax.inject.Inject

class AuthorFormFragment : FormFragment<Author>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var selectedDate: Date? = null

    private val authorViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthorViewModel::class.java]
    }

    private lateinit var binding: FragmentAuthorFormBinding

    val author by lazy {
        arguments?.getParcelable<Author>("author")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = if(author == null)  "Add Author" else "Edit Author"

        binding = FragmentAuthorFormBinding.inflate(inflater)
        binding.author = author
        binding.birthday.setOnClickListener {
            showDatePickerDialog(requireContext(), author?.birthday){ date ->
                selectedDate = date
                binding.birthday.setText(defaultDateFormat().format(date))
            }
        }
        return binding.root
    }

    override fun validate(): Boolean {
        var result = true
        result = Validator.asRequired(binding.firstName.text, binding.errorFirstName) && result
        result = Validator.asRequired(binding.lastName.text, binding.errorLastName) && result
        return result
    }

    override fun save() {
        val newAuthor = Author(
            author?.id ?: 0,
            binding.firstName.text.toString().trim(),
            binding.lastName.text.toString().trim(),
            selectedDate
        )

        if (author != null) {
            authorViewModel.update(newAuthor).observe(viewLifecycleOwner, ResultObserver())
        } else {
            authorViewModel.insert(newAuthor).observe(viewLifecycleOwner, ResultObserver())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(author: Author?) =
            AuthorFormFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("author", author)
                }
            }
    }
}