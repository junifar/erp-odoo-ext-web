package com.prasetia.erp.pojo.corrective

import java.util.*

class CorrectiveYearData(
        val id: Long,
        val customer_id:Long,
        val code:String,
        val jumlah_site: Long?,
        val year_project: String?,
        val nilai_po: Long?,
        val nilai_inv: Long?,
        val nilai_budget: Long?,
        val realisasi_budget: Long?,
        val percentage: Float?,
        val persent_budget: Float?,
        val profit: Long?,
        val profit_precentage: Float?,
        var projects : MutableList<CorrectiveProjectData>?
){
    constructor(): this(0,  0, "",0, "", 0, 0, 0,
            0,0f,0f,0,0f, null)
}

class CorrectiveProjectData(
        val id:Long,
        val year_project:String,
        val site_name:String,
        val customer:String,
        var budget_used: MutableList<CorrectiveBudgetUsedData>?,
        var cash_advance: MutableList<CorrectiveAdvanceData>?
){
    constructor(): this(0, "", "","", null, null)
}

class CorrectiveBudgetUsedData(
        val id:Long,
        val year_project:String?,
        val project_id:Long,
        val amount:Long,
        val narration:String?,
        val ref:String?,
        val pic:String?,
        val penerima_dana:String?,
        val tanggal: Date?
){
    constructor(): this(0, "", 0, 0, "","", "", "", null)
}

class CorrectiveAdvanceData(
        val id:Long,
        val year_project:String,
        val project_id:Long,
        val amount:Long,
        val narration:String?,
        val ref:String,
        val pic:String,
        val penerima_dana:String?,
        val tanggal: Date?,
        val ca_id: Long,
        val no_mi: String?,
        val no_po:String?,
        val nilai_po:Long?,
        var advance_invoice:List<CorrectiveAdvanceInvoiceData>?
){
    constructor(): this(0, "", 0, 0, "","", "", "", null, 0, "", "", 0, null)
}

class CorrectiveAdvanceInvoiceData(
        val id:Long,
        val nilai_invoice:Long,
        val invoice_state:String,
        val no_inv:String,
        val year_project:String
){
    constructor(): this(0, 0, "", "","")
}