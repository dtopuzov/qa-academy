Feature: Calculator

	In order to avoid silly mistakes
	As a math idiot
	I want to have calculator

Scenario: Multiply two numbers

	Given scientific calculator
	When press 2  
	And press ×
	And press 2
	And press =
	Then result is 4

Scenario Outline: Sum two numbers

	Given scientific calculator
	When press <First Number> 
	And press +
	And press <Second Number>
	And press =
	Then result is <Result>

	Examples:
	| First Number | Second Number | Result |
	| 0            | 0             | 0      |
	| 1            | 1             | 2      |
	| 2            | 2             | 4      |
	| 4            | 4             | 8      |

