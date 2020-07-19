package isel.leic.ps.iqueue.requests

interface Callback<T> {
    fun onSuccess(response: T)
}