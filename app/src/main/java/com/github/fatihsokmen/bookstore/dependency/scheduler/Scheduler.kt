package com.github.fatihsokmen.bookstore.dependency.scheduler

interface Scheduler {

    fun io(): io.reactivex.Scheduler

    fun main(): io.reactivex.Scheduler
}