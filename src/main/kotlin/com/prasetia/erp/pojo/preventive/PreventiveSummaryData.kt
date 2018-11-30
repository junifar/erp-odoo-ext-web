package com.prasetia.erp.pojo.preventive

class PreventiveSummaryData(
        val id: Long,
        val tahun: String,
        val nilai_po: Double?,
        val nilai_penagihan: Double?,
        val nilai_budget: Double?,
        val realisasi_budget: Double?,
        val laba_rugi: Double?,
        val persent_penagihan:Double?,
        val persent_budget:Double?,
        val persent_laba_rugi:Long?
){
    constructor(): this(0, "", 0.0, 0.0,
            0.0, 0.0, 0.0,
            0.0,0.0,0)
}