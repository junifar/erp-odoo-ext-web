package com.prasetia.erp.pojo.department

class DepartmentPeriodeTypeData(
        val id: Long?,
        val department_name: String?,
        val nilai_budget: Long?,
        val realisasi_budget: Long?,
        val persent_budget: Float?
){
    constructor():this(0,"",0,0,0f)
}
