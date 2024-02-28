package com.dongnaoedu.carhome.mapper

import com.dongnaoedu.carhome.entity.CarBrandEntity
import com.dongnaoedu.carhome.model.CarBrandItemModel

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class Entity2ItemModelMapper : Mapper<CarBrandEntity,CarBrandItemModel> {
    override fun map(input: CarBrandEntity): CarBrandItemModel =
        CarBrandItemModel(id = input.id, name = input.name, icon = input.icon)
}