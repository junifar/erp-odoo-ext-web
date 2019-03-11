package com.prasetia.erp.pojo.revenue

class RevenueYearHeaderData(
        val id:Long,
        val tahun:Int?,
        val nilai_po:Double?,
        val invoiced:Double?,
        val paid:Double?,
        val total:Double?,
        val target:Double?,
        var revenue_year_detail: List<RevenueYearDetailData>?
){
 constructor(): this(0,0, 0.0,0.0, 0.0, 0.0, 0.0, null)
}

class RevenueYearDetailData(
        val id:Long,
        val customer_id:Long,
        val code:String,
        val jumlah_site:Int,
        val tahun:Int?,
        val nilai_po:Double?,
        val invoiced:Double?,
        val paid:Double?,
        val total:Double?,
        val target:Double?
){
 constructor(): this(0,0,"", 0,0,0.0, 0.0, 0.0, 0.0, 0.0)
}
