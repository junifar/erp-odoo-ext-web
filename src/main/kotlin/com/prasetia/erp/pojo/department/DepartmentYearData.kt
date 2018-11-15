package com.prasetia.erp.pojo.department


class DepartmentYearData(
        val id:Long,
        val department_name:String?,
        val department_id:Long?,
        val nilai_budget:Long?,
        val realisasi_budget:Long?,
        val persent_budget: Float?
){
    constructor(): this(0,"",0,0,0,0f)
}
