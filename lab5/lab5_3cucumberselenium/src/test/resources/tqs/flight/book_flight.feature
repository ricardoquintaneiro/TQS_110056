Feature: Booking a flight

    Scenario: Booking a flight from Boston to Dublin
        When I navigate to "https://blazedemo.com/"
        And I select the origin as "Boston"
        And I select the destination as "Dublin"
        And I click find flights
        Then I should see a list of available flights

        When I select flight 2
        And I click purchase flight
        Then I should see a confirmation message 
