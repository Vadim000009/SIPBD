package com.example.web.app.dao;

import com.example.web.app.dao.model.ModelHoody;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Design implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());

    private int ID = 1;
    private int MAXID = 0;
    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";    // поменять
    static final String USER = "USER_Design";    // поменть
    static final String PASS = "dfsa7NHIsadf9";  // поменять

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
    /*
    * Если более 5 толстовок имеется, то необходимо назначить собрание на четверг и закидывать к тому списку толстовки
    * */

    /*  Выдача модели по id */
    public ModelHoody selectModelById(int id) {
        if (id == 0) { ID--; id = ID;
            if (ID == 0) { ID = 1; id = 1;}
        } else if (id == 1) { ID++; id = ID;
        } else if (id == -1) { id = 1; ID = 1; }
        if (ID == 0) { ID = 1; id = 1; }
        String query = "select * from modelhoody where id = " + id;
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            resultSet.next();
            ModelHoody mh = new ModelHoody();
            mh.setId(resultSet.getInt("id"));
            mh.setName_hoody(resultSet.getString("name_hoody"));
            mh.setIs_tested(resultSet.getInt("is_tested"));
            mh.setIs_allowed(resultSet.getBoolean("is_allowed"));
            int human_id = resultSet.getInt("human_id");
            Main main = new Main();
            String NSP = main.getNSP(human_id);
            mh.setNSP(NSP);
            mh.setUrlmaket(resultSet.getString("urlmaket"));
            mh.setMaterial(resultSet.getString("material"));
            mh.setPhoto(resultSet.getString("photo"));
            return mh;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выбора толстовки!\n", ex);
            return new ModelHoody();
        }
    }

    /*  Создание новой толстовки */
    public Boolean createNewModel(String login,ModelHoody mh) {
        String name_hoody = mh.getName_hoody(), urlmaket = mh.getUrlmaket(), material = mh.getMaterial(), photo = mh.getPhoto();
        Main main = new Main();
        int id = main.getIdLoginFromDB(login);
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO modelhoody (name_hoody, is_tested, is_allowed, human_id, urlmaket, material, photo) VALUES ('")
                .append(name_hoody).append("',").append(0).append(",").append(false).append(",").append(id).append(",'")
                .append(urlmaket).append("','").append(material).append("','").append(photo).append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            Tester test = new Tester();
            test.createNewModelToTest(id, urlmaket, material);
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка создания толстовки!\n", ex);
            return false;
        }
    }

    public Boolean changeMaket(int id, String urlMaket) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO modelhoody (is_tested, urlmaket) VALUES (")
                .append(0).append(",'").append(urlMaket).append("' where id=").append(id).append(");");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка изменения макета толстовки!\n", ex);
            return false;
        }
    }
}
