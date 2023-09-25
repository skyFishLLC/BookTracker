package com.example.booktracker

import com.example.booktracker.books.data.local.LocalBook
import com.example.booktracker.books.data.remote.RemoteBook
import com.example.booktracker.books.domain.model.Book

object MockData {
    fun getDomainBooks() = arrayListOf(
        Book(0, "t0","a0","g0","s0",false),
        Book(1, "t1","a1","g1","s1",false),
        Book(2, "t2","a1","g2","s2",false),
        Book(3, "t3","a1","g3","s3",false),
        Book(4, "t4","a1","g4","s4",false),
    )

    fun getRemoteBooks() = listOf(
        RemoteBook(0, "t0","a0","g0","s0"),
        RemoteBook(1, "t1","a1","g1","s1"),
        RemoteBook(2, "t2","a1","g2","s2"),
        RemoteBook(3, "t3","a1","g3","s3"),
        RemoteBook(4, "t4","a1","g4","s4"),
    )

    fun getLocalBooks() = listOf(
        LocalBook(0, "t0","a0","g0","s0",false),
        LocalBook(1, "t1","a1","g1","s1",false),
        LocalBook(2, "t2","a1","g2","s2",false),
        LocalBook(3, "t3","a1","g3","s3",false),
        LocalBook(4, "t4","a1","g4","s4",false),
    )

}