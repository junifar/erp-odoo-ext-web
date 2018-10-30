package com.prasetia.erp.pojo


class PreventiveCustomerYear(
        val id: Long,
        var tahun: String,
        var detail: MutableList<PreventiveCustomerGroup>?
){
    constructor(): this(0,"", null)
}

class PreventiveCustomerGroup(
        val id:Long,
        var customer: String?,
        var customer_id: Long?,
        var detail: MutableList<PreventiveCustomerGroupDetail>?
){
    constructor(): this(0, "", 0, null)
}

class PreventiveCustomerGroupDetail(
        val id: Long,
        val area: String?,
        val nilai_po: Long?,
        val nilai_penagihan: Long?,
        val nilai_budget: Long?,
        val realisasi_budget: Long?,
        val laba_rugi: Long?,
        val persent_penagihan:Long?,
        val persent_budget:Long?,
        val persent_laba_rugi:Long?,
        val area_id:Long?
){
    constructor(): this(0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0)
}