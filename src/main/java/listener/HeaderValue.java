package listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;


public class HeaderValue {

	public static Float Total;

	public static Map<String, String> authentication ()
	{
		Map<String, String> Headers = new HashMap<>();
		Headers.put("content-type", "application/json");
		Headers.put("Connection", "keep-alive");
		Headers.put("Authorization", "c06d06da9666a219db15cf575aff28241648128226");
		return Headers;
	}

	public static void createFolder()
	{
		File Directory = new File ("JSON FILE OUTPUT");

		if (Directory.exists())
		{
			System.out.println(Directory.exists());
		}
		else
		{
			if (Directory.mkdir())
			{
				System.out.println("Directory Created");
			}
		}

	}

	public static void Response(String path, String response) throws IOException
	{

		FileWriter file = new FileWriter(path);

		file.write(response);

		file.flush();

		file.close();
	}
	public static void ReplaceRequest(String path, String request) throws IOException
	{

		FileWriter file = new FileWriter(path);

		file.write(request);

		file.flush();

		file.close();
	}
	public static String gson(Object JSONObject)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(JSONObject);

	}

	public static JSONObject parser(String path) throws IOException, ParseException
	{
		JSONParser jsonParser = new JSONParser();

		FileReader reader  = new FileReader(path);

		Object obj = jsonParser.parse(reader);

		return (JSONObject) obj;

	}

	public static JSONObject replaceValue(String path) throws IOException, ParseException
	{
		FileReader readerPrice = new FileReader(path);

		JSONParser jsonParserPrice = new JSONParser();

		Object jsonString = jsonParserPrice.parse(readerPrice);

		return (JSONObject) jsonString;
	}

	public static JsonNode readingResponse(String Path) throws IOException, ParseException
	{
		JSONObject requestObject = HeaderValue.parser(Path);

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readTree(String.valueOf(requestObject));
	}

	public static Float roundingValue (Float amount, Float hST)
	{
		    double totalAmount = amount + hST;

			BigDecimal bd = new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP);

	        return Total = (float) bd.doubleValue();

	}
}






