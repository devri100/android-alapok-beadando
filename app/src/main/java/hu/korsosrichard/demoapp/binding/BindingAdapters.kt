package hu.korsosrichard.demoapp.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.korsosrichard.demoapp.models.Author
import hu.korsosrichard.demoapp.models.Book
import hu.korsosrichard.demoapp.models.BookAndAuthor
import hu.korsosrichard.demoapp.ui.author.AuthorListAdapter
import hu.korsosrichard.demoapp.ui.book.BookListAdapter

@BindingAdapter("bookAndAuthorList")
fun bindBooksRecycleView(recyclerView: RecyclerView, data: List<BookAndAuthor>?) {
    val adapter = recyclerView.adapter as BookListAdapter
    adapter.submitList(data)
}

@BindingAdapter("authors")
fun bindAuthorsRecycleView(recyclerView: RecyclerView, data: List<Author>?) {
    val adapter = recyclerView.adapter as AuthorListAdapter
    adapter.submitList(data)
}