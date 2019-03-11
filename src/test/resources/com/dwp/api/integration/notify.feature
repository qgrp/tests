Feature: successful segregation and auditing

  Scenario Outline: Successfully segregate the queues and audit the messages
    Given There exists valid documenttype, documentAuthority to notify "<documentId>""<documentType>""<documentAuthority>""<documentContent>"
    When We notify of the docunent
    Then We get a 'OK' Http Response Code
    And We find a NOTIFICATION queue in the cbr Exchange
    And We find a NOTIFICATION.<documentAuthority>.<documentType> queue in the cbr Exchange
    And We find the message has been logged in the audit database
    Examples:
      | documentId                              | documentType  | documentAuthority  | documentContent   |
      | 32b568bc-4104-4f85-b675-165c5ed18733    | UC9999        | LA0246             | Lorem ipsum dolor sit amet, consectetur adipiscing..     |
