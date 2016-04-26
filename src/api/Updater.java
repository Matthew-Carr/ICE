package api;

import connection.ConnectionTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by MC046006 on 4/26/2016.
 */
public class Updater {
    public static void update() throws IOException {
        LeagueAPI Lol_api = LeagueAPI.getInstance();
        try {
            String data_dragon_version = new String(Files.readAllBytes(Paths.get("data_dragon_version.txt")));
            data_dragon_version = data_dragon_version.trim();
            ConnectionTask versionTask = new ConnectionTask(
                    "https://global.api.pvp.net/api/lol/static-data/na/v1.2/versions?api_key=7f057410-5d0b-4174-b2e4-ad93f77c18d6");
            String raw_data = versionTask.raw_data;
            String trimmed = raw_data.replace("[", "").replace("]", "");
            ArrayList<String> jarr = new ArrayList<String>(Arrays.asList(trimmed.split(",")));

            if (!data_dragon_version.equals(jarr.get(0))) { // get updated data
                PrintWriter writer = new PrintWriter("data_dragon_version.txt", "UTF-8");
                writer.println(jarr.get(0));
                writer.close();

                System.out.println("retrieving updated data");
                System.out.println(System.getProperty("user.dir"));
                writer = new PrintWriter("Champion_getChampions.txt", "UTF-8");
                writer.println(Lol_api.champion.getChampions(false));
                writer.close();

                writer = new PrintWriter("Item_getItems.txt", "UTF-8");
                writer.println(Lol_api.item.getItems());
                writer.close();

                writer = new PrintWriter("StaticData_getChampions.txt", "UTF-8");
                writer.println(Lol_api.staticData.getChampions("en_US", "6.1.1"));
                writer.close();

                writer = new PrintWriter("StaticData_getMasteries.txt", "UTF-8");
                writer.println(Lol_api.staticData.getMasteries());
                writer.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
