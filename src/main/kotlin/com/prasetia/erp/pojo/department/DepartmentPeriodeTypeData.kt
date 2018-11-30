package com.prasetia.erp.pojo.department

class DepartmentPeriodeTypeData(
        val id: Long?,
        val department_name: String?,
        val nilai_budget: Double?,
        val realisasi_budget: Double?,
        val persent_budget: Double?
){
    constructor():this(0,"",0.0,0.0,0.0)
}
