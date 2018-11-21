package com.prasetia.erp.pojo.corrective

class CorrectiveSOData(
        val id:Long,
        val year_project:Long,
        val site_name:String,
        val project_id:String,
        val no_po:String,
        val nilai_po:Long,
        val nilai_invoice:Long,
        val persent_invoice:Float
){
    constructor(): this(0,0,"","","",0,0,0f)
}

class CorrectiveBudgetData(
        val id:Long,
        val budget_id:Long?,
        val customer_id:Long?,
        val year_project:Long?,
        val site_name:String?,
        val project_id:String?,
        val nomor_budget:String?,
        val nilai_budget:Long?,
        val realisasi_budget:Long?,
        val persent_budget:Float?
){
    constructor(): this(0,0,0,0,"","","",0,0,0f)
}

class CorrectiveDetailYearCustomerData(
        val id: Long,
        val customer_id:Long,
        val code:String,
        val jumlah_site: Long?,
        val year_project: String?,
        val nilai_po: Long?,
        val nilai_inv: Long?,
        val realisasi_budget: Long?,
        val nilai_budget: Long?,
        val percentage:Float?,
        val persent_budget:Float?,
        val profit: Long?,
        val profit_precentage: Float?,
        var sales_order: List<CorrectiveSOData>?,
        var budget: List<CorrectiveBudgetData>?
){
    constructor(): this(0,0,"",0,"",0,
            0,0,0,0f,0f,0,0f, null, null)
}