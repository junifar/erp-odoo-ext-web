package com.prasetia.erp.pojo.department

class DepartmentBudgetRealisasiData(
        val id:Long,
        val budget_id:Long,
        val parent_id:Long,
        val ref:String?,
        val narration:String?,
        val budget_realisasi:Long?
){
    constructor(): this(0,0,0,"", "",0)
}

class DepartmentBudgetDetailData(
        val id:Long,
        val budget_id: Long?,
        val line_id:Long?,
        val code:String?,
        val budget_item_view:String?,
        val nilai_budget:Long?,
        val realisasi_budget:Long?,
        val persent_budget: Float?,
        var realisasi:List<DepartmentBudgetRealisasiData>?
){
    constructor(): this(0,0,0 , "","",0,0,0f, null)
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