package com.example.web.app.dao;

import com.example.web.app.api.request.ByDateRequest;
import com.example.web.app.api.request.ByIdRequest;
import com.example.web.app.dao.model.Production;
import com.example.web.app.dao.model.Workshop;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WorkshopWork implements InitializingBean {

    private Logger log = Logger.getLogger(getClass().getName());

    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";
    static final String USER = "USER_Workshop"; // поменять
    static final String PASS = "asfd36ygsa8";  // поменять

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
        } catch (ClassNotFoundException |
                SQLException ex) {
            log.log(Level.WARNING, "База для " + USER + "не подключена", ex);
            ex.printStackTrace();
        }
    }

    public Workshop getInfoAboutWorkshop(String login) {
        Main main = new Main();
        int id = main.getIdLoginFromDB(login);
        String query = "SELECT * FROM workshop WHERE workshopboss =" + id;
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(query);
            rs.next();
            Workshop ws = new Workshop();
            ws.setId(rs.getInt("id"));
            ws.setLocation(rs.getString("location"));
            ws.setNSP(main.getNSP(rs.getInt("workshopboss")));
            return ws;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос. Узнать, кто босс сия цеха");
            return new Workshop();
        }
    }

    /* Получить данные о поставке, а именно - колчестве поставок на сегодня */
    public ByDateRequest selectByDate(int workshop_id) {
        StringBuilder query = new StringBuilder();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
        query.append("select COUNT(id) from supplymaterial where date_supply='").append(timeStamp).append("' AND workshop_id='")
                .append(workshop_id).append("';");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(String.valueOf(query));
            rs.next();
            ByDateRequest bdr = new ByDateRequest();
            bdr.setHowManySupply(rs.getInt("count"));
            return bdr;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос. Узнать, сколько сегодня поставок");
            return new ByDateRequest();
        }
    }

    public Boolean sendProduct(Production production) {
        int stock_id = production.getStock_id(), modelhoody_id = production.getModelhoody_id(), workshop_id = production.getWorkshop_id(), quantity_supply = production.getQuantity_supply();
        Date today = new Date();
        String date = today.toString(), reviem = production.getReviem();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYYY");
        String todayDate = sdf.format(date);
        StringBuilder query = new StringBuilder();
        query.append("insert into production (stock_id, modelhoody_id, workshop_id, quantity_supply, date_production, reviem) values (")
                .append(stock_id).append(", ").append(modelhoody_id).append(", ").append(workshop_id).append(", ")
                .append(quantity_supply).append(", '").append(todayDate).append("', '").append(reviem).append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(String.valueOf(query));
            rs.next();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос. Отправка продукции");
            return false;
        }
    }

    /*
    * Начальник цеха получает свой цех
    * Начальник цеха отправляет говно на склад
    * Узнаёт, когда придут материалы
    * insert into workshop (location, workshopboss, humans) values
('Улица пуськина', 1, '{"2","3","4","5"}');
    * */
}
