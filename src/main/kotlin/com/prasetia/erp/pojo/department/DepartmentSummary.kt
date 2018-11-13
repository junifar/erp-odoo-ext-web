package com.prasetia.erp.pojo.department

class DepartmentSummary(
        val id:Long,
        val periode:Long?,
        val nilai_budget:Long?,
        val persent_budget:Float?
){
    constructor(): this(0,0,0,0f)
}