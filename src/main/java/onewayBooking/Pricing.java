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
import org.json.simple.parser.ParseException;

import java.io.IOException;

class AirPricing
    {
        Object ShoppingResponseIdValue; Object offerId;

        String PricingShoppingResponseId; String OfferResponseId;

        String PricingOfferId; Float BookingCurrencyPrice;

        String Error;
    }

    public class Pricing
    {
           AirPricing P = new AirPricing();

           public ConfigFileReader configFileReader = new ConfigFileReader();

           static final Logger logger = LogManager.getLogger(AirShopping.class);

        public Response pricing(String searchResponse, String pricingRequest) throws IOException, ParseException
        {

            JsonNode jsonTree = HeaderValue.readingResponse(searchResponse);

            P.ShoppingResponseIdValue = jsonTree.get("AirShoppingRS").get("ShoppingResponseId").asText();

            System.out.println(P.ShoppingResponseIdValue);

            logger.info(P.ShoppingResponseIdValue);

            P.offerId = jsonTree.get("AirShoppingRS").get("OffersGroup").get("AirlineOffers").get(0).get("Offer")
                    .get(0).get("OfferID").asText();

            System.out.println(P.offerId);

            logger.info(P.offerId);

            JSONObject objectPrice = HeaderValue.replaceValue(pricingRequest);

            JSONObject offerPriceRq = (JSONObject) objectPrice.get("OfferPriceRQ");

            offerPriceRq.get("ShoppingResponseId");

            offerPriceRq.put("ShoppingResponseId", P.ShoppingResponseIdValue);

            JSONObject query = (JSONObject) offerPriceRq.get("Query");

            JSONArray jsonArray = (JSONArray) query.get("Offer");

            JSONObject offerId = (JSONObject) jsonArray.get(0);

            offerId.get("OfferID");

            offerId.put("OfferID", P.offerId);

            String jsonPrice = HeaderValue.gson(objectPrice);

            HeaderValue.ReplaceRequest(pricingRequest, jsonPrice);

            RequestSpecification requestSpecificationPricing = RestAssured.given();

            requestSpecificationPricing = requestSpecificationPricing.log().all();

            requestSpecificationPricing.baseUri(configFileReader.engineUrl());

            requestSpecificationPricing.basePath(configFileReader.airOfferPrice());

            requestSpecificationPricing.body(jsonPrice);

            requestSpecificationPricing.headers(HeaderValue.authentication());

            return requestSpecificationPricing.post();

        }

        public Object pricingGetNode(Response response, String jsonPath, String pricingResponse) throws IOException
        {
            ValidatableResponse validaTableResponsePricing = response.then().contentType(ContentType.JSON);

            validaTableResponsePricing.statusCode(200);

            JsonPath jsonPathEvaluatorPricing = response.jsonPath();

            HeaderValue.Response(pricingResponse, response.asPrettyString());

            return jsonPathEvaluatorPricing.get(jsonPath);
        }

        public boolean pricingShoppingResponseId(Response response, String pricingResponse) throws IOException
        {
            AirPricing P = new AirPricing();

            P.PricingShoppingResponseId = (String) pricingGetNode(response,"OfferPriceRS.ShoppingResponseId", pricingResponse);

            System.out.println(P.PricingShoppingResponseId);

            logger.info(P.PricingShoppingResponseId);

            return ( P.PricingShoppingResponseId.isEmpty() ||  P.PricingShoppingResponseId.matches("\\d+"));
        }

        public boolean offerResponseId(Response response, String pricingResponse) throws IOException
        {
            AirPricing P = new AirPricing();

            P.OfferResponseId = (String) pricingGetNode(response,"OfferPriceRS.OfferResponseId",pricingResponse);

            System.out.println(P.OfferResponseId);

            logger.info(P.OfferResponseId);

            return (P.OfferResponseId.matches("\\d+"));
        }

        public boolean pricingOfferId(Response response, String pricingResponse) throws IOException
        {

            AirPricing P = new AirPricing();

            P.PricingOfferId = (String) pricingGetNode(response, "OfferPriceRS.PricedOffer[0].OfferID",pricingResponse);

            System.out.println(P.PricingOfferId);

            logger.info(P.PricingOfferId);

            return (P.PricingOfferId.matches("\\d+"));
        }

        public boolean bookingCurrencyPrice(Response response, String pricingResponse) throws IOException
        {
            AirPricing P = new AirPricing();

            P.BookingCurrencyPrice = Float.parseFloat(""+pricingGetNode(response,"OfferPriceRS.PricedOffer[0].TotalPrice.BookingCurrencyPrice",pricingResponse));

            System.out.println(P.BookingCurrencyPrice);

            logger.info(P.BookingCurrencyPrice);

            return (P.BookingCurrencyPrice!=null);
        }

        public boolean errorResults(Response response,String pricingResponse)
        {
            try {
                AirPricing P = new AirPricing();

                P.Error = (String) pricingGetNode(response, "OfferPriceRS.Errors.Error.Value", pricingResponse);

                if(P.Error.matches("Unable to confirm availability for the selected booking class at this moment"))
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

