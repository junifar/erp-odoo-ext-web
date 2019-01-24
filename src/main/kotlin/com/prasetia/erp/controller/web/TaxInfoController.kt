package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.pojo.taxinfo.TaxInvoiceData
import com.prasetia.erp.pojo.taxinfo.TaxInvoicePeriodeData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL

@Controller("Tax Info Controller")
class TaxInfoController {

    @RequestMapping("/taxinfo")
    fun indexTaxInfo(model:Model):String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/taxinfoperiode")
        val taxInfoSummaryDataList: List<TaxInvoicePeriodeData> = objectMapper.readValue(url)
        model.addAttribute("taxInfoSummaryDataList", taxInfoSummaryDataList.sortedByDescending { it.year })
        return "taxinfo/index"
    }

    @RequestMapping("taxinfo/{tahun}")
    fun taxInfoYear(model:Model, @PathVariable("tahun") tahun:String):String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/taxinfo/%s".format(tahun))
        val taxInfoDataList:List<TaxInvoiceData> = objectMapper.readValue(url)

        model.addAttribute("taxInfoDataList", taxInfoDataList.sortedBy { it.tanggal_pembayaran })
        model.addAttribute("periode", tahun)
        return "taxinfo/tax_invoice_by_year"
    }

}
