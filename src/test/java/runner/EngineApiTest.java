package runner;

import io.restassured.response.Response;
import listener.Location;
import onewayBooking.AirShopping;
import onewayBooking.OrderCreate;
import onewayBooking.Pricing;
import onewayBooking.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class EngineApiTest {

    public Response response;public Response responseReturn;public Response responseMultiCity; public Response responseOpenJaw;

    public static Logger log = LogManager.getLogger(EngineApiTest.class);

    AirShopping airShopping = new AirShopping();

    Pricing pricing = new Pricing();

    OrderCreate orderCreate = new OrderCreate();

    Request request = new Request();

    @Test
    public void request() {

        try
        {
            request.oneWayInput(Location.SearchRequest);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test (dependsOnMethods = "request")
    public void verifyOnewaySearching() {

        log.info("SUCCESS");

        try {
            response = airShopping.searching(Location.SearchRequest);

        } catch (IOException | ParseException e) {
            e.printStackTrace();

        }
        if (airShopping.errorFound(response, Location.SearchResponse)) {
            throw new SkipException("Origin Date is Greater Than Destination Date");
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayShopping() {
        try {
            assertTrue(airShopping.validateShopping(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayOffer() {
        try {
            assertTrue(airShopping.validateOffer(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayOwner() {
        try {
            assertTrue(airShopping.validateOwner(response, Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayOwnerName(){
        try {
            assertTrue(airShopping.ownerName(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayItinerary() {
        try {
            assertTrue(airShopping.count(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayIdentifier() {
        try {
            assertTrue(airShopping.pccIdentifier(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayPcc(){
        try {
            assertTrue(airShopping.pcc(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayProviderInfo(){
        try {
            assertTrue(airShopping.providerInfo(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayCabinType(){
        try {
            assertTrue(airShopping.cabinType(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayBaseBookingCurrencyPrice(){
        try {
            assertTrue(airShopping.baseBookingCurrencyPrice(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayTaxBookingCurrencyPrice(){
        try {
            assertTrue(airShopping.taxBookingCurrencyPrice(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayFopRef() {
        try {
            assertTrue(airShopping.fopRef(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void onewayBookingCurrencyCode(){
        try {
            assertTrue(airShopping.bookingCurrencyCode(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test(dependsOnMethods = "verifyOnewaySearching")
    public void pricingVerify() {

        try {
            log.info("SUCCESS");

            response = pricing.pricing(Location.SearchResponse, Location.PrincingRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pricing.errorResults(response,Location.PricingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching"})
    public void pricingShoppingResponseId(){
        try {
           assertTrue(pricing.pricingShoppingResponseId(response, Location.PricingResponse));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching"})
    public void offerResponseId(){
        try {
            assertTrue(pricing.offerResponseId(response, Location.PricingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching"})
    public void pricingOfferId() {
        try {
            assertTrue(pricing.pricingOfferId(response, Location.PricingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching"})
    public void bookingCurrencyPrice() {
        try {
            assertTrue(pricing.bookingCurrencyPrice(response, Location.PricingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching"})
    public void bookingVerify() {

        try {
            log.info("SUCCESS");

            response = orderCreate.onewayBooking(Location.PricingResponse, Location.BookingRequest);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if(orderCreate.errorResults(response, Location.BookingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }

    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void responseOrderId() {
        try {
            assertTrue(orderCreate.responseOrderId(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void gdsBookingReference() {
        try {
            assertTrue(orderCreate.gdsBookingReference(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void orderStatus() {
        try {
            assertTrue(orderCreate.orderStatus(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void fareType() {
        try {
            assertTrue(orderCreate.fareType(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void orgFareType() {
        try {
            assertTrue(orderCreate.orgFareType(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void supplierId() {
        try {
            assertTrue(orderCreate.supplierId(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"pricingVerify","verifyOnewaySearching","bookingVerify"})
    public void flightDeparture() {
        try {
            assertTrue(orderCreate.flightDeparture(response, Location.BookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void cardRequest()
    {
        try
        {
            request.oneWayInput(Location.SearchRequest);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test (dependsOnMethods = "cardRequest")
    public void onewayCardSearching() {
        log.info("SUCCESS");

        try {
            response = airShopping.searching(Location.SearchRequest);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        if (airShopping.errorFound(response, Location.SearchResponse)) {
            throw new SkipException("Origin Date is Greater Than Destination Date");
        }
    }

    @Test(dependsOnMethods = "onewayCardSearching")
    public void onewayCardShopping() {
        try
        {
            assertTrue(airShopping.validateShopping(response,Location.SearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test (dependsOnMethods = {"onewayCardSearching"} )
    public void onewayCardPricingVerify() {

        try {
            log.info("SUCCESS");

            response = pricing.pricing(Location.SearchResponse, Location.PrincingRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pricing.errorResults(response, Location.PricingResponse)) {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }
    @Test (dependsOnMethods = {"onewayCardPricingVerify","onewayCardSearching"})
    public void onewayCardBookingVerify() {

        try {
            log.info("SUCCESS");

            response = orderCreate.onewayBooking(Location.PricingResponse, Location.CardBookingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (orderCreate.errorResults(response, Location.CardBookingResponse)) {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }
    @Test (dependsOnMethods = "onewayCardBookingVerify")
    public void onFlyMarkupVerify() throws IOException {

        try {
            assertTrue(orderCreate.onFlyMarkup(response,Location.CardBookingResponse));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Test
    public void returnSearchRequest()
    {
        try
        {
            request.returnInput(Location.ReturnsearchRequest);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test (dependsOnMethods = "returnSearchRequest")
    public void returnSearch() {
        try {
            log.info("SUCCESS");

            responseReturn = airShopping.searching(Location.ReturnsearchRequest);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (airShopping.errorFound(responseReturn, Location.ReturnsearchResponse))
        {
            throw new SkipException("No Results Found & Origin Date is Greater Than Destination Date");
        }
    }
    @Test(dependsOnMethods = "returnSearch")
    public void returnShopping(){
        try {
            assertTrue(airShopping.validateShopping(responseReturn,Location.ReturnsearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test(dependsOnMethods = {"returnSearch"})
    public void returnPricing() {
        try {
            log.info("SUCCESS");

            responseReturn = pricing.pricing(Location.ReturnsearchResponse, Location.PrincingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pricing.errorResults(responseReturn, Location.ReturnPricingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }
    @Test(dependsOnMethods = {"returnPricing","returnSearch"})
    public void returnPricingShoppingResponseId(){
        try {
            assertTrue(pricing.pricingShoppingResponseId(responseReturn, Location.ReturnPricingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"returnPricing","returnSearch"})
    public void returnBooking() {
        try {
            log.info("SUCCESS");

            responseReturn = orderCreate.onewayBooking(Location.ReturnPricingResponse, Location.BookingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(orderCreate.errorResults(responseReturn, Location.ReturnbookingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }
    @Test(dependsOnMethods = {"returnPricing","returnSearch","returnBooking"})
    public void returnBookingResponseOrderId(){
        try {
            assertTrue(orderCreate.responseOrderId(responseReturn, Location.ReturnbookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void multiCityRequest()
    {
        try
        {
            request.multiCityInput(Location.MulticitysearchRequest);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test (dependsOnMethods = "multiCityRequest")
    public void multiCitySearch() {
        try {
            log.info("SUCCESS");

            responseMultiCity = airShopping.searching(Location.MulticitysearchRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (airShopping.errorFound(responseMultiCity, Location.MulticitysearchResponse))
        {
            throw new SkipException("Origin Date is Greater Than Destination Date");
        }
    }
    @Test(dependsOnMethods = "multiCitySearch")
    public void multiCityShopping() {
        try {
            assertTrue(airShopping.validateShopping(responseMultiCity,Location.MulticitysearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test(dependsOnMethods = "multiCitySearch")
    public void multiCityPricing() {
        try {
            log.info("SUCCESS");

            responseMultiCity = pricing.pricing(Location.MulticitysearchResponse, Location.PrincingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pricing.errorResults(responseMultiCity, Location.MulticitypricingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }

    }
    @Test(dependsOnMethods = {"multiCitySearch","multiCityPricing"})
    public void multiCityPricingShoppingResponseId() {
        try {
            assertTrue(pricing.pricingShoppingResponseId(responseMultiCity, Location.MulticitypricingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test(dependsOnMethods = {"multiCitySearch","multiCityPricing"})
    public void multiCityBooking(){
        try {
            log.info("SUCCESS");

            responseMultiCity = orderCreate.onewayBooking(Location.MulticitypricingResponse, Location.BookingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(orderCreate.errorResults(responseMultiCity, Location.MulticitybookingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }

    @Test(dependsOnMethods = {"multiCitySearch","multiCityPricing","multiCityBooking"})
    public void multiCityBookingResponseOrderId() {
        try {
            assertTrue(orderCreate.responseOrderId(responseMultiCity, Location.MulticitybookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void openJawRequest()
    {
        try
        {
            request.openJawInput(Location.OpenjawsearchRequest);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test (dependsOnMethods = "openJawRequest")
    public void openJawSearch() {
        try {
            log.info("SUCCESS");

            responseOpenJaw = airShopping.searching(Location.OpenjawsearchRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (airShopping.errorFound(responseOpenJaw, Location.OpenjawsearchResponse))
        {
            throw new SkipException("Origin Date is Greater Than Destination Date");
        }
    }
    @Test(dependsOnMethods = "openJawSearch")
    public void openJawShopping() {
        try {
            assertTrue(airShopping.validateShopping(responseOpenJaw, Location.OpenjawsearchResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test(dependsOnMethods = "openJawSearch")
    public void openJawPricing() {
        try {
            log.info("SUCCESS");

            responseOpenJaw = pricing.pricing(Location.OpenjawsearchResponse, Location.PrincingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pricing.errorResults(responseOpenJaw, Location.OpenjawpricingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }
    @Test(dependsOnMethods = {"openJawSearch","openJawPricing"})
    public void openJawPricingShoppingResponseId() {
        try {
            assertTrue(pricing.pricingShoppingResponseId(responseOpenJaw, Location.OpenjawpricingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test(dependsOnMethods = {"openJawPricing","openJawSearch"})
    public void openJawBooking() {
        try {
            log.info("SUCCESS");

            responseOpenJaw = orderCreate.onewayBooking(Location.OpenjawpricingResponse, Location.BookingRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(orderCreate.errorResults(responseOpenJaw, Location.OpenjawbookingResponse))
        {
            throw new SkipException("Unable to confirm availability for the selected booking class at this moment");
        }
    }
    @Test(dependsOnMethods = {"openJawSearch","openJawPricing","openJawBooking"})
    public void openJawBookingResponseOrderId() {
        try {
            assertTrue(orderCreate.responseOrderId(responseOpenJaw, Location.OpenjawbookingResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




