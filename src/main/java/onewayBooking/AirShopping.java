package onewayBooking;

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
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;


class Airvariable {

    String ShoppingResponseId; String Offer; String Owner; String OwnerName; String PccIdentifier; String PCC;

    String ProviderInfo; String CabinType; Float BaseBookingCurrencyPrice=null; Float TaxBookingCurrencyPrice=null;

    String FopRef; String BookingCurrencyCode; String Error;
}

public class AirShopping
{
    public ConfigFileReader configFileReader = new ConfigFileReader();

    static final Logger logger = LogManager.getLogger(AirShopping.class);

    public Response searching(String newSearchRequest) throws IOException, ParseException
     {

        JSONObject requestObject = HeaderValue.parser(newSearchRequest);

        System.out.println(requestObject);

        RequestSpecification requestSpecification = RestAssured.given();

        requestSpecification = requestSpecification.log().all();

        requestSpecification.baseUri(configFileReader.engineUrl());

        requestSpecification.basePath(configFileReader.airShopping());

        requestSpecification.body(requestObject);

        requestSpecification.headers(HeaderValue.authentication());

        return requestSpecification.post();

    }

    public Object getNode(Response response, String jsonPath, String searchingResponse) throws IOException
    {

        ValidatableResponse validatableResponse = response.then().contentType(ContentType.JSON);

        validatableResponse.statusCode(200);

        JsonPath jsonPathEvaluator = response.jsonPath();

        HeaderValue.createFolder();

        HeaderValue.Response(searchingResponse, response.asPrettyString());

        return jsonPathEvaluator.get(jsonPath);
    }

    public boolean validateShopping(Response response,String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.ShoppingResponseId = (String) getNode(response, "AirShoppingRS.ShoppingResponseId",searchingResponse);

        System.out.println(V.ShoppingResponseId);

        logger.info(V.ShoppingResponseId);

        return (V.ShoppingResponseId.isEmpty() || V.ShoppingResponseId.matches("\\d+"));
    }

    public boolean validateOffer(Response response,String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.Offer = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].OfferID",searchingResponse);

        System.out.println(V.Offer);

        logger.info(V.Offer);

        return (V.Offer.isEmpty() || V.Offer.matches("\\d+"));
    }

    public boolean validateOwner(Response response,String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.Owner = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].Owner",searchingResponse);

        System.out.println(V.Owner);

        logger.info(V.Owner);

        return true;
    }

    public boolean ownerName(Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.OwnerName = (String) getNode(response,"AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].OwnerName",searchingResponse);

        System.out.println(V.OwnerName);

        logger.info(V.OwnerName);

        return (!V.OwnerName.isEmpty() || V.OwnerName.matches("[a-zA-Z]"));

    }
    public boolean count (Response response, String searchingResponse) throws IOException
    {
        ArrayList<String> OfferIDS = (ArrayList<String>) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer",searchingResponse);

        System.out.println(OfferIDS.size());

        logger.info(OfferIDS.size());

        return OfferIDS.size() > 0 ;

    }

    public boolean pccIdentifier (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.PccIdentifier = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].PccIdentifier",searchingResponse);

        System.out.println(V.PccIdentifier);

        logger.info(V.PccIdentifier);

        return (!V.PccIdentifier.isEmpty());
    }

    public  boolean pcc (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.PCC = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].PCC",searchingResponse);

        System.out.println(V.PCC);

        logger.info(V.PCC);

        return (!V.PCC.isEmpty());
    }
    public boolean providerInfo (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.ProviderInfo = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].ProviderInfo",searchingResponse);

        System.out.println(V.ProviderInfo);

        logger.info(V.ProviderInfo);

        return (!V.ProviderInfo.isEmpty());
    }
    public boolean cabinType (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.CabinType = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].CabinType",searchingResponse);

        System.out.println(V.CabinType);

        logger.info(V.CabinType);

        return (!V.CabinType.isEmpty());
    }
    public boolean baseBookingCurrencyPrice (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.BaseBookingCurrencyPrice = Float.parseFloat(""+getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].BasePrice.BookingCurrencyPrice",searchingResponse));

        System.out.println(V.BaseBookingCurrencyPrice);

        logger.info(V.BaseBookingCurrencyPrice);

        return (V.BaseBookingCurrencyPrice!=null);
    }
    public boolean taxBookingCurrencyPrice (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.TaxBookingCurrencyPrice =  Float.parseFloat(""+getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].TaxPrice.BookingCurrencyPrice",searchingResponse));

        System.out.println(V.TaxBookingCurrencyPrice);

        logger.info(V.TaxBookingCurrencyPrice);

        return (V.TaxBookingCurrencyPrice!=null);
    }

    public boolean fopRef (Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.FopRef = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].FopRef",searchingResponse);

        System.out.println(V.FopRef);

        logger.info(V.FopRef);

        return (!V.FopRef.isEmpty());
    }

    public boolean bookingCurrencyCode(Response response, String searchingResponse) throws IOException
    {
        Airvariable V = new Airvariable();

        V.BookingCurrencyCode = (String) getNode(response, "AirShoppingRS.OffersGroup.AirlineOffers[0].Offer[0].BookingCurrencyCode",searchingResponse);

        System.out.println(V.BookingCurrencyCode);

        logger.info(V.BookingCurrencyCode);

        return (!V.BookingCurrencyCode.isEmpty());
    }
    public boolean errorFound(Response response,String bookingResponse)
    {
        try {
            Airvariable V = new Airvariable();

            V.Error = (String) getNode(response, "AirShoppingRS.Errors.Error.Value", bookingResponse);

            if(V.Error.matches("Origin Date is Greater Than Destination Date"))
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

