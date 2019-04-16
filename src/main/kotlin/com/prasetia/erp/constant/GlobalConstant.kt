package com.prasetia.erp.constant

class GlobalConstant{
    companion object {
//        const val BASE_URL = "http://192.168.0.200:8080/erp/"
        const val BASE_URL = "http://192.168.0.200:8080/"
//        const val BASE_URL = "http://localhost:8888/"
//        const val BASE_URL = "http://localhost:8080/erp/"

        const val MAIN_ROOT = "/"
        const val BUDGET_DEPT_URL = "budget_department/"
        const val BUDGET_PROJECT_URL = "budget_project/"
        const val BUDGET_MAINTENANCE_URL = "budget_maintenance/"
        const val ACCOUNTING_URL = "accounting/"
        const val DIREKSI_URL = "direksi/"
        const val GENERAL_URL = "general/"

        const val REDIRECT_LOGIN_URL = "redirect:/login"
        const val REDIRECT_LOGOUT_URL = "redirect:/logout"

        const val PREVENTIVE_TITLE_PAGE = "Maintenance Preventive"
        const val MAINTENANCE_TITLE_PAGE = "Maintenance"
        const val APPLICATION_NAME = "Prasetia ERP Report"
    }
}
