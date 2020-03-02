Feature: Saving Users
  Scenario: Saving a User
    When I save a user with:
      """
      {
        "firstName":"asf",
        "lastName":"asdf",
        "userName":"32145asdf",
        "emailAddress":"a@b.c",
        "expirationDateDriversLicense":null,
        "isActive":true,
        "isBanned":false,
        "street":"test",
        "zipCode":"jasdf",
        "city":"asdjkff",
        "country":"jkadsf"
      }
      """
    And the response is 200