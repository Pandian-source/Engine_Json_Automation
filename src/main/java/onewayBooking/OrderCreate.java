package onewayBooking;


import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import listener.ConfigFileReader;
import listener.HeaderValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class BookVariable
{
    String GdsBookingReference; String OrderStatus; String ResponseOrderId; String PricingShoppingResponseId;

    String OfferResponseId; String PricingOfferId;  Float TotalAmount; Float HST; Float Total;

    String FareType; String OrgFareType; String SupplierId; String FlightDeparture; String Error;

    String OnFlyMarkup;

}

public class OrderCreate {

    public ConfigFileReader configFileReader = new ConfigFileReader();

    static final Logger logger = LogManager.getLogger(AirShopping.class);

    BookVariable B = new BookVariable();


    public Response onewayBooking(String pricingResponse, String bookingRequest) {

        try {
            B = new BookVariable();

            JsonNode jsonTree = HeaderValue.readingResponse(pricingResponse);

            B.PricingShoppingResponseId = jsonTree.get("OfferPriceRS").get("ShoppingResponseId").asText();

            System.out.println(B.PricingShoppingResponseId);

            logger.info(B.PricingShoppingResponseId);

            B.OfferResponseId = jsonTree.get("OfferPriceRS").get("OfferResponseId").asText();

            System.out.println(B.OfferResponseId);

            logger.info(B.OfferResponseId);

            B.PricingOfferId = jsonTree.get("OfferPriceRS").get("PricedOffer").get(0).get("OfferID").asText();

            System.out.println(B.PricingOfferId);

            logger.info(B.PricingOfferId);

            B.TotalAmount = jsonTree.get("OfferPriceRS").get("PricedOffer").get(0).get("TotalPrice").get("EquivCurrencyPrice").floatValue();

            System.out.println(B.TotalAmount);

            logger.info(B.TotalAmount);

            B.Total = B.TotalAmount;


            JSONObject objectBooking = HeaderValue.replaceValue(bookingRequest);

            JSONObject BookingRq = (JSONObject) objectBooking.get("OrderCreateRQ");

            BookingRq.get("ShoppingResponseId");

            BookingRq.put("ShoppingResponseId", B.PricingShoppingResponseId);

            BookingRq.get("OfferResponseId");

            BookingRq.put("OfferResponseId", B.OfferResponseId);

            JSONObject Query = (JSONObject) BookingRq.get("Query");

            JSONArray jsonArrayBooking = (JSONArray) Query.get("Offer");

            JSONObject OfferIdBooking = (JSONObject) jsonArrayBooking.get(0);

            OfferIdBooking.get("OfferID");

            OfferIdBooking.put("OfferID", B.PricingOfferId);

            JSONObject Payments = (JSONObject) BookingRq.get("Payments");

            JSONArray Payment = (JSONArray) Payments.get("Payment");

            JSONObject paymentArray = (JSONObject) Payment.get(0);

            paymentArray.put("Amount", B.Total);

            String jsonBooking = HeaderValue.gson(objectBooking);

            HeaderValue.ReplaceRequest(bookingRequest, jsonBooking);

            RequestSpecification requestSpecificationBooking = RestAssured.given();

            requestSpecificationBooking = requestSpecificationBooking.log().all();

            requestSpecificationBooking.baseUri(configFileReader.engineUrl());

            requestSpecificationBooking.basePath(configFileReader.airOrderCreate());

            requestSpecificationBooking.body(jsonBooking);

            requestSpecificationBooking.headers(HeaderValue.authentication());

            Response re = requestSpecificationBooking.post();

            System.out.println(re.asPrettyString());

            return re;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Object bookingGetNode(Response response, String jsonPath, String bookingResponse) throws IOException {

        ValidatableResponse validaTableResponseBooking = response.then().contentType(ContentType.JSON);

        validaTableResponseBooking.statusCode(200);

        JsonPath jsonPathEvaluatorBooking = response.jsonPath();

        HeaderValue.Response(bookingResponse, response.asPrettyString());

        return jsonPathEvaluatorBooking.get(jsonPath);
    }

    public boolean responseOrderId(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.ResponseOrderId = (String) bookingGetNode(response, "OrderViewRS.Order[0].OrderID", bookingResponse);

        System.out.println(B.ResponseOrderId);

        logger.info(B.ResponseOrderId);

        return (!B.ResponseOrderId.isEmpty());
    }


    public boolean gdsBookingReference(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.GdsBookingReference = (String) bookingGetNode(response, "OrderViewRS.Order[0].GdsBookingReference", bookingResponse);

        System.out.println(B.GdsBookingReference);

        logger.info(B.GdsBookingReference);

        return (!B.GdsBookingReference.isEmpty() || B.GdsBookingReference.matches("\\w"));
    }

    public boolean orderStatus(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.OrderStatus = (String) bookingGetNode(response, "OrderViewRS.Order[0].OrderStatus", bookingResponse);

        System.out.println(B.OrderStatus);

        logger.info(B.OrderStatus);

        return (!B.OrderStatus.isEmpty() || B.OrderStatus.matches("\\w"));
    }

    public boolean fareType(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.FareType = (String) bookingGetNode(response, "OrderViewRS.Order[0].FareType", bookingResponse);

        System.out.println(B.FareType);

        logger.info(B.FareType);

        return (!B.FareType.isEmpty() || B.FareType.matches("[a-zA-Z]"));
    }

    public boolean orgFareType(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.OrgFareType = (String) bookingGetNode(response, "OrderViewRS.Order[0].OrgFareType", bookingResponse);

        System.out.println(B.OrgFareType);

        logger.info(B.OrgFareType);

        return (!B.OrgFareType.isEmpty() || B.OrgFareType.matches("[a-zA-Z]"));
    }

    public boolean supplierId(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.SupplierId = bookingGetNode(response, "OrderViewRS.Order[0].SupplierId", bookingResponse).toString();

        System.out.println(B.SupplierId);

        logger.info(B.SupplierId);

        return (!B.SupplierId.isEmpty() || B.SupplierId.matches("\\d"));
    }

    public boolean flightDeparture(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        B.FlightDeparture = (String) bookingGetNode(response, "OrderViewRS.Order[0].0_FlightDeparture", bookingResponse);

        System.out.println(B.FlightDeparture);

        logger.info(B.FlightDeparture);

        return true;

    }
    public boolean onFlyMarkup(Response response, String bookingResponse) throws IOException {

        B = new BookVariable();

        ArrayList<String> count = (ArrayList<String>) bookingGetNode (response,"OrderViewRS.DataLists.PassengerList.Passengers",bookingResponse);

        System.out.println(count.size());

        logger.info(count.size());

        for(int i=0; i<=count.size(); i++)
        {
            B.OnFlyMarkup = (String) bookingGetNode(response,"OrderViewRS.DataLists.PassengerList.Passengers["+i+"].OnflyMarkup",bookingResponse);

            System.out.println(B.OnFlyMarkup);

            logger.info(B.OnFlyMarkup);

        }
        return true;
    }


    public boolean errorResults(Response response,String bookingResponse)
    {
        try {
            B = new BookVariable();

            B.Error = (String) bookingGetNode(response, "OrderViewRS.Errors.Error.Value", bookingResponse);

            if(B.Error.matches("Unable to confirm availability for the selected booking class at this moment"))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());

            logger.info(e.getMessage());
        }
        return false;
    }
}








