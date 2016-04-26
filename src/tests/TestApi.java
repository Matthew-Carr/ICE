package tests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.Test;

import api.*;
import org.json.JSONObject;

public class TestApi {

    @Test
    public void test() throws JSONException {
        LeagueAPI Lol_api = LeagueAPI.getInstance();
        assertTrue(Lol_api.champion.getChampions(false) instanceof JSONObject);
        /*assertNotNull(Champion.getChampions(true));
		assertNotNull(Champion.getChampions());
		assertNotNull(Champion.getChampionById(266));
		
		assertNotNull(StaticData.getChampions("en_US", "5.24.2", true, "passive"));
		assertNotNull(StaticData.getChampionById(266, "en_US", "5.24.2", "blurb"));
		
		assertNotNull(Item.getItems());
		assertNotNull(Item.getItemById(3082, "en_US", "5.24.2", "all"));
		 */
    }

}
