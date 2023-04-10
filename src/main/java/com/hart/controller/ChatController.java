package com.hart.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
@Log4j2
@RequestMapping("/chat")
@Controller
public class ChatController {
	
	@GetMapping("/s")
	public String testChat() {
		
		return "chat";
	}
	
	@PostMapping("/")
	public String main(String voiceMessage) {
		// voiceMessage라는 문자열 매개변수 ,사용자가 보내려는 메시지 내용이 포함되어 있을 것으로 추정됩니다.

		String chatbotMessage = "";
		log.info(voiceMessage+"voiceMessage");

		String secretKey = "bkdVdGZTVERtTHdKd3ZFSWJQdVZyTU9IaGVVaVpDUFk=";
		String apiUrl = "https://keepk4k7pu.apigw.ntruss.com/custom/v1/10080/6113693b24230d2699b8f0cacd34281fa09355ef64fccf83c71d0bec7dcb3a1b";

		try {



			URL url = new URL(apiUrl);

			String message = getReqMessage(voiceMessage);
			System.out.println("message##" + message);

			String encodeBase64String = makeSignature(message, secretKey);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json;UTF-8");
			con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

			// post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(message.getBytes("UTF-8"));
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();

			BufferedReader br;

			if (responseCode == 200) { // Normal call
				System.out.println(con.getResponseMessage());

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String decodedString;
				while ((decodedString = in.readLine()) != null) {
					chatbotMessage = decodedString;
				}
				// chatbotMessage = decodedString;
				in.close();

			} else { // Error occurred
				chatbotMessage = con.getResponseMessage();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return chatbotMessage;
	}

	public static String makeSignature(String message, String secretKey) {

		String encodeBase64String = "";

		try {
			byte[] secrete_key_bytes = secretKey.getBytes("UTF-8");
<<<<<<< HEAD
			//비밀 키 문자열은 UTF-8 문자 인코딩을 사용하여 바이트 배열로 변환됩니다.
			SecretKeySpec signingKey = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
			//HmacSHA256" 알고리즘을 사용하여 새로운 Mac 개체가 생성되고 SecretKeySpec 개체로 초기화됩니다.메시지 문자열은 UTF-8 문자 인코딩을 사용하여 바이트 배열로 변환됩니다
			Mac mac = Mac.getInstance("HmacSHA256");
			//Mac 개체는 비밀 키를 사용하여 메시지 바이트 배열의 HMAC-SHA256 해시를 계산하고 이를 원시 바이트 배열로 반환합니다.
			mac.init(signingKey);
			
			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			encodeBase64String = Base64.encodeBase64String(rawHmac);
			//원시 바이트 배열은 Base64.NO_WRAP 플래그와 함께 Base64 클래스의 encodeToString() 메서드를 사용하여 Base64 문자열로 인코딩됩니다. 
			//즉, 출력에 줄바꿈이 추가되지 않습니다.

			return encodeBase64String;

		} catch (Exception e) {
			System.out.println(e);
		}

		return encodeBase64String;

	}

	public static String getReqMessage(String voiceMessage) {
<<<<<<< HEAD
		// voiceMessage라는 문자열 매개변수를 사용합니다. 여기에는 사용자가 보내려는 메시지 내용이 포함되어 있을 것으로 추정됩니다
		String requestBody = "";// requestBody는 빈 문자열로 초기화되며 메시지 요청을 나타내는 최종 JSON 문자열을 저장하는 데 사용

		try {

			JSONObject obj = new JSONObject();
			// 메시지 요청의 다양한 요소를 보유하게 될 새로운 JSONObject 인스턴스가 생성
			long timestamp = new Date().getTime();
			// timestamp 변수는 새로운 Date 개체의 getTime() 메서드를 사용하여 현재 시간(밀리초)으로 초기화됩니다. 이 타임스탬프는
			// 메시지 요청에 포함
			System.out.println("##timestamp" + timestamp);

			obj.put("version", "v2");
			obj.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");// 유니크값 생성
			// version", "userId", "timestamp" 및 "event" 요소가 각각의 값과 함께 JSON 개체에 추가됩니다.
			obj.put("timestamp", timestamp);

			JSONObject bubbles_obj = new JSONObject();
			// 실제 메시지 내용을 나타내는 bubbles_obj라는 새 JSONObject 인스턴스가 생성
			bubbles_obj.put("type", "text");
			// 이 개체에는 값이 "text"인 "type" 요소가 있으며 이는 이것이 텍스트 메시지임을 나타냅니다.
			JSONObject data_obj = new JSONObject();
			data_obj.put("description", voiceMessage);
			/*
			 * 'data_obj'라는 또 다른 'JSONObject' 인스턴스가 생성되어 메시지의 실제 텍스트 콘텐츠를 포함합니다.
			 * voiceMessage 매개변수는 "description" 요소의 값으로 포함됩니다. data_obj는 bubbles_obj의 "데이터"
			 * 요소에 대한 값으로 추가됩니다
			 */

			bubbles_obj.put("type", "text");
			bubbles_obj.put("data", data_obj);

			JSONArray bubbles_array = new JSONArray();
			bubbles_array.put(bubbles_obj);

			obj.put("bubbles", bubbles_array);
			obj.put("event", "send");

			requestBody = obj.toString();

		} catch (Exception e) {
			System.out.println("## Exception : " + e);
		}

		return requestBody;

	}

}
