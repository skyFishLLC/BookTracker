package com.example.booktracker

import com.example.booktracker.books.domain.model.Book

object MockData {
    fun getDomainBooks() = arrayListOf(
        Book(0, "t0","a0","g0","s0",false),
        Book(1, "t1","a1","g1","s1",false),
        Book(2, "t2","a1","g2","s2",false),
        Book(3, "t3","a1","g3","s3",false),
        Book(4, "t4","a1","g4","s4",false),
    )
}