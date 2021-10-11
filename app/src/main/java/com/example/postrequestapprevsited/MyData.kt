package com.example.postrequestapprevsited

import com.google.gson.annotations.SerializedName

class MyData {

    var data: List<MyDataDetails>? = null

    class MyDataDetails {

        var pk: Int? = null

        var name: String? = null

        var location: String? = null

        constructor(name: String?, location: String?, pk: Int) {
            this.name = name
            this.location = location
            this.pk = pk
        }
    }
}