package com.prasetia.erp.pojo.department

class DepartmentBudgetDetailData(
        val id:Long?,
        val code:String?,
        val budget_item_view:String?,
        val nilai_budget:Long?,
        val realisasi_budget:Long?,
        val persent_budget: Float?
){
    constructor(): this(0, "","",0,0,0f)
}

class DepartmentBudgetYearData(
        val id:Long?,
        val department_name:String?,
        val department_id:Long?,
        val nilai_budget:Long?,
        val realisasi_budget:Long?,
        val persent_budget: Float?,
        var department_budget: MutableList<DepartmentBudgetData>?
){
    constructor(): this(0,"", 0,0,0,0f, null)
}

class DepartmentBudgetData(
        val id:Long?,
        val name:String?,
        val nilai_budget:Long?,
        val realisasi_budget: Long?,
        val persent_budget: Float?,
        var budget_detail: MutableList<DepartmentBudgetDetailData>?
){
    constructor(): this(0,"",0,0,0f,null)
}