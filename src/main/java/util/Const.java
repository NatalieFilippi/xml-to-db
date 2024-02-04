package util;

public class Const {
    public final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    public final static String USERNAME = "admin";
    public final static String PASSWORD = "admin";
    public final static int BATCH_SIZE = 1000;
    public static String SQL_INSERT = "insert into tracks (dugf, hhrs, lcount, btrfsnum, pod_id, pod_tssnum) values (?,?,?,?,?,?)";
}
