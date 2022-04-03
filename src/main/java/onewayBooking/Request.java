package onewayBooking;

import listener.ConfigFileReader;
import listener.HeaderValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Request {

    public String fromAirport;
    public String toAirport;
    public String travelDate;
    public String onewayDate;
    public String airline;
    public String departureDate;

    public String returnDate;
    public String departTravelDate;
    public String returnTravelDate;
    public String fromAirportM;
    public String toAirportM;

    public ConfigFileReader configFileReader = new ConfigFileReader();

    static final Logger logger = LogManager.getLogger(AirShopping.class);

    public void oneWayInput(String oldRequest) throws IOException, ParseException
    {
        onewayDetails("Oneway");

        if (travelDate.contains("days"))
        {
           onewayDate = dateFormat(travelDate);
        }
        String search = onewayJson(oldRequest);

        HeaderValue.ReplaceRequest(oldRequest, search);
    }

    public void returnInput(String oldRequest) throws IOException, ParseException
    {
        returnDetails("RoundTrip");

        if ((departureDate.contains("days") || returnDate.contains("days"))) {

            departTravelDate = dateFormat(departureDate);

            returnTravelDate = dateFormat(returnDate);
        }
        String search = returnJson(oldRequest);

        HeaderValue.ReplaceRequest(oldRequest, search);
    }

    public void multiCityInput(String oldRequest) throws IOException, ParseException
    {
         Details("MultiCity");

        if ((departureDate.contains("days") || returnDate.contains("days"))) {

            departTravelDate = dateFormat(departureDate);

            returnTravelDate = dateFormat(returnDate);
        }
        String search = requestJson(oldRequest);

        HeaderValue.ReplaceRequest(oldRequest, search);
    }

    public void openJawInput(String oldRequest) throws IOException, ParseException
    {
        Details("openJaw");

        if ((departureDate.contains("days") || returnDate.contains("days"))) {

            departTravelDate = dateFormat(departureDate);

            returnTravelDate = dateFormat(returnDate);
        }
        String search = requestJson(oldRequest);

        HeaderValue.ReplaceRequest(oldRequest, search);
    }

    public String onewayJson(String oldRequest) throws IOException, ParseException {

        JSONObject objectSearch = HeaderValue.replaceValue(oldRequest);

        JSONObject AirShoppingRQ = (JSONObject) objectSearch.get("AirShoppingRQ");

        JSONObject CoreQuery = (JSONObject) AirShoppingRQ.get("CoreQuery");

        JSONObject OriginDestinations = (JSONObject) CoreQuery.get("OriginDestinations");

        JSONArray jsonArray = (JSONArray) OriginDestinations.get("OriginDestination");

        JSONObject Departure = (JSONObject) jsonArray.get(0);

        JSONObject Orgin = (JSONObject) Departure.get("Departure");

        Orgin.get("Date");Orgin.put("Date", onewayDate);

        Orgin.get("AirportCode");Orgin.put("AirportCode", fromAirport);

        JSONObject Arrival = (JSONObject) Departure.get("Arrival");

        Arrival.get("AirportCode");Arrival.put("AirportCode", toAirport);

        JSONObject Preference = (JSONObject) AirShoppingRQ.get("Preference");

        JSONObject AirlinePreference = (JSONObject) Preference.get("AirlinePreference");

        AirlinePreference.get("PreferedAirlines");AirlinePreference.put("PreferedAirlines", Arrays.asList(airline));

        return HeaderValue.gson(objectSearch);
    }

    public String returnJson(String oldRequest) throws IOException, ParseException
    {
        JSONObject objectSearch = HeaderValue.replaceValue(oldRequest);

        JSONObject AirShoppingRQ = (JSONObject) objectSearch.get("AirShoppingRQ");

        JSONObject CoreQuery = (JSONObject) AirShoppingRQ.get("CoreQuery");

        JSONObject OriginDestinations = (JSONObject) CoreQuery.get("OriginDestinations");

        JSONArray jsonArray = (JSONArray) OriginDestinations.get("OriginDestination");

        JSONObject Departure = (JSONObject) jsonArray.get(0);

        JSONObject Orgin = (JSONObject) Departure.get("Departure");

        Orgin.get("Date");Orgin.put("Date", departTravelDate);

        Orgin.get("AirportCode");Orgin.put("AirportCode", fromAirport);

        JSONObject Arrival = (JSONObject) Departure.get("Arrival");

        Arrival.get("AirportCode");Arrival.put("AirportCode", toAirport);

        JSONObject ReturnArrival = (JSONObject) jsonArray.get(1);

        JSONObject ReturnOrgin = (JSONObject) ReturnArrival.get("Departure");

        ReturnOrgin.get("Date");ReturnOrgin.put("Date", returnTravelDate);

        ReturnOrgin.get("AirportCode");ReturnOrgin.put("AirportCode", toAirport);

        JSONObject ArrivalSector = (JSONObject) ReturnArrival.get("Arrival");

        ArrivalSector.get("AirportCode");ArrivalSector.put("AirportCode", fromAirport);

        JSONObject Preference = (JSONObject) AirShoppingRQ.get("Preference");

        JSONObject AirlinePreference = (JSONObject) Preference.get("AirlinePreference");

        AirlinePreference.get("PreferedAirlines");AirlinePreference.put("PreferedAirlines", Arrays.asList(airline));

        return HeaderValue.gson(objectSearch);
    }

    public String requestJson(String oldRequest) throws IOException, ParseException {

        JSONObject objectSearch = HeaderValue.replaceValue(oldRequest);

        JSONObject AirShoppingRQ = (JSONObject) objectSearch.get("AirShoppingRQ");

        JSONObject CoreQuery = (JSONObject) AirShoppingRQ.get("CoreQuery");

        JSONObject OriginDestinations = (JSONObject) CoreQuery.get("OriginDestinations");

        JSONArray jsonArray = (JSONArray) OriginDestinations.get("OriginDestination");

        JSONObject Departure = (JSONObject) jsonArray.get(0);

        JSONObject Orgin = (JSONObject) Departure.get("Departure");

        Orgin.get("Date");Orgin.put("Date", departTravelDate);

        Orgin.get("AirportCode");Orgin.put("AirportCode", fromAirport);

        JSONObject Arrival = (JSONObject) Departure.get("Arrival");

        Arrival.get("AirportCode");Arrival.put("AirportCode", toAirport);

        JSONObject ReturnArrival = (JSONObject) jsonArray.get(1);

        JSONObject ReturnOrgin = (JSONObject) ReturnArrival.get("Departure");

        ReturnOrgin.get("Date");ReturnOrgin.put("Date", returnTravelDate);

        ReturnOrgin.get("AirportCode");ReturnOrgin.put("AirportCode", fromAirportM);

        JSONObject ArrivalSector = (JSONObject) ReturnArrival.get("Arrival");

        ArrivalSector.get("AirportCode");ArrivalSector.put("AirportCode", toAirportM);

        return HeaderValue.gson(objectSearch);
    }

    public void onewayDetails(String key)
    {
        String[] oneWayDetails = configFileReader.getProperty(key).split(",");

        fromAirport = oneWayDetails[0];toAirport = oneWayDetails[1];travelDate = oneWayDetails[2];airline = oneWayDetails[3];
    }

    public void returnDetails(String key)
    {
        String[] returnTripDetails = configFileReader.getProperty(key).split(",");

        fromAirport = returnTripDetails[0];toAirport = returnTripDetails[1];departureDate = returnTripDetails[2];

        returnDate = returnTripDetails[3];airline = returnTripDetails[4];
    }

    public void Details(String key)
    {
        String[] multiCityDetails = configFileReader.getProperty(key).split(",");

        fromAirport = multiCityDetails[0];toAirport = multiCityDetails[1];fromAirportM = multiCityDetails[2];

        toAirportM = multiCityDetails[3];departureDate = multiCityDetails[4];returnDate = multiCityDetails[5];
    }

    public String dateFormat(String date)
    {

        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DATE, Integer.parseInt(date.trim().replaceAll("days", "")));

            return dateFormat.format(calendar.getTime());

        } catch (Exception e) {

            System.out.println(e.getMessage());

            logger.info(e.getMessage());
        }
        return date;
    }
}


