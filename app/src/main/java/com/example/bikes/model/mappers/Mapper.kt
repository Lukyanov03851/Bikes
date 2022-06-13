package com.example.bikes.model.mappers

interface Mapper<SRC, DST> {
    fun transform(src: SRC): DST
}