package hu.korsosrichard.demoapp.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.korsosrichard.demoapp.databinding.FragmentBookListItemBinding
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.models.BookAndAuthor

class BookListAdapter :
    ListAdapter<BookAndAuthor, BookListAdapter.BookViewHolder>(DiffCallback) {

    inner class BookViewHolder(val binding: FragmentBookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookAndAuthor: BookAndAuthor) {
            binding.bookAndAuthor = bookAndAuthor
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentBookListItemBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<BookAndAuthor>() {
        override fun areItemsTheSame(oldBook: BookAndAuthor, newBook: BookAndAuthor): Boolean {
            return oldBook.book.id == newBook.book.id
        }

        override fun areContentsTheSame(oldBook: BookAndAuthor, newBook: BookAndAuthor): Boolean {
            return oldBook == newBook
        }
    }
}