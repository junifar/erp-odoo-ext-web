package com.prasetia.erp.pojo.preventive

class PreventiveCustomerDetailHeader(
        val id:Long,
        val customer_id:Long?,
        val customer_name:String?,
        val area:String?,
        val area_id:Long?,
        val tahun:String,
        var sale_order: MutableList<PreventiveSaleOrder>?,
        var invoice: MutableList<PreventiveInvoice>?,
        var budget_area: MutableList<PreventiveBudgetArea>?,
        var realisasi_budget_area: MutableList<PreventiveRealisasiBudgetArea>?
){
    constructor(): this(0, 0, "", "", 0, "", null, null, null, null)
}

class PreventiveSaleOrder(
        val id:Long,
        val client_order_ref: String,
        val i:Long?,
        val ii:Long?,
        val iii:Long?,
        val iv:Long?,
        val v:Long?,
        val vi:Long?,
        val vii:Long?,
        val viii:Long?,
        val ix:Long?,
        val x:Long?,
        val xi:Long?,
        val xii:Long?,
        val total: Long?,
        var sale_order_invoice: MutableList<PreventiveSaleOrderInvoice>?
){
    constructor(): this(0, "", 0, 0,0,0,0,0,0,0,0,0,0,0, 0, null)
}

class PreventiveInvoice(
        val id:Long,
        val client_order_ref: String,
        val i:Long?,
        val ii:Long?,
        val iii:Long?,
        val iv:Long?,
        val v:Long?,
        val vi:Long?,
        val vii:Long?,
        val viii:Long?,
        val ix:Long?,
        val x:Long?,
        val xi:Long?,
        val xii:Long?,
        val total: Long?
){
    constructor(): this(0, "", 0, 0,0,0,0,0,0,0,0,0,0,0, 0)
}

class PreventiveBudgetArea(
        val id:Long,
        val area_detail: String?,
        var budget: MutableList<PreventiveBudget>?
){
    constructor(): this(0, "", null)
}

class PreventiveBudget(
        val id:Long,
        val name: String,
        val i:Long?,
        val ii:Long?,
        val iii:Long?,
        val iv:Long?,
        val v:Long?,
        val vi:Long?,
        val vii:Long?,
        val viii:Long?,
        val ix:Long?,
        val x:Long?,
        val xi:Long?,
        val xii:Long?,
        val total: Long?
){
    constructor(): this(0, "", 0, 0,0,0,0,0,0,0,0,0,0,0, 0)
}

class PreventiveRealisasiBudgetArea(
        val id:Long,
        val area_detail: String?,
        var realisasi_budget: MutableList<PreventiveRealisasiBudget>?
){
    constructor(): this(0, "", null)
}

class PreventiveRealisasiBudget(
        val id:Long,
        val name: String,
        val i:Long?,
        val ii:Long?,
        val iii:Long?,
        val iv:Long?,
        val v:Long?,
        val vi:Long?,
        val vii:Long?,
        val viii:Long?,
        val ix:Long?,
        val x:Long?,
        val xi:Long?,
        val xii:Long?,
        val total: Long?
){
    constructor(): this(0, "", 0, 0,0,0,0,0,0,0,0,0,0,0, 0)
}

class PreventiveSaleOrderInvoice(
        val id:Long,
        val client_order_ref: String,
        val state:String,
        val i:Long?,
        val ii:Long?,
        val iii:Long?,
        val iv:Long?,
        val v:Long?,
        val vi:Long?,
        val vii:Long?,
        val viii:Long?,
        val ix:Long?,
        val x:Long?,
        val xi:Long?,
        val xii:Long?,
        val i_val:String,
        val ii_val:String,
        val iii_val:String,
        val iv_val:String,
        val v_val:String,
        val vi_val:String,
        val vii_val:String,
        val viii_val:String,
        val ix_val:String,
        val x_val:String,
        val xi_val:String,
        val xii_val:String,
        val i_state:String,
        val ii_state:String,
        val iii_state:String,
        val iv_state:String,
        val v_state:String,
        val vi_state:String,
        val vii_state:String,
        val viii_state:String,
        val ix_state:String,
        val x_state:String,
        val xi_state:String,
        val xii_state:String,
        val total: Long?
){
    constructor(): this(0,"", "",0,0,0,0,
            0,0,0,0,0,0,0,0,"","",
            "","","","","","","","","","",
            "","","","","","","","","",
            "","","", 0)
}