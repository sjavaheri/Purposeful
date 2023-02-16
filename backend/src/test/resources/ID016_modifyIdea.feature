Feature: Modify Idea
	As a user, I want to be able to modify the details of any idea that I have posted it so that my idea can be changed over time

	Background:
		Given the database contains the following user accounts:
			| firstName | lastName | email                | password         |
			| User      | Steve    | user.steve@gmail.com | SteveIsAwesome01 |
			| User      | Bob      | user.bob@gmail.com   | BobIsAwesome01   |
		And the database contains the following domains:
			| name       |
			| Software   |
			| Computer   |
			| Electrical |
		And the database contains the following topics:
			| name              |
			| Frontend Dev      |
			| Backend Dev       |
			| Embedded Software |
		And the database contains the following techs:
			| name   |
			| Python |
			| Java   |
			| React  |
			| C      |
		And the database contains the following URLs:
			| URL           |
			| something.com |
			| another.com   |
			| sayless.com   |
			| keepitup.com  |
			| interest.com  |
			| bestteam.com  |
		And the database contains the following ideas:
			| title             | date       | purpose   | descriptions     | isPaid | inProgress | isPrivate | domains             | topics                    | techs            | image_urls                 | icon_url       | user_email             |
			| Home Care App     | 2022-02-15 | Health    | Quality app      | True   | True       | False     | Software            | Frontend Dev              | Python           | something.com              | interest.com   | user.steve@gmail.com   |
			| Football Game     | 2022-02-16 | Entertain | For fun          | False  | True       | False     | Software, Computer  | Frontend Dev              | Python           | something.com, another.com | interest.com   | user.steve@gmail.com   |
			| Car Detection App | 2022-02-17 | Police    | Effective app    | True   | False      | False     | Computer            | Backend Dev, Frontend Dev | Python, C        | keepitup.com               | bestteam.com   | user.steve@gmail.com   |
			| Circuit Design    | 2022-02-18 | Electric  | Silicon photonic | True   | False      | True      | Electrical          | Embedded Software         | Java, React, C   | sayless.com                | bestteam.com   | user.steve@gmail.com   |
		And I am logged in as the user with email "user.steve@gmail.com" and password "SteveIsAwesome01"

	# Normal Flow

	Scenario Outline: Successfully modify an idea
		When the user requests to modify the field <field> to become <new_value> instead of <old_value> for idea with title <title>
		Then the idea with title <title> will have value <new_value> for the field <field>

		Examples:
			| title             | field        | old_value       | new_value         |
			| Home Care App     | title        | Home Care App   | Health App        |
			| Home Care App     | date         | 2022-02-15      | 2022-01-15        |
			| Football Game     | purpose      | Entertain       | FIFA Product      |
			| Football Game     | descriptions | For fun         | La Liga           |
			| Car Detection App | isPaid       | True            | False             |
			| Car Detection App | inProgress   | False           | True              |
			| Circuit Design    | isPrivate    | True            | False             |
			| Circuit Design    | domains      | Electrical      | Software          |
			| Home Care App     | topics       | Frontend Dev    | Backend Dev       |
			| Football Game     | techs        | Python          | Java, React       |
			| Car Detection App | image URLs   | keepitup.com    | sayless.com       |
			| Circuit Design    | icon URL     | bestteam.com    | another.com       |

	# Alternate Flow

	Scenario Outline: Successfully modify the idea with an empty field that can be empty
		When the user requests to modify the field <field> to become empty for idea with title <title>
		Then the idea with title <title> will have empty for the field <field>

		Examples:
			| title             | field      |
			| Circuit Design    | domains    |
			| Home Care App     | topics     |
			| Football Game     | techs      |
			| Car Detection App | image URLs |
			| Circuit Design    | icon URL   |

	# Error Flow

	Scenario Outline: Unsuccessfully modify the idea with an empty field that cannot be empty
		When the user requests to modify the field <field> to become empty for idea with title <title>
		Then the idea with title <title> will have value <old_value> for the field <field>
		Then the error message <error> will be thrown with status code <Http_status>

		Examples:
			| title             | field        | old_value     | error                                    | Http_status |
			| Home Care App     | title        | Home Care App | Necessary fields have been left empty    | 400         |
			| Home Care App     | date         | 2022-02-15    | Necessary fields have been left empty    | 400         |
			| Football Game     | purpose      | Entertain     | Necessary fields have been left empty    | 400         |
			| Football Game     | descriptions | For fun       | Necessary fields have been left empty    | 400         |
			| Car Detection App | isPaid       | True          | Necessary fields have been left empty    | 400         |
			| Car Detection App | inProgress   | False         | Necessary fields have been left empty    | 400         |
			| Circuit Design    | isPrivate    | True          | Necessary fields have been left empty    | 400         |

	# Error Flow

	Scenario Outline: (Error Flow) Unsuccessfully modify an idea with a non-existing object
		When the user requests to modify the field <field> to become new value <new value> for idea with title <title>
		Then the idea with title <title> will have value <old_value> for the field <field>
		Then the error message <error> will be thrown with status code <Http_status>

		Examples:
			| title             | field      | old_value           | new_value            | error                                                                    | Http_status |
			| Circuit Design    | domains    | Electrical          | Earth Specialist     | You are attempting to link your idea to an object that does not exist    | 400         |
			| Home Care App     | topics     | Frontend Dev        | Fake Dev             | You are attempting to link your idea to an object that does not exist    | 400         |
			| Football Game     | techs      | Python              | Latin Language       | You are attempting to link your idea to an object that does not exist    | 400         |
			| Car Detection App | image URLs | keepitup.com        | nonexisting.com      | You are attempting to link your idea to an object that does not exist    | 400         |
			| Circuit Design    | icon URL   | bestteam.com        | evenworse.com        | You are attempting to link your idea to an object that does not exist    | 400         |



