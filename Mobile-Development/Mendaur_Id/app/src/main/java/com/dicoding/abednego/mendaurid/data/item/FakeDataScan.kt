package com.dicoding.abednego.mendaurid.data.item

import com.dicoding.abednego.mendaurid.data.api.response.mendaur.Result

object FakeDataScan {
    val result: List<Result> = listOf(
        Result(
            jenis = "Plastik",
            akurasi = "99%",
            tipe = "Non-organik"
        )
    )
}