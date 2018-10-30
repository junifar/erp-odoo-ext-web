package com.prasetia.erp.pojo.cme

class CmeSummaryYearData(
        val id: Long,
        val year_project: Long,
        val jumlah_site: Long,
        val site_cancel: Long,
        val nilai_po: Long,
        val nilai_invoice: Long,
        val nilai_budget: Long,
        val realisasi_budget: Long,
        val estimate_po: Long,
        val percentage: Float,
        val remaining_invoice: Long,
        val percentage_realization: Float,
        val profit_loss: Long,
        val percentage_profit_realization: Float,
        val percentage_profit_po: Float
){
    constructor(): this(0,0,0,0,0,0,0,
            0, 0, 0f, 0, 0f, 0,
            0f, 0f)
}