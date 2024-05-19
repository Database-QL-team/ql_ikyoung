package ikyoung_ql_crawling;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.*;
import java.util.*;

public class Main {

	// 솔브닥 이화여대 User를 크롤링하고, User Table에 insert
	static void crawlSchool() {
		int school_id = 352; // EWHA
		int rank_ingroup = 0; // rating 기준으로 셈

		for (int page = 1; page < 10; page++) {
			try {
				Thread.sleep(1000); // API 요청 사이의 딜레이 주기
				String path = "https://solved.ac/api/v3/ranking/in_organization?organizationId=" + school_id + "&page="
						+ page;
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();

				int responseCode = conn.getResponseCode();
				if (responseCode != 200) {
					System.out.println(responseCode);
					break;
				}

				Document doc = Jsoup.connect(path).ignoreContentType(true).get();
				String jsonString = doc.text();
				JSONObject jsonObject = new JSONObject(jsonString);
				JSONArray itemsArray = jsonObject.getJSONArray("items");

				// rating 순으로 정렬하기 위해 List로 변환
				List<JSONObject> itemsList = new ArrayList<>();
				for (int i = 0; i < itemsArray.length(); i++) {
					itemsList.add(itemsArray.getJSONObject(i));
				}

				// Rating 기준으로 내림차순 정렬 -> rank_ingroup을 구할 용도
				itemsList.sort((a, b) -> {
					try {
						return Integer.compare(b.getInt("rating"), a.getInt("rating"));
					} catch (JSONException e) {
						e.printStackTrace();
						return 0;
					}
				});
				
				for (int i = 0; i < itemsList.size(); i++) {
				    JSONObject item = itemsList.get(i);
				    String handle = item.getString("handle");
				    int solvednum = item.getInt("solvedCount");
				    int tier = item.getInt("tier");
				    // int rating = item.getInt("rating"); //rating은 저장하지 않는다.
				    ++rank_ingroup;

				    // 확인용 출력
				    System.out.println("Handle: " + handle);
				    String userlink = "https://solved.ac/profile/" + handle; // userlink 만들기
				    System.out.println("Userlink: " + userlink);
				    System.out.println("Solvednum: " + solvednum);
				    System.out.println("Tier: " + tier);
				    System.out.println("rank_ingroup: " + rank_ingroup);

				    // Insert into database
				    ConnectMySQL.insertValuesUser(handle, userlink, solvednum, tier, rank_ingroup);
				}

				// Close the connection
				conn.disconnect();
			} catch (IOException | InterruptedException | JSONException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {

		String dbid = "DB2024Team01"; 
		String userid = "root"; //DB2024Team01 로 변경해야 함
		String passwd = "root"; //DB2024Team01 로 변경해야 함

		ConnectMySQL.mySQLConnect(dbid, userid, passwd);

		crawlSchool(); // 크롤링, Insert

	}
}
