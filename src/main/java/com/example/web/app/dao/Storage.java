package com.example.web.app.dao;


import com.example.web.app.api.request.PasswordGenerator;
import com.example.web.app.dao.model.Stock;
import com.example.web.app.dao.model.SupplyMaterial;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Storage implements InitializingBean {

    private Logger log = Logger.getLogger(getClass().getName());

    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";
    static final String USER = "USER_Storage"; // поменять
    static final String PASS = "asd78f3qkjhna";  // поменять

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    /*  Инициализация */
    public void initDb() {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
                System.out.println("Подключение "+ USER + " прошло успешно!");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База для " + USER + "не подключена", ex);
            ex.printStackTrace();
        }
    }

    /*Добавление материалов на склад*/
    //insert into stock (location, quantity, material) values ('Улица пупкина', 20, '("Рога и Рога", "Флис")');
    public Boolean createNewMaterialOnStock(Stock stock) {
        String location = stock.getLocation(), name = stock.getName(), who_sell = stock.getWho_sell();
        int quantity = stock.getQuantity();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO stock (location, quantity, material) values ('")
                .append(location).append("',").append(quantity).append(",'(\"").append(who_sell).append("\", \"")
                .append(name).append("\")');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выполнения запроса. Вставка в бд. Причина: ", ex);
            return false;
        }
    }

    /* Отправка вещей в цех*/
    //insert into supplymaterial (stock_id, workshop_id, quantity_supply, date_supply, material_supply) values (1, 1, '{10, 20, 30}', '12-12-2020', '{"Флис", "Велсофт", "Футер"}');
    public Boolean sendSupplyToWorkshop(SupplyMaterial sm) {
        String quantity = sm.getQuantity_supply();
        int ws_id = sm.getWorkshop_id(), s_id = sm.getStock_id();
        Date date_supply = sm.getDate_supply();
        String ms = sm.getMaterial_supply();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO supplymaterial (stock_id, workshop_id, quantity_supply, date_supply, material_supply) VALUES (")
                .append(s_id).append(" ,").append(ws_id).append(" , '").append(quantity).append("', '").append(date_supply)
                .append("', '").append(ms).append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выполнения запроса. Вставка в бд. Причина: ", ex);
            return false;
        }
    }
}
