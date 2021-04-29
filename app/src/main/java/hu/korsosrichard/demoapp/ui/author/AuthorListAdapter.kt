package hu.korsosrichard.demoapp.ui.author

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.korsosrichard.demoapp.databinding.FragmentAuthorListItemBinding
import hu.korsosrichard.demoapp.databinding.FragmentBookListItemBinding
import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.models.Book

class AuthorListAdapter :
    ListAdapter<Author, AuthorListAdapter.AuthorViewHolder>(DiffCallback) {

    inner class AuthorViewHolder(val binding: FragmentAuthorListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(author: Author) {
            binding.author = author
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentAuthorListItemBinding.inflate(inflater, parent, false)
        return AuthorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Author>() {
        override fun areItemsTheSame(oldBook: Author, newBook: Author): Boolean {
            return oldBook.id == newBook.id
        }

        override fun areContentsTheSame(oldBook: Author, newBook: Author): Boolean {
            return oldBook == newBook
        }
    }
}