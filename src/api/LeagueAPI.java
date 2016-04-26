package api;

import org.json.JSONException;
import org.json.JSONObject;

import connection.ConnectionTask;

public class LeagueAPI {
    protected static String api_key = null;
    protected static String region = "na";
    protected static String domain = "https://" + region + ".api.pvp.net";

    public Champion champion;
    public Item item;
    public StaticData staticData;

    private static LeagueAPI API = new LeagueAPI("7f057410-5d0b-4174-b2e4-ad93f77c18d6", "NA");

    private LeagueAPI(String _api_key, String _region) {
        api_key = _api_key;
        region = _region;
        champion = new Champion();
        item = new Item();
        staticData = new StaticData();
    }

    private LeagueAPI() {
        champion = new Champion();
        item = new Item();
        staticData = new StaticData();
    }

    public static LeagueAPI getInstance() {
        return API;
    }

    public void setRegion(String _region) {
        region = _region;
        // update domain
        domain = "https://" + region + ".api.pvp.net";
    }

    public void setKey(String _api_key) {
        api_key = "api_key=" + _api_key;
    }

    private JSONObject getJsonFromUrl(final String url) {
        ConnectionTask task = null;
        try {
            task = new ConnectionTask(url);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return task.data;
    }

    public class Champion {
        private String api_url = "/api/lol/na/v1.2/champion?";

        public JSONObject getChampions(boolean... free_to_play) throws JSONException {
            api_url = "/api/lol/na/v1.2/champion?api_key=";
            String url = domain + api_url + api_key;
            if (free_to_play.length > 1) {
                throw new IllegalArgumentException("too many arguments supplied to Champion.getChampions()");
            } else if (free_to_play.length == 1) {
                if (free_to_play[0] == true)
                    url += "&freeToPlay=true";
                else
                    url += "&freeToPlay=false";
            }

            return getJsonFromUrl(url);
        }

        public JSONObject getChampionById(Integer id) throws JSONException {
            api_url = "/api/lol/na/v1.2/champion/" + id.toString() + "?";
            String url = domain + api_url + api_key;

            return getJsonFromUrl(url);
        }
    }

    public class Item {
        private String api_url = "/api/lol/static-data/" + region + "/v1.2/item?";

        // optionals: locale, version, itemListData
        public JSONObject getItems(Object... optionals) throws JSONException {
            api_url = "/api/lol/static-data/" + region.toLowerCase() + "/v1.2/item?api_key=";
            String locale = "en_US";
            String version = "6.1.1";
            String itemListData = "all";

            String url = "https://global.api.pvp.net" + api_url + api_key;
            if (optionals.length > 3) {
                throw new IllegalArgumentException("too many arguments");
            } else {
                if (optionals.length > 0) {

                    if (!(optionals[0] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    locale = (String) optionals[0];
                }
                if (optionals.length > 1) {

                    if (!(optionals[1] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    version = (String) optionals[1];
                }
                if (optionals.length > 2) {

                    if (!(optionals[2] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    itemListData = (String) optionals[2];
                }
            }

            url += "&locale=" + locale + "&version=" + version + "&itemListData=" + itemListData;

            return getJsonFromUrl(url);
        }

        public JSONObject getItemById(Integer id, Object... optionals) throws JSONException {
            api_url = "/api/lol/static-data/" + region + "/v1.2/item/" + id.toString() + "?api_key=";
            String locale = "en_US";
            String version = "5.24.2";
            String itemData = "all";

            String url = domain + api_url + api_key;
            if (optionals.length > 4) {
                throw new IllegalArgumentException("too many arguments");
            } else {
                if (optionals.length > 0) {

                    if (!(optionals[0] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    locale = (String) optionals[0];
                }
                if (optionals.length > 1) {

                    if (!(optionals[1] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    version = (String) optionals[1];
                }
                if (optionals.length > 2) {

                    if (!(optionals[2] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    itemData = (String) optionals[2];
                }
            }

            url += "&locale=" + locale + "&version=" + version + "&itemData=" + itemData;

            return getJsonFromUrl(url);
        }
    }

    public class StaticData {
        private String api_url = "/api/lol/static-data/" + region + "/v1.2/champion?";

        // optionals: locale, version, dataById, ChampData
        public JSONObject getChampions(Object... optionals) throws JSONException {
            api_url = "/api/lol/static-data/" + region.toLowerCase() + "/v1.2/champion?api_key=";
            String locale = "en_US";
            String version = "5.24.2";
            Boolean dataById = false;
            String ChampData = "all"; // needs to be dict

            String url = "https://global.api.pvp.net" + api_url + api_key;
            if (optionals.length > 4) {
                throw new IllegalArgumentException("too many arguments");
            } else {
                if (optionals.length > 0) {

                    if (!(optionals[0] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    locale = (String) optionals[0];
                }
                if (optionals.length > 1) {

                    if (!(optionals[1] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    version = (String) optionals[1];
                }
                if (optionals.length > 2) {

                    if (!(optionals[2] instanceof Boolean)) {
                        throw new IllegalArgumentException("...");
                    }
                    dataById = (Boolean) optionals[2];
                }
                if (optionals.length > 3) {

                    if (!(optionals[3] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    ChampData = (String) optionals[3];
                }
            }

            url += "&locale=" + locale + "&version=" + version + "&dataById=" + dataById.toString() + "&champData="
                    + ChampData;

            return getJsonFromUrl(url);
        }

        public JSONObject getChampionById(Integer id, Object... optionals) throws JSONException {
            api_url = "/api/lol/static-data/" + region.toLowerCase() + "/v1.2/champion/" + id.toString() + "?api_key=";
            String locale = "en_US";
            String version = "5.24.2";
            String ChampData = "all"; // needs to be dict

            String url = "https://global.api.pvp.net" + api_url + api_key;
            if (optionals.length > 4) {
                throw new IllegalArgumentException("too many arguments");
            } else {
                if (optionals.length > 0) {

                    if (!(optionals[0] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    locale = (String) optionals[0];
                }
                if (optionals.length > 1) {

                    if (!(optionals[1] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    version = (String) optionals[1];
                }
                if (optionals.length > 2) {

                    if (!(optionals[2] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    ChampData = (String) optionals[2];
                }
            }

            url += "&locale=" + locale + "&version=" + version + "&champData=" + ChampData;

            return getJsonFromUrl(url);
        }

        public JSONObject getMasteries(Object... optionals) throws JSONException {
            api_url = "/api/lol/static-data/" + region.toLowerCase() + "/v1.2/mastery?api_key=";
            String locale = "en_US";
            String version = "5.24.2";
            String masteryListData = "all";

            String url = "https://global.api.pvp.net" + api_url + api_key;
            if (optionals.length > 3) {
                throw new IllegalArgumentException("too many arguments");
            } else {
                if (optionals.length > 0) {

                    if (!(optionals[0] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    locale = (String) optionals[0];
                }
                if (optionals.length > 1) {

                    if (!(optionals[1] instanceof String)) {
                        throw new IllegalArgumentException("...");
                    }
                    version = (String) optionals[1];
                }
                if (optionals.length > 2) {

                    if (!(optionals[2] instanceof Boolean)) {
                        throw new IllegalArgumentException("...");
                    }
                    masteryListData = (String) optionals[2];
                }
            }

            url += "&locale=" + locale + "&version=" + version + "&masteryListData=" + masteryListData;

            return getJsonFromUrl(url);
        }
    }

}
