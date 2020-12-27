package com.example.web.app.dao;

import com.example.web.app.api.request.ByNSPRequest;
import com.example.web.app.api.request.PasswordGenerator;
import com.example.web.app.dao.model.Human;
import com.example.web.app.dao.model.Rebuke;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Main implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());

    @Autowired
    private JavaMailSender javaMailSender;

    private int ID = 1;
    private int MAXID = 0;
    static final String dbPath = "jdbc:postgresql://192.168.56.101:5432/hornsandhooves";
    static final String USER = "USER_Employee";
    static final String PASS = "usaf57sa87sa";

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

    /*  Выполнение любого запроса. Закладка. */
    public Boolean execute(String query) {
        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    /*  Выдача сотрудника по id */
    public Human selectHumanById(int id) {
        getMaxId();
        if (id == 0) { ID--; id = ID;
            if (ID == 0) { ID = 1; id = 1; }
        } else if (id == 1) { ID++; id = ID;
        } else if (id == -1) { id = 1; ID = 1; }
        if (ID == 0) { ID = 1; id = 1; }
        if (id >= MAXID) { id = MAXID; ID = MAXID; }
        String query = "select * from human where id = " + id;
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            resultSet.next();
            Human human = new Human();
            human.setId(resultSet.getInt("id"));
            human.setName(resultSet.getString("name"));
            human.setSurname(resultSet.getString("surname"));
            human.setPartition(resultSet.getString("partition"));
            human.setAge(resultSet.getDate("age"));
            human.setGender(resultSet.getBoolean("gender"));
            human.setIs_working(resultSet.getInt("is_working"));
            human.setPhone(resultSet.getString("phone"));
            human.setLogin(resultSet.getString("login"));
            human.setPassword(resultSet.getString("password"));
            human.setPhoto(resultSet.getString("photo"));
            return human;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос. Создание Человека");
            return new Human();
        }
    }

    /*  Создание нового сотрудника */
    public Boolean createNewHuman(Human human) {
        getMaxId();
        String name = human.getName(), surname = human.getSurname(), patrition = human.getPartition(), phone = human.getPhone(), email = human.getEmail();
        Boolean gender = human.getGender();
        Date age = human.getAge();
        String login = email.replaceFirst("@.\\w+.+","");
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).useLower(true).useUpper(true).build();
        String password = passwordGenerator.generate(8);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println(password);
        String photoBase64 = human.getPhoto();
        StringBuilder query = new StringBuilder();
        sendEmail(email, password);
        query.append("insert into human (name, surname, partition, age, gender, is_working, phone, login, password, email, photo) values ('")
                .append(name).append("','").append(surname).append("','").append(patrition).append("','").append(age)
                .append("','").append(gender).append("','").append(1).append("','").append(phone).append("','")
                .append(login).append("','").append(hashedPassword).append("','").append(email).append("','").append(photoBase64).append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            query.setLength(0);
            query.append("SELECT id FROM human where email='").append(email).append("'");
            try(Connection conn2 = DriverManager.getConnection(dbPath, USER, PASS);
                Statement stat2 = conn2.createStatement()) {
                ResultSet resultSet = stat2.executeQuery(String.valueOf(query));
                resultSet.next();
                int id = resultSet.getInt("id");
                DepartmentSalary dp = new DepartmentSalary();
                dp.createNewDep(id);
            }
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выполнения запроса. Вставка в бд");
            return false;
        }
    }

    /*  Добавление замечанию сотруднику */
    public Boolean createNewRebukeForHuman(Rebuke rebukeForHuman) {
        String rebuke = rebukeForHuman.getRebuke();
        Integer human_id = rebukeForHuman.getHuman_id();
        StringBuilder query = new StringBuilder();
        query.append("insert into rebuke (human_id, rebuke) values ('").append(human_id).append("','").append(rebuke).append("');");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выполнения запроса. Запись замечания");
            return false;
        }
    }

    public Boolean changeWorkability(int id, int is_working) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE human SET is_working =").append(is_working).append(" where id=").append(id).append(";");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS)) {
            PreparedStatement stat = conn.prepareStatement(String.valueOf(query));
            stat.execute();
            return true;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Ошибка выполнения запроса. Изменение трудоспособности");
            return false;
        }
    }

    public String getNSP(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM human WHERE id=").append(id).append(";");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.valueOf(query));
            resultSet.next();
            StringBuilder finalString = new StringBuilder();
            finalString.append(resultSet.getString("surname")).append(" ").append(resultSet.getString("name"))
                    .append(" ").append(resultSet.getString("partition"));

            return String.valueOf(finalString);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос");
            return new String();
        }
    }

    public Integer getIdFromDB (ByNSPRequest NSP) {
        String[] words = NSP.getNSP().split(" ");
        StringBuilder query = new StringBuilder();
        query.append("SELECT id FROM human WHERE surname='").append(words[0]).append("' AND name='")
                .append(words[1]).append("'AND partition='").append(words[2]).append("';");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.valueOf(query));
            resultSet.next();
            int id = resultSet.getInt("id");;
            return id;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос");
            return 0;
        }
    }

    public String getLoginFromBD (int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT login FROM human WHERE id=").append(id).append(";");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.valueOf(query));
            resultSet.next();
            String login = resultSet.getString("login");
            return login;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос");
            return new String();
        }
    }

    public Integer getIdLoginFromDB (String login) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT id FROM human WHERE login='").append(login).append("';");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.valueOf(query));
            resultSet.next();
            int id = resultSet.getInt("id");;
            return id;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос");
            return 0;
        }
    }

    public void sendEmail(String emailRegisteredNow, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailRegisteredNow);
        msg.setSubject("Рога и Толстовки");
        msg.setText("Вы внесены успешно в систему пользователей. Логин вам уже известен, пароль: " + password);
        javaMailSender.send(msg);
    }

    public void getMaxId() {
        String query = ("SELECT max(id) FROM human;");
        try (Connection conn = DriverManager.getConnection(dbPath, USER, PASS);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            resultSet.next();
            MAXID = resultSet.getInt("max");;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос");
        }
    }
}