Feature: Create Idea
  As a user, I want to create a public post about an idea I have and link it to a domain, so that other users interested in the domain can see my idea.

  Background: 
    Given the database contains the following user account:
      | firstname | lastname | email               | password           |
      | User      | Test     | user.test@gmail.com | UnbreakableCode101 |
    And the number of ideas in the database is 0
    And the database contains the following domains:
      | name     |
      | Software |
      | Science  |
      | Other    |
    And the database contains the following topics:
      | name             |
      | AI               |
      | Machine Learning |
    And the database contains the following techs:
      | name       |
      | Tensorflow |
      | Python     |
    And the database contains the following URLs:
      | url          |
      | img.png      |
      | img2.png     |
      | schedule.png |

  # Normal Flow
  Scenario Outline: Successfully create an idea
    Given that the user is logged in with the email "user.test@gmail.com" and the password "UnbreakableCode101"
    When the user creates an idea with title "<title>", purpose "<purpose>", description "<description>", paid status "<isPaid>", progress status "<inProgress>", private status "<isPrivate>", domains "<domains>", topics "<topics>", techs "<techs>", icon "<iconUrl>", supporting images "<supportingImageUrls>"
    Then a new idea exists in the database with title "<title>", icon "<iconUrl>"
    And the number of ideas in the database is 1

    Examples: 
      | title                  | purpose          | description               | isPaid | inProgress | isPrivate | domains          | topics              | techs             | iconUrl      | supportingImageUrls |
      | Smart Schedule Manager | Save people time | AI daily schedule builder | true   | false      | false     | Software,Science | AI,Machine Learning | Tensorflow,Python | schedule.png | img.png,img2.png    |
      | exampleTitle           | improve testing  | Example description       | true   | false      | false     | Software,Other   | AI,Machine Learning |                   | schedule.png |                     |

  # Error Flows
  Scenario Outline: Unsuccessfully create an idea
    Given that the user is logged in with the email "user.test@gmail.com" and the password "UnbreakableCode101"
    When the user creates an idea with title "<title>", purpose "<purpose>", description "<description>", paid status "<isPaid>", progress status "<inProgress>", private status "<isPrivate>", domains "<domains>", topics "<topics>", techs "<techs>", icon "<iconUrl>", supporting images "<supportingImageUrls>"
    Then the following error "<error>" shall be raised with the status code <statusCode>
    And the number of ideas in the database is 0

    Examples: 
      | title                                                                                                   | purpose          | description               | isPaid | inProgress | isPrivate | domains              | topics              | techs             | iconUrl      | supportingImageUrls | error                                                                 | statusCode |
      |                                                                                                         | Save people time | AI daily schedule builder | true   | false      | false     | Software,Science     | AI,Machine Learning | Tensorflow,Python | schedule.png | img.png,img2.png    | Necessary fields have been left empty                                 |        400 |
      | Smart Schedule Manager                                                                                  |                  | AI daily schedule builder | true   | false      | false     | Software,Science     | AI,Machine Learning | Tensorflow,Python | schedule.png | img.png,img2.png    | Necessary fields have been left empty                                 |        400 |
      | Smart Schedule Manager                                                                                  | Save people time |                           | true   | false      | false     | Software,Science     | AI,Machine Learning | Tensorflow,Python | schedule.png | img.png,img2.png    | Necessary fields have been left empty                                 |        400 |
      | exampleTitle                                                                                            | improve testing  | Example description       | true   |            | false     |                      | AI,Machine Learning |                   | schedule.png |                     | You must specify at least 1 domain                                    |        400 |
      | exampleTitle                                                                                            | improve testing  | Example description       | true   |            | false     | Software,Science     |                     |                   | schedule.png |                     | You must specify at least 1 topic                                     |        400 |
      | Smart Schedule Manager                                                                                  | Save people time | AI daily schedule builder | true   | false      | false     | Software,Science     | AI,Machine Learning | Tensorflow,Python |              | img.png,img2.png    | An idea icon is required                                              |        400 |
      | wfbypsX1842YKvElZNfDYCtjMU6UZXXF15KltjAsMdpYt8VFBLbpaS28R8PyiUrUJX79y77dtmEyrnfzOk0aAIp2wz8wX3J6wjynwqe | Save people time | AI daily schedule builder | true   | false      | false     | Software,Science     | AI,Machine Learning | Tensorflow,Python | schedule.png | img.png,img2.png    | Idea titles cannot exceed 100 characters                              |        400 |
      | Smart Schedule Manager                                                                                  | Save people time | AI daily schedule builder | true   | false      | false     | Software,NonExistent | AI,Machine Learning | Tensorflow,Python | schedule.png | img.png,img2.png    | You are attempting to link your idea to an object that does not exist |        400 |
