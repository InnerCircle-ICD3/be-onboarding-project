package com.innercircle.common

import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.ParamDef


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FilterDef(
    name = "deletedFilter",
    parameters = [ParamDef(name = "isDeleted", type = Boolean::class)]
)
@Filter(
    name = "deletedFilter",
    condition = "is_deleted = :isDeleted"
)
annotation class SoftDeleteFilter
