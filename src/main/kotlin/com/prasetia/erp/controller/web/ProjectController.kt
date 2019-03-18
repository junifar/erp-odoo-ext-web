package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.pojo.project.ProjectRecapAgingData
import com.prasetia.erp.pojo.project.ProjectRecapData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL

@Controller("Project Web Controller")
class ProjectController{

    @RequestMapping("/project/recap")
    fun index(model:Model):String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/project/recap")
        val url_aging = URL(BASE_URL + "api/project/recap_aging")

        val projectRecapDataList: List<ProjectRecapData> = objectMapper.readValue(url)
        val projectRecapAgingDataList: List<ProjectRecapAgingData> = objectMapper.readValue(url_aging)

        model.addAttribute("projectRecapDataList", projectRecapDataList
                .filter { it.site_type != "Maintenance Corrective" }
                .filter { it.site_type != "Maintenance Preventive" }
                .sortedByDescending { it.po })

        model.addAttribute("projectRecapByValueDataList", projectRecapDataList
                .filter { it.site_type != "Maintenance Corrective" }
                .filter { it.site_type != "Maintenance Preventive" }
                .sortedByDescending { it.nilai_po })

        model.addAttribute("projectRecapAgingDataList", projectRecapAgingDataList
                .filter { it.site_type != "Maintenance Corrective" }
                .filter { it.site_type != "Maintenance Preventive" })
        return "project_summary/index"
    }
}
