
# Stock Market App

## Module

- app
- core/common
- data
- domain
- features
  - Stock Chart
  
    Displays interactive charts for real-time or historical stock data.
  - Search Stock

    Enables users to search for stocks and view search history.



## Screenshots
<img src=".\screenshot\ss_chart_day.png" alt="chart_day" width="200"/> <img src=".\screenshot\ss_chart_night.png" alt="chart_night" width="200"/>
<img src=".\screenshot\ss_search_history_day.png" alt="search_history_day" width="200"/>
<img src=".\screenshot\ss_search_history_night.png" alt="search_history_night" width="200"/>
<img src=".\screenshot\ss_empty_history.png" alt="default_empty_history" width="200"/>
<img src=".\screenshot\ss_empty_history_query.png" alt="query_empty_history" width="200"/>


## 🛠 Tech Stack
- Kotlin
- Modular Clean Architecture
- Flow
- Koin
- Coil
- View Binding
- MP Android Chart
- Mockito
- Retrofit
- Room DB


## File Tree
<details>
<summary>
File Tree
</summary>

```
┣ 📂app
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃     ┗ 📂stockmarket
┃ ┃ ┃ ┃       ┣ 📂di
┃ ┃ ┃ ┃       ┃ ┣ 📂module
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜DatabaseModule.kt
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜DataModule.kt
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜DomainModule.kt
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ViewModelModule.kt
┃ ┃ ┃ ┃       ┃ ┗ 📜KoinInitializer.kt
┃ ┃ ┃ ┃       ┗ 📜MainApplication.kt
┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂core
┃ ┗ 📂common
┃   ┣ 📂src
┃   ┃ ┣ 📂main
┃   ┃ ┃ ┣ 📂java
┃   ┃ ┃ ┃ ┗ 📂com
┃   ┃ ┃ ┃   ┗ 📂riftar
┃   ┃ ┃ ┃     ┗ 📂common
┃   ┃ ┃ ┃       ┣ 📂helper
┃   ┃ ┃ ┃       ┃ ┗ 📜ValuesExtensions.kt
┃   ┃ ┃ ┃       ┗ 📂view
┃   ┃ ┃ ┃         ┣ 📂base
┃   ┃ ┃ ┃         ┃ ┗ 📜BaseActivity.kt
┃   ┃ ┃ ┃         ┣ 📜NavigationConstants.kt
┃   ┃ ┃ ┃         ┗ 📜ViewConstants.kt
┃   ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂data
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃     ┗ 📂data
┃ ┃ ┃ ┃       ┣ 📂common
┃ ┃ ┃ ┃       ┃ ┣ 📂client
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜HttpClientProvider.kt
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜NetworkErrorInterceptor.kt
┃ ┃ ┃ ┃       ┃ ┗ 📂database
┃ ┃ ┃ ┃       ┃   ┗ 📜AppDatabase.kt
┃ ┃ ┃ ┃       ┣ 📂searchhistory
┃ ┃ ┃ ┃       ┃ ┣ 📂mapper
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜SearchStockMapper.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂repository
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜SerchStockRepositoryImpl.kt
┃ ┃ ┃ ┃       ┃ ┗ 📂room
┃ ┃ ┃ ┃       ┃   ┣ 📂dao
┃ ┃ ┃ ┃       ┃   ┃ ┗ 📜SearchHistoryDao.kt
┃ ┃ ┃ ┃       ┃   ┗ 📂entity
┃ ┃ ┃ ┃       ┃     ┗ 📜SearchHistoryEntity.kt
┃ ┃ ┃ ┃       ┗ 📂stockchart
┃ ┃ ┃ ┃         ┣ 📂api
┃ ┃ ┃ ┃         ┃ ┗ 📜StockChartAPI.kt
┃ ┃ ┃ ┃         ┣ 📂mapper
┃ ┃ ┃ ┃         ┃ ┗ 📜StockChartMapper.kt
┃ ┃ ┃ ┃         ┣ 📂repository
┃ ┃ ┃ ┃         ┃ ┗ 📜StockChartRepositoryImpl.kt
┃ ┃ ┃ ┃         ┗ 📂response
┃ ┃ ┃ ┃           ┣ 📜ChartResponse.kt
┃ ┃ ┃ ┃           ┣ 📜ChartResponseWrapper.kt
┃ ┃ ┃ ┃           ┣ 📜CurrentTradingPeriodResponse.kt
┃ ┃ ┃ ┃           ┣ 📜ErrorResponse.kt
┃ ┃ ┃ ┃           ┣ 📜IndicatorsResponse.kt
┃ ┃ ┃ ┃           ┣ 📜MetaResponse.kt
┃ ┃ ┃ ┃           ┣ 📜QuoteResponse.kt
┃ ┃ ┃ ┃           ┣ 📜ResultResponse.kt
┃ ┃ ┃ ┃           ┣ 📜StockChartResponse.kt
┃ ┃ ┃ ┃           ┗ 📜TradingPeriodResponse.kt
┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂domain
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂cpp
┃ ┃ ┃ ┃ ┣ 📜CMakeLists.txt
┃ ┃ ┃ ┃ ┗ 📜domain.cpp
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃     ┗ 📂domain
┃ ┃ ┃ ┃       ┣ 📂searchhistory
┃ ┃ ┃ ┃       ┃ ┣ 📂mapper
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ChartResultToHistoryMapper.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂model
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜StockHistory.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂repository
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜SearchStockRepository.kt
┃ ┃ ┃ ┃       ┃ ┗ 📂usecase
┃ ┃ ┃ ┃       ┃   ┣ 📜SaveCurrentSearchToHistoryUseCase.kt
┃ ┃ ┃ ┃       ┃   ┗ 📜SearchStockHistoryByQuery.kt
┃ ┃ ┃ ┃       ┗ 📂stockchart
┃ ┃ ┃ ┃         ┣ 📂model
┃ ┃ ┃ ┃         ┃ ┣ 📜ChartResult.kt
┃ ┃ ┃ ┃         ┃ ┣ 📜CurrentTradingPeriod.kt
┃ ┃ ┃ ┃         ┃ ┣ 📜Indicators.kt
┃ ┃ ┃ ┃         ┃ ┣ 📜Meta.kt
┃ ┃ ┃ ┃         ┃ ┣ 📜Quote.kt
┃ ┃ ┃ ┃         ┃ ┗ 📜TradingPeriod.kt
┃ ┃ ┃ ┃         ┣ 📂repository
┃ ┃ ┃ ┃         ┃ ┗ 📜StockChartRepository.kt
┃ ┃ ┃ ┃         ┗ 📂usecase
┃ ┃ ┃ ┃           ┗ 📜GetStockChartUseCase.kt
┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂features
┃ ┣ 📂searchstock
┃ ┃ ┣ 📂src
┃ ┃ ┃ ┣ 📂main
┃ ┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃ ┃     ┗ 📂searchstock
┃ ┃ ┃ ┃ ┃       ┣ 📂adapter
┃ ┃ ┃ ┃ ┃       ┃ ┗ 📜SearchStockAdapter.kt
┃ ┃ ┃ ┃ ┃       ┣ 📜SearchStockActivity.kt
┃ ┃ ┃ ┃ ┃       ┗ 📜SearchStockViewModel.kt
┃ ┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┃ ┗ 📂stockchart
┃   ┣ 📂src
┃   ┃ ┣ 📂main
┃   ┃ ┃ ┣ 📂java
┃   ┃ ┃ ┃ ┗ 📂com
┃   ┃ ┃ ┃   ┗ 📂riftar
┃   ┃ ┃ ┃     ┗ 📂stockchart
┃   ┃ ┃ ┃       ┣ 📂chart
┃   ┃ ┃ ┃       ┃ ┣ 📜ChartFormatter.kt
┃   ┃ ┃ ┃       ┃ ┣ 📜ChartHelper.kt
┃   ┃ ┃ ┃       ┃ ┗ 📜CustomMarkerView.kt
┃   ┃ ┃ ┃       ┣ 📜StockChartActivity.kt
┃   ┃ ┃ ┃       ┗ 📜StockChartViewModel.kt
┃   ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂screenshot
┣ 📜endpoint_reference.txt
┣ 📜gradle.properties
┣ 📜gradlew
┣ 📜gradlew.bat
┣ 📜README.md
┗ 📜settings.gradle.kts
```
</details>

## Authors

- [@Riftar](https://www.github.com/riftar)
