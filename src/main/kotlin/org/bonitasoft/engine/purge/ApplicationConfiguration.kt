package org.bonitasoft.engine.purge

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import java.sql.Connection.TRANSACTION_READ_COMMITTED
import javax.sql.DataSource

@Configuration
@ComponentScan
@EnableScheduling
open class ApplicationConfiguration {

    private val logger = LoggerFactory.getLogger(Application::class.java)

    @Bean
    open fun someBean(datasource: DataSource): Database {
        val connect = Database.connect(datasource, setupConnection = { connection -> connection.transactionIsolation = TRANSACTION_READ_COMMITTED })
        TransactionManager.manager.defaultIsolationLevel = TRANSACTION_READ_COMMITTED
        logger.info("Using datasource with ${datasource.javaClass.simpleName}")
        return connect
    }

}