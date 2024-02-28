package com.dongnaoedu.carhome.mapper

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
interface Mapper<I, O> {

    fun map(input: I): O

}