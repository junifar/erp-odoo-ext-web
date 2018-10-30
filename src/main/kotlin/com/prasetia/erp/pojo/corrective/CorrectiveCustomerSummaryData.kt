package com.prasetia.erp.pojo.corrective

class CorrectiveCustomerSummaryData(
        val id: Long,
        val jumlah_site: Long?,
        val year_project: Long?,
        val nilai_po: Long?,
        val nilai_inv: Long?,
        val realisasi_budget: Long?,
        val percentage: Float?,
        val profit: Long?,
        val profit_precentage: Float?
){
        constructor(): this(0,0,0,0,0,0,0f,0,0f)
}