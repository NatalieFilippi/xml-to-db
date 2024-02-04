import lombok.extern.slf4j.Slf4j;
import model.Track;
import model.Tracks;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.sql.*;

import static util.Const.*;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Copy();
    }

    private static void Copy() {

        DataSource db = getDb();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStreamReader reader = new InputStreamReader(Main.class.getResourceAsStream("/out.xml"));
        XMLStreamReader streamReader = null;
        try {
            streamReader = inputFactory.createXMLStreamReader(reader);
            streamReader.nextTag();
            JAXBContext context = JAXBContext.newInstance(Tracks.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            try (Connection connection = db.getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
                int rowCount = 0; //счётчик строк
                while (streamReader.nextTag() == XMLStreamConstants.START_ELEMENT) {
                    try {
                        Track track = (Track) unmarshaller.unmarshal(streamReader);

                        preparedStatement.setLong(1, track.getDugf());
                        preparedStatement.setLong(2, track.getHhrs());
                        preparedStatement.setLong(3, track.getLcount());
                        preparedStatement.setLong(4, track.getBtrfsnum());
                        preparedStatement.setLong(5, track.getPod().getId());
                        preparedStatement.setDate(6, Date.valueOf(track.getPod().getTssnum()));

                        preparedStatement.addBatch();
                        if (++rowCount % BATCH_SIZE == 0) {
                            preparedStatement.executeBatch();
                            connection.commit();
                            preparedStatement.clearBatch();
                        }
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                preparedStatement.executeBatch();
                connection.commit();
                preparedStatement.clearBatch();
                log.info("Missing complete!");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (XMLStreamException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private static DataSource getDb() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName("org.postgresql.Driver");
        builder.url(URL);
        builder.username(USERNAME);
        builder.password(PASSWORD);
        return builder.build();
    }
}