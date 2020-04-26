package java.com.kwazarart.app.model;

public class JdbcProperties {
    private static final String URL = "jdbc:mysql://localhost:3306/DEVSKILLSPEC";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    private static final String TABLE_SKILLS= "skills";
    private static final String TABLE_SPECIALTIES= "specialties";
    private static final String TABLE_DEVELOPERS= "developers";

    public static String getURL() {
        return URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getTableSkills() {
        return TABLE_SKILLS;
    }

    public static String getTableSpecialties() {
        return TABLE_SPECIALTIES;
    }

    public static String getTableDevelopers() {
        return TABLE_DEVELOPERS;
    }
}
