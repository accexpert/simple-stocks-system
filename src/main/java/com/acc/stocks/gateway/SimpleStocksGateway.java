package com.acc.stocks.gateway;

import com.acc.stocks.calculations.TradeCalculationsHandler;
import com.acc.stocks.events.EventHandler;
import com.acc.stocks.events.IEventHandler;
import com.acc.stocks.models.StockModel;
import com.acc.stocks.repositories.IDataRepository;
import com.acc.stocks.repositories.StockDataRepository;
import com.acc.stocks.repositories.TradeDataRepository;
import com.acc.stocks.services.clients.*;
import org.apache.log4j.Logger;

public class SimpleStocksGateway {
    private static final Logger LOGGER = Logger.getLogger(SimpleStocksGateway.class);


    public SimpleStocksGateway() {
        LOGGER.info(this.getClass().getSimpleName()+" created.");
    }

    public void start() {
        IDataRepository<StockModel> dataRepository = new StockDataRepository();
        IEventHandler eventHandler = new EventHandler();
        IWriterHandler writerHandler = new ConsoleWriterService();
        new Thread(new GuiService(writerHandler, eventHandler)).start();
        new Thread(new InputService(writerHandler, eventHandler)).start();
        new Thread(new TradeService(writerHandler, eventHandler, new TradeDataRepository(), new StockDataRepository(), new TradeCalculationsHandler())).start();
        LOGGER.info("Simple stock app started");
    }
}
