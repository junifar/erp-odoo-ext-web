package com.prasetia.erp.pojo.cme

class CmeInvoiceProjectData(
        val id:Long,
        val project_id:Long?,
        val name:String?,
        val state:String?,
        val nilai_invoice:Double?
){
    constructor(): this(0,0,"","",0.0)
}

class CmeSummaryYearProjectTypeCustData(
        val id: Long,
        val year_project: Long,
        val jumlah_site: Long,
        val project_type: String,
        val site_cancel: Long,
        val nilai_po: Double,
        val nilai_invoice: Double,
        val nilai_budget: Double,
        val realisasi_budget: Double,
        val estimate_po: Double,
        val site_type_id: Long?,
        val customer:String?,
        val customer_id:Long?,
        val percentage: Double,
        val remaining_invoice: Double,
        val percentage_realization: Double,
        val profit_loss: Double,
        val percentage_profit_realization: Double,
        val percentage_profit_po: Double,
        var project_list: List<CmeYearProjectTypeCustProjectDetailData>?
){
    constructor(): this(0,0,0,"",0,
            0.0,0.0,0.0,
            0.0,0.0, 0, "",
            0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, null)
}
class CmeYearProjectTypeCustProjectDetailData(
    val id: Long,
    val name:String,
    val year_project: Long,
    val project_type: String,
    val project_id: String,
    val nilai_po: Double,
    val no_po: String?,
    val nilai_invoice: Double,
    val nilai_budget: Double,
    val realisasi_budget: Double,
    val estimate_po: Double,
    val customer:String?,
    val customer_id:Long?,
    val site_type_id: Long?,
    var percent_po : Double?,
    var percent_labarugi : Double?,
    val area: String?,
    var invoice_list:List<CmeInvoiceProjectData>?
){
    constructor(): this(0,"",0,"","",0.0,"",
            0.0,0.0,0.0,0.0,"", 0, 0, 0.0, 0.0, "", null)
}