package com.prasetia.erp.pojo.revenue


class RevenueYearData(
        val id:Long,
        val tahun:Int?,
        val nilai_po:Double?,
        val invoiced:Double?,
        val paid:Double?,
        val total:Double?,
        val target:Double?
){
    constructor(): this(0,0, 0.0,0.0, 0.0, 0.0, 0.0)
}
