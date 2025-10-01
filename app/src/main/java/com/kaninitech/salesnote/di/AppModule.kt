package com.kaninitech.salesnote.di


import com.kaninitech.salesnote.data.local.AppDatabase
import com.kaninitech.salesnote.repository.SingleProductRepository
import com.kaninitech.salesnote.repository.SingleSaleRepository
import com.kaninitech.salesnote.viewmodel.SingleProductSaleViewModel
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
//import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {

    single{ AppDatabase.manageDatabase(get()).singleSaleProductDao() }
    single { SingleProductRepository(get()) }
    viewModel { SingleProductSaleViewModel(get()) }


    single { AppDatabase.manageDatabase(get()).singleSaleDao() }
    single { SingleSaleRepository(get()) }
    viewModel { SingleSaleViewModel(get()) }

}