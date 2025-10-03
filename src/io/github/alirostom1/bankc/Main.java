package io.github.alirostom1.bankc;

import java.sql.Connection;

import io.github.alirostom1.bankc.database.DatabaseConnection;
import io.github.alirostom1.bankc.repository.AccountRepositoryImpl;
import io.github.alirostom1.bankc.repository.ClientRepositoryImpl;
import io.github.alirostom1.bankc.repository.TransactionRepositoryImpl;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;
import io.github.alirostom1.bankc.repository.Interface.ClientRepository;
import io.github.alirostom1.bankc.repository.Interface.TransactionRepository;
import io.github.alirostom1.bankc.service.AccountServiceImpl;
import io.github.alirostom1.bankc.service.ClientServiceImpl;
import io.github.alirostom1.bankc.service.ReportServiceImpl;
import io.github.alirostom1.bankc.service.TransactionServiceImpl;
import io.github.alirostom1.bankc.service.Interface.AccountService;
import io.github.alirostom1.bankc.service.Interface.ClientService;
import io.github.alirostom1.bankc.service.Interface.ReportService;
import io.github.alirostom1.bankc.service.Interface.TransactionService;
import io.github.alirostom1.bankc.ui.AnalyticsMenu;
import io.github.alirostom1.bankc.ui.ClientMenu;
import io.github.alirostom1.bankc.ui.MainMenu;
import io.github.alirostom1.bankc.ui.TransactionMenu;

public class Main{
    public final static void main(String[] args){
        Connection connection = DatabaseConnection.getInstance().getConnection();
        ClientRepository clientRepo = new ClientRepositoryImpl(connection);
        AccountRepository accountRepo = new AccountRepositoryImpl(connection);
        TransactionRepository txRepo = new TransactionRepositoryImpl(connection);
        ClientService clientService = new ClientServiceImpl(clientRepo, accountRepo);
        AccountService accountService = new AccountServiceImpl(accountRepo,txRepo);
        TransactionService txService = new TransactionServiceImpl(txRepo, accountRepo);
        ReportService reportService = new ReportServiceImpl(clientRepo, accountRepo, txRepo);
        ClientMenu clientMenu = new ClientMenu(clientService,accountService);
        TransactionMenu txMenu = new TransactionMenu(txService,clientService);
        AnalyticsMenu anMenu = new AnalyticsMenu(reportService,txService);
        MainMenu mainMenu = new MainMenu(clientMenu,txMenu,anMenu);
        mainMenu.run();
    }
}