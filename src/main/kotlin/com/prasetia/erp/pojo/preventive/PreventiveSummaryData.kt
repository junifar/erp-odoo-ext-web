package com.prasetia.erp.pojo.preventive

class PreventiveSummaryData(
        val id: Long,
        val tahun: String,
        val nilai_po: Long?,
        val nilai_penagihan: Long?,
        val nilai_budget: Long?,
        val realisasi_budget: Long?,
        val laba_rugi: Long?,
        val persent_penagihan:Long?,
        val persent_budget:Long?,
        val persent_laba_rugi:Long?
){
    constructor(): this(0, "", 0, 0, 0, 0, 0,
            0,0,0)
}